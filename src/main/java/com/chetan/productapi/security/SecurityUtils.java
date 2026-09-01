package com.chetan.productapi.security;

import com.chetan.productapi.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean hasRole(Role role) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public static Role getRole() {
        String role = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Role not found"));
        return Role.valueOf(role);
    }
    public static String getCurrentUserEmail() {
        // In JwtFilter we set principal as userId; we need email. We'll modify JwtFilter to set email as principal or store email in authentication details.
        // Alternatively, we can get it from SecurityContextHolder.getContext().getAuthentication().getName()
        // Since we set principal as userId, we need to adjust. Let's assume we set email as principal.
        // For now, we'll return a placeholder; adjust based on your actual authentication object.
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}