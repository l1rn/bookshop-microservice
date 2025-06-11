package com.l1rn.user_service.security.jwt;

import com.l1rn.user_service.services.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final Logger logger;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = checkToken(request);
            if(token == null){
                filterChain.doFilter(request, response);
                throw new AuthorizationDeniedException("Токен истек или недействителен");
            }
            if(!jwtUtils.validateToken(token)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

            String email = jwtUtils.getEmailFromClaim(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            logger.error("Ошибка аутентификации: ", e);
            return;
        }
        filterChain.doFilter(request, response);
    }


    public String checkToken(HttpServletRequest request){
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if("accessToken".equals(cookie.getName())){
                    return cookie.getName();
                }
            }
        }
        return null;
    }
}
