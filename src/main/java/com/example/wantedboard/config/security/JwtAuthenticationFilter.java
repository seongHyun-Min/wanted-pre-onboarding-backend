package com.example.wantedboard.config.security;

import com.example.wantedboard.domain.entity.User;
import com.example.wantedboard.service.user.AuthService;
import com.example.wantedboard.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveTokenFromRequest(request);
            // access token 이 있고 유효하다면
            if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
                //토큰으로 찾는거
                User user = authService.findUserByToken(token);
                UserAuthentication authentication = new UserAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (IllegalArgumentException | JwtException e) {
            log.info("JwtAuthentication UnauthorizedUserException!");
            request.setAttribute("UnauthorizedUserException", e);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (!ObjectUtils.isEmpty(token) && token.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            return token.substring(BEARER_TYPE.length()).trim();
        }
        return null;
    }
}