package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final SuccessUserHandler successUserHandler;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserDetailsService userDetailsService,
                             SuccessUserHandler successUserHandler,
                             PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String boss = """
                hasAuthority('MASTER')
                and hasAuthority('TV_MANUFACTURER')
                and hasAuthority('PHONE_MANUFACTURER')
                and hasAuthority('REPAIRER')""";
        final String manufactureMaster = """
                hasAuthority('MASTER')
                and hasAuthority('TV_MANUFACTURER')
                and hasAuthority('PHONE_MANUFACTURER')""";

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAuthority("USER")

                .antMatchers("/headship/**").access(boss)

                .antMatchers("/manufacture/master/**").access(manufactureMaster)

                .antMatchers("/manufacture/tvs/master/**")
                    .access("hasAuthority('TV_MANUFACTURER') and hasAuthority('MASTER')")
                .antMatchers("/manufacture/tvs/**").hasAuthority("TV_MANUFACTURER")

                .antMatchers("/manufacture/phones/master/**")
                    .access("hasAuthority('PHONE_MANUFACTURER') and hasAuthority('MASTER')")
                .antMatchers("/manufacture/phones/**").hasAuthority("PHONE_MANUFACTURER")

                .antMatchers("manufacture/**").hasAnyAuthority("TV_MANUFACTURER", "PHONE_MANUFACTURER")

                .antMatchers("/repair/master/**")
                    .access("hasAuthority('REPAIRER') and hasAuthority('MASTER')")
                .antMatchers("/repair/**").hasAuthority("REPAIRER")

                .antMatchers("/**").permitAll();
        http
                .formLogin().loginPage("/login").permitAll()
                .successHandler(successUserHandler);
        http
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
