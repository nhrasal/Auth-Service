package com.app.auth.configuration;


import com.app.auth.base.redis.RedisRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RedisRepo redisRepo;

    List<String> permittedUrlPatterns = Arrays.asList(
            "/api-docs/**","/swagger-ui/**","/auth/**", "/public/**"
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        boolean isPermittedURI = permittedUrlPatterns.stream().anyMatch(p -> new AntPathMatcher().match(p, path));
        if (isPermittedURI){
//            allowCrossOrigin(request, response, filterChain);
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().write("{\"status\": 401, \"body\": null, \"message\": \"No token added.\"}");
//            return;
        }
        jwt = authHeader.substring(7);

        if (jwt.isEmpty()){
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"status\": 401, \"body\": null, \"message\": \"Token is not valid.\"}");
            return;
        }
//            filterChain.doFilter(request, response);

        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var redisToken = redisRepo.getValue(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails) && jwt.equals(redisToken)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
