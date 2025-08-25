package com.reform.wiz.security.filter;
import com.reform.wiz.security.auth.CustomUserPrincipal;
import com.reform.wiz.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        if (
            path.startsWith("/api/v1/token")
            || path.startsWith("/api/v1/member/login")
            || path.startsWith("/api/v1/member/logout")
            || path.startsWith("/api/v1/member/join")
        ) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer")) {
            // Bearer로 시작안하거나 authroization 헤더에 없으면 에러
            handleException(response, new Exception("Access Token Not Found"));
            return;
        }

        String token = authorization.substring(7);

        try {
            Map<String, Object> map = jwtUtil.validateToken(token);

            // 토큰 문제없는 경우 진행
            String mid = map.get("mid").toString();
            String[] roles = map.get("role").toString().split(",");

            // SecurityContext에서 사용하는 토큰 발행
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    new CustomUserPrincipal(mid),
                    null,
                    Arrays.stream(roles).map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList())
            );

            // SecurityContext에 토큰 넣어두기
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    public void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\":\"" + e.getMessage() + "\"}");
    }
}
