package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.dto.TransazioneDTO;
import aldovalzani.capstone_be.dto.WalletCryptoDTO;
import aldovalzani.capstone_be.entities.Transazione;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.Wallet;
import aldovalzani.capstone_be.entities.WalletCrypto;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.repositories.TransazioneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransazioneServ {
    @Autowired
    private TransazioneRepo transazioneRepo;
    @Autowired
    private WalletCryptoServ walletCryptoServ;
    @Autowired
    private WalletServ walletServ;

    public Transazione postTransazione(Utente utenteAutenticato, long walletCryptoId, TransazioneDTO body){
        WalletCrypto walletCryptoFound = walletCryptoServ.getWalletCryptoById(utenteAutenticato, walletCryptoId);
        Wallet walletFound = walletServ.getWalletByUtente(utenteAutenticato);
        if (walletCryptoFound.getSaldo()< body.quantita()){
          throw new BadRequestException("Saldo insufficiente");
        }
        Transazione newTransazione = new Transazione(walletFound,walletCryptoFound, body.quantita(), body.prezzo());

        walletCryptoFound.setSaldo(walletCryptoFound.getSaldo()- body.quantita());
        walletCryptoServ.updateWalletCrypto(utenteAutenticato,walletCryptoId, new WalletCryptoDTO(
                walletCryptoFound.getWallet().getId(),walletCryptoFound.getNomeCrypto(),walletCryptoFound.getSimbolo() ,walletCryptoFound.getSaldo()
        ));
        return transazioneRepo.save(newTransazione);
    }
}
