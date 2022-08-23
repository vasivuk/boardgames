package com.vasivuk.boardgames.security;

import com.vasivuk.boardgames.configuration.UserRole;
import com.vasivuk.boardgames.filter.MyAuthenticationFilter;
import com.vasivuk.boardgames.filter.MyAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import static com.vasivuk.boardgames.configuration.Routes.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter(authenticationManagerBean());
        myAuthenticationFilter.setFilterProcessesUrl(LOGIN);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //Public routes
        http.authorizeRequests().mvcMatchers(HttpMethod.GET, PUBLIC_ROUTES).permitAll();
        http.authorizeRequests().mvcMatchers(REGISTER, REFRESH_TOKEN).permitAll();
        http.authorizeRequests().mvcMatchers("/images/**").permitAll();

        //For TESTING
        //http.authorizeRequests().mvcMatchers("/**").permitAll();
        //Admin only routes
        http.authorizeRequests().mvcMatchers(HttpMethod.POST, ADMIN_POST_PUT_DELETE_ROUTES).hasAuthority(UserRole.ADMIN);
        http.authorizeRequests().mvcMatchers(HttpMethod.PUT, ADMIN_POST_PUT_DELETE_ROUTES).hasAuthority(UserRole.ADMIN);
        http.authorizeRequests().mvcMatchers(HttpMethod.DELETE, ADMIN_POST_PUT_DELETE_ROUTES).hasAuthority(UserRole.ADMIN);
        http.authorizeRequests().mvcMatchers(USER_COMMON + ID + ASSIGN_ADMIN).hasAuthority(UserRole.ADMIN);
        //Logged User routes
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(myAuthenticationFilter);
        http.addFilterBefore(new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
