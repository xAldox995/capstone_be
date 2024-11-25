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



    @GetMapping("/{id_cliente}")
    public Utente findById(@PathVariable long id_cliente) {
        return this.utenteServ.findUtenteById(id_cliente);
    }
}
