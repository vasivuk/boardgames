package com.vasivuk.boardgames.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasivuk.boardgames.configuration.Constants;
import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.dto.AppUserDTO;
import com.vasivuk.boardgames.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vasivuk.boardgames.configuration.Routes.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000/**")
public class UserController {

    /*
    -CreateUser(User)
    -Login(User)
    -Logout(User)
    -EditUserData(User)
     */
    private final UserService service;

    @GetMapping("/api/users")
    public List<AppUser> getUsers() {
        return service.getUsers();
    }

    @GetMapping(USER_COMMON + ID)
    public AppUser findUserById(@PathVariable("id") Long id) throws EntityNotFoundException {
        return service.findUserById(id);
    }

    @PostMapping("/api/register")
    public AppUser createUser(@RequestBody @Valid AppUserDTO appUser) throws EntityAlreadyExistsException {
        return service.saveUser(appUser);
    }

    @GetMapping("/api/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response,
                                         @CookieValue(value = "refreshToken") String refreshToken) {
        log.info("In logout controller");
        if (refreshToken.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Cookie deleteRefreshToken = new Cookie("refreshToken", null);
        deleteRefreshToken.setMaxAge(0);
        deleteRefreshToken.setHttpOnly(true);
        deleteRefreshToken.setSecure(true);
        deleteRefreshToken.setPath("/");

        response.addCookie(deleteRefreshToken);
        return ResponseEntity.ok("Successfully logged out");
    }

    @GetMapping("/api/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response,
                             @CookieValue(value = "refreshToken") String refreshToken) throws IOException {
        Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            log.info("Refreshing token");
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String email = decodedJWT.getSubject();
            AppUser user = service.findUserByEmail(email);

            String accessToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_EXPIRATION))
                    .withClaim("authority", user.getUserRole())
                    .sign(algorithm);
            response.setContentType(APPLICATION_JSON_VALUE);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("authority", user.getUserRole());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (Exception e) {
            response.setStatus(FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", e.getMessage());
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

    @GetMapping(USER_COMMON + ID + ASSIGN_ADMIN)
    public void assignAdminRole(@PathVariable Long id) throws EntityNotFoundException {
        service.assignAdminRoleToUser(id);
    }

}
