package br.com.dlsolutions.lincegps.security.util;

import br.com.dlsolutions.lincegps.security.UserDetailsCustom;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static Authentication getAuthentication() {
        Authentication authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(() -> new RuntimeException("Contexto da sessão não encontrado!"));
        return authentication;
    }

    public static UserDetailsCustom userDetailsCustomAuthenticated() {
       return  (UserDetailsCustom) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .orElseThrow(() -> new RuntimeException("Contexto da sessão não encontrado!"));

    }
}
