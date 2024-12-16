package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.services.UtenteServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/*
http://localhost:3001/utenti
 */
@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteServ utenteServ;


    @GetMapping("/{id_cliente}")
    @PreAuthorize("hasAnyAuthority('CLIENTE','ADMIN')")
    public Utente findById(@PathVariable long id_cliente) {
        return this.utenteServ.findUtenteById(id_cliente);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> findAllUtenti(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "30") int size) {
        return this.utenteServ.findAllUtenti(page, size);
    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticalUtente){
        return currentAuthenticalUtente;
    }
}
