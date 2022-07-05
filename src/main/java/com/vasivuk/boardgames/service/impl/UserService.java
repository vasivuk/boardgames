package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.repository.UserRepository;
import com.vasivuk.boardgames.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public AppUser createUser(AppUser appUser) {
        return null;
    }

    @Override
    public AppUser login(AppUser appUser) {
        return null;
    }

    @Override
    public void logout(AppUser appUser) {

    }

    /**
     * Kreira User objekat iz security klase springboot frameworka, na osnovu korisnickih podataka iz baze.
     * Za identifikaciju koristi email i password korisnika. Email je unique polje u bazi.
     * @param email email korisnika
     * @return user objekat
     * @throws UsernameNotFoundException U slučaju da email ne postoji u bazi.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = repository.findByEmail(email);
        Collection<SimpleGrantedAuthority> authorities;
        if (user == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("user not found in the database");
        } else {
            log.info("User with email: {} found in database", email);
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getAuthorizationLevel().getName()));
        }
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
