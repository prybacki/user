package com.rybacki.user.config;

import com.rybacki.user.domain.CalculationAlgorithm;
import com.rybacki.user.domain.UserService;
import com.rybacki.user.domain.ports.out.UserProvider;
import com.rybacki.user.domain.ports.out.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserConfig {

    @Bean
    UserService userService(UserProvider userProvider, UserRepository userRepository) {
        return new UserService(
            userProvider,
            userRepository,
            new CalculationAlgorithm());
    }

}
