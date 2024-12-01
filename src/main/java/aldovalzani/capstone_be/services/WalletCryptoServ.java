package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.dto.WalletCryptoDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.Wallet;
import aldovalzani.capstone_be.entities.WalletCrypto;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.exceptions.NotFoundException;
import aldovalzani.capstone_be.exceptions.UnauthorizedException;
import aldovalzani.capstone_be.repositories.WalletCryptoRepo;
import aldovalzani.capstone_be.tools.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Base64;
import java.util.List;

@Service
public class WalletCryptoServ {
    @Autowired
    private WalletCryptoRepo walletCryptoRepo;
    @Autowired
    private WalletServ walletServ;
    @Autowired
    private KeyGenerator keyGenerator;

    public WalletCrypto postWalletCrypto(Utente utenteAutenticato, WalletCryptoDTO body) {
        Wallet walletfound = walletServ.getWalletByUtente(utenteAutenticato);
        walletCryptoRepo.findByNomeCryptoAndWallet_Id(body.nome(), walletfound.getId()).
                ifPresent(walletCrypto -> {
                    throw new BadRequestException("La cryptovaluta è già presente nel wallet");
                });
        KeyPair keyPair = keyGenerator.generateKeyPair();
        String indirizzo = keyGenerator.generateAddress(keyPair.getPublic());

        WalletCrypto newWalletCrypto = new WalletCrypto();
        newWalletCrypto.setNomeCrypto(body.nome());
        newWalletCrypto.setSimbolo(body.nome());
        newWalletCrypto.setSaldo(body.saldo());
        newWalletCrypto.setIndirizzo(indirizzo);
        newWalletCrypto.setPublicKey(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        newWalletCrypto.setPrivateKey(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        newWalletCrypto.setWallet(walletfound);

        return walletCryptoRepo.save(newWalletCrypto);
    }

    public List<WalletCrypto> findAllWalletsCrypto(Utente utenteAutenticato/*,int page, int size*/) {
//        if (size > 10) size = 10;
//        Pageable pageable = PageRequest.of(page, size);
        Wallet walletfound = walletServ.getWalletByUtente(utenteAutenticato);
        return this.walletCryptoRepo.findAllByWallet_id(walletfound.getId());
    }


    public WalletCrypto getWalletCryptoById(Utente utenteAutenticato, long walletCryptoId) {
        WalletCrypto walletCryptoFound = walletCryptoRepo.findById(walletCryptoId)
                .orElseThrow(() -> new NotFoundException(walletCryptoId));
        if (walletCryptoFound.getWallet().getUtente().getId() != utenteAutenticato.getId()) {
            throw new UnauthorizedException("Non sei autorizzato ad accedere a questo wallet");
        }
        return walletCryptoFound;
    }

    public WalletCrypto updateWalletCrypto(Utente utenteAutenticato, long walletCryptoId, WalletCryptoDTO body) {
        WalletCrypto walletCrypto = getWalletCryptoById(utenteAutenticato, walletCryptoId);
        walletCrypto.setSaldo(body.saldo());
        return walletCryptoRepo.save(walletCrypto);
    }

    public WalletCrypto updateWalletCryptoSaldo(Utente utenteAutenticato, long walletCryptoId, double saldo) {
        WalletCrypto walletCrypto = getWalletCryptoById(utenteAutenticato, walletCryptoId);
        walletCrypto.setSaldo(saldo);
        return walletCryptoRepo.save(walletCrypto);
    }

    public WalletCrypto aggiornaSaldoWalletCrypto(Utente utenteAutenticato, long walletCryptoId, double variazioneSaldo) {
        WalletCrypto walletCrypto = getWalletCryptoById(utenteAutenticato, walletCryptoId);
        walletCrypto.setSaldo(walletCrypto.getSaldo() + variazioneSaldo);
        return walletCryptoRepo.save(walletCrypto);
    }

    public void deleteWalletCrypto(Utente utenteAutenticato, long walletCryptoId) {
        WalletCrypto walletCrypto = getWalletCryptoById(utenteAutenticato, walletCryptoId);
        walletCryptoRepo.delete(walletCrypto);
    }
}



