package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.dto.LoginDTO;
import aldovalzani.capstone_be.dto.LoginResponseDTO;
import aldovalzani.capstone_be.dto.UtenteDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.services.UtenteServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/*
http://localhost:3001/auth
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UtenteServ utenteServ;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body){
        return new LoginResponseDTO(this.utenteServ.checkCredentialAndGenerateToken(body));
    }

    @PostMapping("/register")
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
}
