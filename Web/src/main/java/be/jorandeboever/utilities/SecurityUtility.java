package be.jorandeboever.utilities;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtility {

    private SecurityUtility() {
    }

    public static boolean isUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> "USER".equals(a.getAuthority()));
    }
}
