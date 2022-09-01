package com.vasivuk.boardgames.service.impl;

import com.vasivuk.boardgames.configuration.UserRole;
import com.vasivuk.boardgames.exception.EntityAlreadyExistsException;
import com.vasivuk.boardgames.exception.EntityNotFoundException;
import com.vasivuk.boardgames.model.AppUser;
import com.vasivuk.boardgames.model.dto.AppUserDTO;
import com.vasivuk.boardgames.repository.UserRepository;
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
        userToSave.setUserRole(UserRole.USER);
        userToSave.setCountry(appUser.getCountry());
        userToSave.setCity(appUser.getCity());
        userToSave.setAddress(appUser.getAddress());
        log.info("Saving user {} ", userToSave.getEmail());

        return userRepository.save(userToSave);
    }

    @Override
    public AppUser findUserByEmail(String email) throws EntityNotFoundException {
        Optional<AppUser> optUser = userRepository.findByEmail(email);
        if(optUser.isEmpty()) {
            throw new EntityNotFoundException("User with email: " + email + " doesn't exist");
        }
        log.info("Fetching user {}", email);
        return optUser.get();
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public AppUser findUserById(Long id) throws EntityNotFoundException {
        Optional<AppUser> optUser = userRepository.findById(id);
        if(optUser.isEmpty()) {
            throw new EntityNotFoundException("User with id: " + id + " doesn't exist");
        }
        return optUser.get();
    }

    @Override
    public void assignAdminRoleToUser(Long id) throws EntityNotFoundException {
        Optional<AppUser> optUser = userRepository.findById(id);
        if(optUser.isEmpty()) {
            throw new EntityNotFoundException("User with id: " + id + " doesn't exist");
        }
        optUser.get().setUserRole(UserRole.ADMIN);
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
        Optional<AppUser> user = userRepository.findByEmail(email);
        log.info(email);
        Collection<SimpleGrantedAuthority> authorities;
        if (user.isEmpty()) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User with email: {} found in database", email);
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.get().getUserRole()));
        }
        return new User(user.get().getEmail(), user.get().getPassword(), authorities);
    }
}
