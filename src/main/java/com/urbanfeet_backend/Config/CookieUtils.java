package com.urbanfeet_backend.Config;

import org.springframework.http.ResponseCookie;

public class CookieUtils {
    // Crea cookie httpOnly con SameSite configurable
    public static ResponseCookie accessCookie(String jwt, boolean secure, String domain, String sameSite,
            long maxAgeSec) {
        var b = ResponseCookie.from("ACCESS_TOKEN", jwt)
                .httpOnly(true).secure(secure).sameSite(sameSite).path("/");
        if (maxAgeSec > 0)
            b.maxAge(maxAgeSec); // si no pones maxAge => cookie de sesión
        if (domain != null && !domain.isBlank())
            b.domain(domain);
        return b.build();
    }

    public static ResponseCookie refreshCookie(String jwt, boolean secure, String domain, String sameSite,
            long maxAgeSec) {
        var b = ResponseCookie.from("REFRESH_TOKEN", jwt)
                .httpOnly(true).secure(secure).sameSite(sameSite).path("/");
        if (maxAgeSec > 0)
            b.maxAge(maxAgeSec);
        if (domain != null && !domain.isBlank())
            b.domain(domain);
        return b.build();
    }

    public static ResponseCookie delete(String name, boolean secure, String domain, String sameSite) {
        var b = ResponseCookie.from(name, "")
                .httpOnly(true).secure(secure).sameSite(sameSite).path("/").maxAge(0);
        if (domain != null && !domain.isBlank())
            b.domain(domain);
        return b.build();
    }
}
