package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.UserRole;
import com.vasivuk.boardgames.repository.UserRepository;
import com.vasivuk.boardgames.repository.UserRoleRepository;
import com.vasivuk.boardgames.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }


    @Override
    public AppUser login(AppUser appUser) {
        return null;
    }

    @Override
    public void logout(AppUser appUser) {

    }

    @Override
    public AppUser getUser(String email) {
        log.info("Fetching user {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public UserRole saveRole(UserRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        AppUser user = userRepository.findByEmail(email);
        UserRole role = roleRepository.findByName(roleName);
        log.info("Assigning role: {} to user: {}", role.getName(), user.getEmail());
        user.setUserRole(role);
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
        AppUser user = userRepository.findByEmail(email);
        Collection<SimpleGrantedAuthority> authorities;
        if (user == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User with email: {} found in database", email);
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getUserRole().getName()));
        }
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
