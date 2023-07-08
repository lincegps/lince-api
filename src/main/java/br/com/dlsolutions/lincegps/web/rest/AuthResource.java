package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.domain.Usuario;
import br.com.dlsolutions.lincegps.security.UserDetailsCustom;
import br.com.dlsolutions.lincegps.security.UserDetailsServiceImpl;
import br.com.dlsolutions.lincegps.security.jwt.JWTUtil;
import br.com.dlsolutions.lincegps.security.util.SecurityUtils;
import br.com.dlsolutions.lincegps.services.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthResource {

    private final JWTUtil jwtUtil;

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        final var userDetailsCustom = SecurityUtils.userDetailsCustomAuthenticated();
        final var token = jwtUtil.generateToken(userDetailsCustom.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }
}
