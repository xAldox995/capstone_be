package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.dto.WalletDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.Wallet;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.exceptions.NotFoundException;
import aldovalzani.capstone_be.repositories.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServ {
    @Autowired
    private WalletRepo walletRepo;

    public Wallet postWalletForUtente(Utente utente) {
        if (walletRepo.findByUtente_Id(utente.getId()).isPresent()) {
            throw new BadRequestException("L'utente con id " + utente.getId() + " ha già un wallet");
        }
        Wallet newWallet = new Wallet();
        newWallet.setUtente(utente);
        newWallet.setImporto(0.0);
        return walletRepo.save(newWallet);
    }

    public Wallet findWalletById(long idWallet) {
        return walletRepo.findById(idWallet).orElseThrow(
                () -> new NotFoundException(idWallet)
        );
    }

    public Wallet findByUtenteId(long idUtente) {
        return walletRepo.findByUtente_Id(idUtente).orElseThrow(
                () -> new NotFoundException("Wallet non trovato per id utente: " + idUtente)
        );
    }

    public Wallet getWalletByUtente(Utente utenteAutenticato) {
        return walletRepo.findByUtente_Id(utenteAutenticato.getId())
                .orElseThrow(() -> new NotFoundException(utenteAutenticato.getId()));
    }

    public Wallet updateWalletImporto(Utente utenteAutenticato, WalletDTO body) {
        Wallet wallet = getWalletByUtente(utenteAutenticato);
        wallet.setImporto(body.importo());
        return walletRepo.save(wallet);
    }

    public Wallet aggiornaSaldoWallet(Utente utenteAutenticato, double variazioneImporto) {
        Wallet wallet = getWalletByUtente(utenteAutenticato);
        wallet.setImporto(wallet.getImporto() + variazioneImporto);
        return walletRepo.save(wallet);
    }


}
