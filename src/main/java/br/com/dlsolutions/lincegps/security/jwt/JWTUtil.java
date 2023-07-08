package br.com.dlsolutions.lincegps.security.jwt;

import br.com.dlsolutions.lincegps.security.UserDetailsCustom;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    static final long EXPIRATION_TIME = 86400000;
    static final String SECRET = "JWtlincegpsbrasil";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaim(token);
        if(Objects.nonNull(claims)) {
            final var username = claims.getSubject();
            final var expritaionDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if(Objects.nonNull(username) && Objects.nonNull(expritaionDate) && now.before(expritaionDate)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaim(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            return null;
        }

    }

    public String getUsername(String token) {
        Claims claims = getClaim(token);
        if(Objects.nonNull(claims)) {
            return claims.getSubject();
        }
        return null;
    }
}
