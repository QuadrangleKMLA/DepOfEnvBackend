package com.quadrangle.depofenvkmla.Security.JWT;

import com.quadrangle.depofenvkmla.Documents.User.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${quadrangle.app.jwtSecret}")
    private String jwtSecret;

    @Value("${quadrangle.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(@NonNull Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Contract(" -> new")
    private @NonNull Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey((key())).build()
                .parseClaimsJwt(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException exception) {
            logger.error("Invalid JWT token : {}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            logger.error("Expired JWT token : {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            logger.error("Unsupported JWT token : {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.error("Empty JWT token string : {}", exception.getMessage());
        }

        return false;
    }
}
