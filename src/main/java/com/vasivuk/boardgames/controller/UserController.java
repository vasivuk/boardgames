package com.vasivuk.boardgames.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasivuk.boardgames.configuration.Constants;
import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.dto.AppUserDTO;
import com.vasivuk.boardgames.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vasivuk.boardgames.configuration.Routes.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequiredArgsConstructor
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

    @GetMapping("/api/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {

                String refresh_token = authorizationHeader.substring("Bearer ".length());

                Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = service.findUserByEmail(username);

                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_EXPIRATION))
                        .withClaim("authority", user.getUserRole())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @GetMapping(USER_COMMON + ID + ASSIGN_ADMIN)
    public void assignAdminRole(@PathVariable Long id) throws EntityNotFoundException {
        service.assignAdminRoleToUser(id);
    }

}
