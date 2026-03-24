package org.example.huyndth04383_lab1.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service("auth")
public class AuthService {
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isAuthenticated() {
        Authentication auth = getAuth();
        return auth != null && auth.isAuthenticated()
                && !auth.getName().equals("anonymousUser");
    }

    public String getUsername() {
        return getAuth().getName();
    }

    public List<String> getRoles() {
        return this.getAuth().getAuthorities().stream()
                .map(authority -> authority.getAuthority().substring(5))
                .toList();
    }

    public boolean hasAnyRole(String... rolesToCheck) {
        var grantedRoles = this.getRoles();
        return Stream.of(rolesToCheck).anyMatch(grantedRoles::contains);
    }
}
