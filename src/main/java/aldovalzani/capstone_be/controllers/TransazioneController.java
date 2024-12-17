package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.dto.TransazioneDTO;
import aldovalzani.capstone_be.entities.Transazione;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.services.TransazioneServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/*
http://localhost:3001/api/transazioni
 */
@RestController
@RequestMapping("api/transazioni")
public class TransazioneController {
    @Autowired
    private TransazioneServ transazioneServ;

    @PostMapping
    public Transazione postTransazione(
            @AuthenticationPrincipal Utente utenteAutenticato,
            @RequestParam(defaultValue = "EUR") String currency,
            @RequestParam String symbol,
            @RequestBody @Validated TransazioneDTO body,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload " + msg);
        }
        return this.transazioneServ.postTransazione(utenteAutenticato, body, symbol, currency);
    }
}
