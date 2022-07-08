package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.configuration.Roles;
import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.UserRole;
import com.vasivuk.boardgames.model.dto.AppUserDTO;
import com.vasivuk.boardgames.repository.UserRepository;
import com.vasivuk.boardgames.repository.UserRoleRepository;
import com.vasivuk.boardgames.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Kreira novi korisnički nalog. Postavlja ulogu u sistemu na "User". Čuva nalog u bazi.
     * @param appUser korisnik
     * @return vraća sačuvanog korisnika
     */
    @Override
    public AppUser saveUser(AppUserDTO appUser) throws EntityAlreadyExistsException {
        Optional<AppUser> userFromDatabase = userRepository.findByEmail(appUser.getEmail());
        if(userFromDatabase.isPresent()) {
            throw new EntityAlreadyExistsException("User with email: " + appUser.getEmail() + " already exists.");
        }
        AppUser userToSave = new AppUser();
        userToSave.setFirstName(appUser.getFirstName());
        userToSave.setLastName(appUser.getLastName());
        userToSave.setEmail(appUser.getEmail());
        userToSave.setPassword(passwordEncoder.encode(appUser.getPassword()));
        userToSave.setUserRole(new UserRole(1L, Roles.ROLE_USER));
        log.info("Saving user {} " + userToSave.getEmail());

        return userRepository.save(userToSave);
    }

    @Override
    public AppUser getUser(String email) {
        log.info("Fetching user {}", email);
        return userRepository.findByEmail(email).get();
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public UserRole saveRole(UserRole role) {
        log.info("Saving role {}.", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        AppUser user = userRepository.findByEmail(email).get();
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
        AppUser user = userRepository.findByEmail(email).get();
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
