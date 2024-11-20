package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.dto.UtenteDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.services.UtenteServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/*
http://localhost:3001/utenti
 */
@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteServ utenteServ;

    @PostMapping
    public Utente creatUtenteWithWallet(@RequestBody @Validated UtenteDTO body,
                                        BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload " + msg);
        }
        return this.utenteServ.postUtente(body);
    }

    @GetMapping("/{id_cliente}")
    public Utente findById(@PathVariable long id_cliente) {
        return this.utenteServ.findUtenteById(id_cliente);
    }
}