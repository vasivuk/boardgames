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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import static com.vasivuk.boardgames.configuration.Routes.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * UserController kontroler obradjuje zahteve vezane za korisnike sistema
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000/**", allowCredentials = "true")
public class UserController {

    /**
     * Servis u kome se nalazi poslovna logika vezana za korisnike
     */
    private final UserService service;

    /**
     * Metoda koja poziva servis da vrati sve korisnike
     * @return svi korisnici sistema
     */
    @GetMapping("/api/users")
    public List<AppUser> getUsers() {
        return service.getUsers();
    }

    /**
     * Metoda vraca korisnika na osnovu prosledjene email adrese
     * @param email email adresa korisnika
     * @return korisnika
     * @throws EntityNotFoundException u slucaju da korisnik nije pronadjen
     */
    @GetMapping("/api/users/user")
    public AppUser fetchUserByEmail(@RequestParam("email") String email) throws EntityNotFoundException {
        return service.findUserByEmail(email);
    }

    /**
     * Metoda vraca korisnika na osnovu njegovog identifikacionog broja u sistemu
     * @param id identifikacioni broj korisnika sistema
     * @return korisnik sistema
     * @throws EntityNotFoundException u slucaju da korisnik nije pronadjen
     */
    @GetMapping(USER_COMMON + ID)
    public AppUser findUserById(@PathVariable("id") Long id) throws EntityNotFoundException {
        return service.findUserById(id);
    }

    /**
     * Metoda salje zahtev za registraciju korisnika
     * @param appUser novi korisnik
     * @return novi kreirani korisnik
     * @throws EntityAlreadyExistsException u slucaju da korisnik sa prosledjenim emailom vec postoji u bazi
     */
    @PostMapping("/api/register")
    public AppUser createUser(@RequestBody @Valid AppUserDTO appUser) throws EntityAlreadyExistsException {
        return service.saveUser(appUser);
    }

    /**
     * Metoda odjavljuje korisnika iz sistema
     * @param response serverski odgovor
     * @param refreshToken token za obnavljanje tokena za autorizaciju
     * @return poruka o uspesnosti
     */
    @GetMapping("/api/logout")
    public ResponseEntity<String> logout(HttpServletResponse response,
                                         @CookieValue(value = "refreshToken") String refreshToken) {
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

    /**
     * Metoda koja se poziva kada treba da se obnovi token za autorizaciju
     * @param response serverski odgovor
     * @param refreshToken token za obnavljanje tokena za autorizaciju
     * @throws IOException u slucaju da dodje do greske pri unosu
     */
    @GetMapping("/api/token/refresh")
    public void refreshToken(HttpServletResponse response,
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
            tokens.put("email", user.getEmail());
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

    /**
     * Metoda dodaje ulogu admina nekom korisniku
     * @param id identifikacioni broj korisnika
     * @throws EntityNotFoundException u slucaju da korisnik sa id-om nije pronadjen
     */
    @GetMapping(USER_COMMON + ID + ASSIGN_ADMIN)
    public void assignAdminRole(@PathVariable Long id) throws EntityNotFoundException {
        service.assignAdminRoleToUser(id);
    }

}
