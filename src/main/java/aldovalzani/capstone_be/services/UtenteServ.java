package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.dto.UtenteDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.exceptions.NotFoundException;
import aldovalzani.capstone_be.repositories.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteServ {
    @Autowired
    private UtenteRepo utenteRepo;
    @Autowired
    private WalletServ walletServ;

    public Utente postUtente(UtenteDTO body) {
        if (utenteRepo.findByEmail(body.email()).isPresent()) {
            throw new BadRequestException("Email già in uso");
        }
        if (utenteRepo.findByUsername(body.username()).isPresent()) {
            throw new BadRequestException("Username già in uso");
        }

        Utente newUtente = this.utenteRepo.save(new Utente(body.username(), body.email(), body.password()));
        walletServ.postWalletForUtente(newUtente) ;
        return newUtente;
    }

    public Utente findUtenteById (long idCliente){
        return utenteRepo.findById(idCliente).orElseThrow(
                ()->new NotFoundException(idCliente)
        );
    }
}
