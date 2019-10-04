package com.ditra.ditraschool.config;


import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> opt = Optional.of("seedUser");

        return opt;
    }

}