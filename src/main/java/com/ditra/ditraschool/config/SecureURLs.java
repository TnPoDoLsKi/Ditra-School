package com.ditra.ditraschool.config;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SecureURLs {
    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(

        //UserController
        new AntPathRequestMatcher("/api/v1/logout"),
        new AntPathRequestMatcher("/api/v1/verifyAccess"),
        new AntPathRequestMatcher("/api/v1/article/**"),
        new AntPathRequestMatcher("/api/v1/classe/**"),
        new AntPathRequestMatcher("/api/v1/eleve/**"),
        new AntPathRequestMatcher("/api/v1/facture/**"),
        new AntPathRequestMatcher("/api/v1/global/**"),
        new AntPathRequestMatcher("/api/v1/inscription/**"),
        new AntPathRequestMatcher("/api/v1/paiement/**")
        );

    public static RequestMatcher getProtectedUrls() {
        return PROTECTED_URLS;
    }
}
