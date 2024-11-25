package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.dto.LoginDTO;
import aldovalzani.capstone_be.dto.UtenteDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.exceptions.NotFoundException;
import aldovalzani.capstone_be.exceptions.UnauthorizedException;
import aldovalzani.capstone_be.repositories.UtenteRepo;
import aldovalzani.capstone_be.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteServ {
    @Autowired
    private UtenteRepo utenteRepo;
    @Autowired
    private WalletServ walletServ;
    @Autowired
    private BCryptPasswordEncoder bcrypt;
    @Autowired
    private JWT jwt;


    public Utente postUtente(UtenteDTO body) {
        if (utenteRepo.findByEmail(body.email()).isPresent()) {
            throw new BadRequestException("Email già in uso");
        }
        if (utenteRepo.findByUsername(body.username()).isPresent()) {
            throw new BadRequestException("Username già in uso");
        }

        Utente newUtente = this.utenteRepo.save(new Utente(body.username(), body.email(), bcrypt.encode(body.password())));
        walletServ.postWalletForUtente(newUtente);
        return newUtente;
    }

    public Page<Utente> findAllUtenti(int page, int size) {
        if (size > 30) size = 30;
        Pageable pageable = PageRequest.of(page, size);
        return  this.utenteRepo.findAll(pageable);
    }

    public Utente findUtenteById(long idCliente) {
        return utenteRepo.findById(idCliente).orElseThrow(
                () -> new NotFoundException(idCliente)
        );

    }

    public Utente findUtenteByEmail(String email) {
        return utenteRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("Email Utente non trovata"));
    }


    public String checkCredentialAndGenerateToken(LoginDTO body) {
        Utente found = findUtenteByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            String accessToken = jwt.createToken(found);
            return accessToken;
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
