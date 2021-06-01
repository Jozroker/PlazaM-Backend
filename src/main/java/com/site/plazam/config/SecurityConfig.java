package com.site.plazam.config;

import com.site.plazam.domain.User;
import com.site.plazam.repository.UserRepository;
import com.site.plazam.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final
    UserDetailsServiceImpl userDetailsService;
    final
    CustomAuthenticationProvider authenticationProvider;
    final UserRepository userRepository;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                          CustomAuthenticationProvider authenticationProvider,
                          UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
    }

    @Bean
    @Lazy
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
//                .antMatchers("/movie/order/**").authenticated()
//                .antMatchers("/resources/**", "/register", "/login",
//                        "/schedule/**", "/", "/movies/**", "/movie/**",
//                        "/home", "/authorization").permitAll()
//                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/authorize")
                .usernameParameter("emailOrUsername")
                .passwordParameter("loginPassword")
                .loginProcessingUrl("/login")
                .permitAll()
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    User user =
                            userRepository.findFirstByUsernameOrEmail(authentication.getName(), authentication.getName()).orElse(null);
                    if (user != null) {
                        String lang;
                        if (user.getSelectedLang().name().equals("UKR")) {
                            lang = "uk";
                        } else if (user.getSelectedLang().name().equals("ENG")) {
                            lang = "en";
                        } else {
                            lang = "pl";
                        }
                        httpServletResponse.sendRedirect("/home?language=" + lang);
                    }

                })
//                .defaultSuccessUrl("/home")
                .failureUrl("/authorize")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/home")
                .permitAll();
//                .and()
//                .exceptionHandling().accessDeniedPage("/access_denied");
    }


}
