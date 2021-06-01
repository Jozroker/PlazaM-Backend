package com.site.plazam.config;

import com.site.plazam.domain.User;
import com.site.plazam.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    final UserRepository userRepository;

    final BCryptPasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserRepository userRepository,
                                        @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String usernameOrEmail = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user =
                userRepository.findFirstByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);

        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
//                String langTag;
//                if (user.getSelectedLang().name().equals("UKR")) {
//                    langTag = "uk-UA";
//                } else if (user.getSelectedLang().name().equals("ENG")) {
//                    langTag = "en-US";
//                } else {
//                    langTag = "pl-PL";
//                }

                return new UsernamePasswordAuthenticationToken(
                        usernameOrEmail, password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}