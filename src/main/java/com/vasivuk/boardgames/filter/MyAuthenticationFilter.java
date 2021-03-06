package com.vasivuk.boardgames.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasivuk.boardgames.configuration.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vasivuk.boardgames.configuration.Constants.ACCESS_TOKEN_EXPIRATION;
import static com.vasivuk.boardgames.configuration.Constants.REFRESH_TOKEN_EXPIRATION;

@RequiredArgsConstructor
@Slf4j
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * Uz pomoć menadžera vrši autentifikaciju tako što uzima email i password iz requesta i kreira autentifikacioni token na osnovu njih.
     *
     * @param request zahtev
     * @param response odgovor
     * @return objekat koji vrši samu autentifikaciju
     * @throws AuthenticationException U slučaju greške pri autentifikaciji
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.info("Username: {}", email);
        log.info("Password: {}", password);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(authToken);
    }

    /**
     * Pokreće se metoda u slučaju da je korisnik uspešno autentifikovan.
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException izuzetak
     * @throws ServletException izuzetak
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User)authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET.getBytes());

        String access_token = createAccessToken(user, algorithm);
        String refresh_token = createRefreshToken(user, algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    /**
     * Kreira token za pristup vebsajtu.
     * @param user
     * @param algorithm
     * @return
     */
    private String createAccessToken(User user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .withClaim("authority", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0))
                .sign(algorithm);
    }

    /**
     * Kreira token za osvežavanje tokena za pristup.
     * @param user
     * @param algorithm
     * @return
     */
    private String createRefreshToken(User user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(algorithm);
    }
}
