package com.l1rn.user_service.services;

import com.l1rn.user_service.dto.auth.JwtResponse;
import com.l1rn.user_service.dto.auth.SigninDTO;
import com.l1rn.user_service.dto.auth.SignupDTO;
import com.l1rn.user_service.models.entity.Device;
import com.l1rn.user_service.models.entity.Token;
import com.l1rn.user_service.models.entity.user.UserEntity;
import com.l1rn.user_service.repository.DeviceRepository;
import com.l1rn.user_service.repository.TokenRepository;
import com.l1rn.user_service.repository.UserRepository;
import com.l1rn.user_service.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

    public void signup(SignupDTO request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким ");
        }
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    @Transactional
    public JwtResponse signin(SigninDTO signinDTO, HttpServletRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinDTO.getEmail(),
                        signinDTO.getPassword()
                )
        );
        UserEntity user = userRepository.findByEmail(signinDTO.email)
                .orElseThrow(() -> new AccessDeniedException("Пользователь с такой почтой не найден"));

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

        Device device = deviceRepository.findByFingerprintAndUser(hashDevice, user)
                .orElseGet(() -> deviceRepository.save(newDevice));

        String refreshToken = jwtUtils.generateRefreshToken(user);
        String accessToken = jwtUtils.generateAccessToken(user);
        tokenRepository.deleteByUserAndDevice(user, device);

        Token refreshTokenEntity = Token.builder()
                .token(refreshToken)
                .user(user)
                .device(device)
                .expiryDate(Instant.now().plusMillis(jwtUtils.getExpirationMillis(refreshToken)))
                .build();

        tokenRepository.save(refreshTokenEntity);

        return new JwtResponse(accessToken, refreshToken);
    }
}
