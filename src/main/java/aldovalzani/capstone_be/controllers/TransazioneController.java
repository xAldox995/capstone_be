package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.dto.TransazioneDTO;
import aldovalzani.capstone_be.entities.Transazione;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.services.TransazioneServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/*
http://localhost:3001/wallets/me/cryptos/{walletCryptoId}/transactions
 */
@RestController
@RequestMapping("wallets/me/cryptos/{walletCryptoId}/transactions")
public class TransazioneController {
    @Autowired
    private TransazioneServ transazioneServ;

    @PostMapping
    public Transazione postTransazione (@AuthenticationPrincipal Utente utenteAutenticato, @PathVariable long walletCryptoId, @RequestBody TransazioneDTO body){
        return transazioneServ.postTransazione(utenteAutenticato, walletCryptoId, body);
    }
}
