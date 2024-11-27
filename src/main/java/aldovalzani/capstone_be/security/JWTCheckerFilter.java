package aldovalzani.capstone_be.security;

import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.exceptions.UnauthorizedException;
import aldovalzani.capstone_be.services.UtenteServ;
import aldovalzani.capstone_be.tools.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWT jwt;
    @Autowired
    private UtenteServ utenteServ;

    public JWTCheckerFilter(JWT jwt, UtenteServ utenteServ) {
        this.jwt = jwt;
        this.utenteServ = utenteServ;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto");
        String accessToken = authHeader.substring(7);
        jwt.verifyToken(accessToken);
        String utenteId = jwt.getIdFromToken(accessToken);
        Utente utenteCorrente = this.utenteServ.findUtenteById(Long.parseLong(utenteId));
        System.out.printf("QUi");
        System.out.printf(utenteCorrente.getAuthorities().toString());
        Authentication authentication = new UsernamePasswordAuthenticationToken(utenteId, null, utenteCorrente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
