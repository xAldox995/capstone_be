package aldovalzani.capstone_be.tools;

import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWT {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String createToken(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(utente.getId()))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public void verifyToken(String accessToken) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build().parse(accessToken);
        } catch (Exception e) {
            throw new UnauthorizedException("Problemi con il token! Per favore effettua di nuovo il login!");
        }
    }

    public String getIdFromToken(String accessToken){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseSignedClaims(accessToken)
                .getPayload().getSubject();
    }
}
