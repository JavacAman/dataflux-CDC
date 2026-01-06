//endpoints
package com.dataflux.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser user) {
        if (user == null) {
            return "Hello Guest! Please login via Keycloak.";
        }
        return "Welcome " + user.getClaim("email");
    }

    @GetMapping("/public/health")
    public String health() {
        return "Auth service is UP";
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal OidcUser user) {
        return user.getClaims();
    }
}

