package com.vasivuk.boardgames.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.vasivuk.boardgames.configuration.Constants.BEARER;
import static com.vasivuk.boardgames.configuration.Constants.SECRET;
import static com.vasivuk.boardgames.configuration.Routes.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
public class MyAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<String> allowedRoutes = Arrays.asList(
                LOGIN,
                REGISTER,
                REFRESH_TOKEN,
                PRODUCT_COMMON,
                CATEGORY_COMMON
        );
        if (allowedRoutes.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
                String token = authorizationHeader.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                try {
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String email = decodedJWT.getSubject();
                    String authority = decodedJWT.getClaim("authority").asString();
                    Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
                    grantedAuthorities.add(new SimpleGrantedAuthority(authority));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (TokenExpiredException ex) {
                    log.error("Error logging in: {}", ex.getMessage());
                    response.setHeader("error", ex.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", ex.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
