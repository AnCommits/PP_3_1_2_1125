package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("MASTER")) {
            if (roles.contains("TV_MANUFACTURER")) {
                if (roles.contains("PHONE_MANUFACTURER")) {
                    if (roles.contains("REPAIRER")) {
                        httpServletResponse.sendRedirect("/headship");
                    } else {
                        httpServletResponse.sendRedirect("/manufacture/master");
                    }
                } else {
                    httpServletResponse.sendRedirect("/manufacture/tvs/master");
                }
            } else if (roles.contains("PHONE_MANUFACTURER")) {
                httpServletResponse.sendRedirect("/manufacture/phones/master");
            } else if (roles.contains("REPAIRER")) {
                httpServletResponse.sendRedirect("/repair/master");
            } else {
                httpServletResponse.sendRedirect("/");
            }
        } else if (roles.contains("TV_MANUFACTURER")) {
            httpServletResponse.sendRedirect("/manufacture/tvs");
        } else if (roles.contains("PHONE_MANUFACTURER")) {
            httpServletResponse.sendRedirect("/manufacture/phones");
        } else if (roles.contains("REPAIRER")) {
            httpServletResponse.sendRedirect("/repair");
        } else {
            if (roles.contains("USER")) {
                httpServletResponse.sendRedirect("/user");
            } else {
                httpServletResponse.sendRedirect("/");
            }
        }
    }
}
