package com.ditra.ditraschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")


public class AuditableConfig {

    @Bean
    public AuditorAware<String> auditorAware() {

        if (SecurityContextHolder.getContext().getAuthentication() == null){
            return new AuditorAwareImpl();
        }

        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
