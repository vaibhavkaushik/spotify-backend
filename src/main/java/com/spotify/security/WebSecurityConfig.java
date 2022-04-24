package com.spotify.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;

    @Autowired
    public WebSecurityConfig(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/users/**").hasRole(ApplicationUserRole.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/songs/**").hasAuthority(ApplicationUserPermission.SONG_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/songs/**").hasAuthority(ApplicationUserPermission.SONG_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/songs/**").hasAuthority(ApplicationUserPermission.SONG_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/songs/**").hasAnyRole(ApplicationUserRole.USER.name(),ApplicationUserRole.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic();
                .formLogin();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("vaibhav")
                .password(encoder.encode("vaibhav"))
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                //.roles(ApplicationUserRole.ADMIN.name())
                .build();

        UserDetails user2 = User.builder()
                .username("nidhi")
                .password(encoder.encode("nidhi"))
                .authorities(ApplicationUserRole.USER.getGrantedAuthorities())
                //.roles(ApplicationUserRole.USER.name())
                .build();

        return new InMemoryUserDetailsManager(user1,user2);
    }
}