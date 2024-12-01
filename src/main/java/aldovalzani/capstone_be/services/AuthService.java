package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.Wallet;
import aldovalzani.capstone_be.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteServ utenteServ;

    public void validateUserAccessToWallet(Wallet wallet){

        String idAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();
        Utente utenteAutenticato = utenteServ.findUtenteById(Long.parseLong(idAutenticato));

        if (wallet.getUtente().getId() != utenteAutenticato.getId()) {
            throw new UnauthorizedException("Non sei autorizzato ad accedere a questo wallet");
        }
    }
}
