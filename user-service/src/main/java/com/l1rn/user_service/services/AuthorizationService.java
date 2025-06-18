package com.l1rn.user_service.services;

import com.l1rn.user_service.dto.auth.JwtResponse;
import com.l1rn.user_service.dto.auth.SigninRequest;
import com.l1rn.user_service.dto.auth.SignupRequest;
import com.l1rn.user_service.models.entity.Device;
import com.l1rn.user_service.models.entity.Token;
import com.l1rn.user_service.models.entity.user.UserEntity;
import com.l1rn.user_service.models.enums.ERole;
import com.l1rn.user_service.models.enums.EStatus;
import com.l1rn.user_service.repository.DeviceRepository;
import com.l1rn.user_service.repository.TokenRepository;
import com.l1rn.user_service.repository.UserRepository;
import com.l1rn.user_service.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    @Autowired
    private final JwtUtils jwtUtils;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DeviceRepository deviceRepository;

    @Autowired
    private final TokenRepository tokenRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким ");
        }
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(ERole.ROLE_USER)
                .status(EStatus.STATUS_CREATED)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public JwtResponse signin(SigninRequest signinRequest, HttpServletRequest request){
        UserEntity user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Пользователь с такой почтой не найден"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinRequest.getEmail(),
                        signinRequest.getPassword()
                )
        );

        String userAgent = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();
        String userString = request.getRemoteUser();
        String fingerPrint = "agent: " + userAgent + " ip: " + ipAddress + " user: " + userString;

        String hashDevice = Hashing.sha256()
                .hashString(fingerPrint, StandardCharsets.UTF_8)
                .toString();
        log.info(hashDevice);

        Device newDevice = Device.builder()
                .fingerPrint(hashDevice)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .username(userString)
                .build();

        Device device = deviceRepository.findByFingerPrintAndUser(hashDevice, user)
                .orElseGet(() -> deviceRepository.save(newDevice));

        String refreshToken = jwtUtils.generateRefreshToken(user);
        String accessToken = jwtUtils.generateAccessToken(user);
        tokenRepository.deleteByUserAndDevice(user, device);
        tokenRepository.flush();

        Token refreshTokenEntity = Token.builder()
                .token(refreshToken)
                .user(user)
                .device(device)
                .expiryDate(Instant.now().plusMillis(jwtUtils.getExpirationMillis(refreshToken)))
                .build();

        tokenRepository.save(refreshTokenEntity);

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse refreshToken(String refreshToken){
        if(!jwtUtils.validateToken(refreshToken)){
            throw new RuntimeException("Invalid refresh token!");
        }

        Token storedToken = tokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        tokenRepository.delete(storedToken);

        if(storedToken.getExpiryDate().isBefore(Instant.now())){
            tokenRepository.delete(storedToken);
            throw new RuntimeException("Refresh token expired");
        }

        UserEntity user = storedToken.getUser();
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshTokenUpdated = jwtUtils.generateRefreshToken(user);

        Token newToken = Token.builder()
                .token(refreshTokenUpdated)
                .user(user)
                .device(storedToken.getDevice())
                .expiryDate(Instant.now().plusMillis(jwtUtils.getExpirationMillis(refreshTokenUpdated)))
                .build();

        tokenRepository.save(newToken);
        return new JwtResponse(accessToken, refreshTokenUpdated);
    }

    @Transactional
    public void deleteToken(String token){
        tokenRepository.findByToken(token)
                .ifPresent(tokenRepository::delete);
    }
}
