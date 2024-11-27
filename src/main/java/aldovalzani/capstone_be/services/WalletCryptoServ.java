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
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UtenteServ utenteServ;
    @Autowired
    private AuthService authService;
    @Autowired
    private KeyGenerator keyGenerator;

    public WalletCrypto postWalletCrypto(long walletId, WalletCryptoDTO body) {
        Wallet walletfound = walletServ.findWalletById(walletId);
        authService.validateUserAccessToWallet(walletfound);
        walletCryptoRepo.findByNomeCryptoAndWallet_Id(body.nome(), walletId).
                ifPresent(walletCrypto -> {
                    throw new BadRequestException("La cryptovaluta è già presente ne wallet");
                });
        KeyPair keyPair = keyGenerator.generateKeyPair();
        String indirizzo = keyGenerator.generateAddress(keyPair.getPublic());

        WalletCrypto newWalletCrypto = new WalletCrypto();
        newWalletCrypto.setNomeCrypto(body.nome());
        newWalletCrypto.setSaldo(body.saldo());
        newWalletCrypto.setIndirizzo(indirizzo);
        newWalletCrypto.setPublicKey(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        newWalletCrypto.setPrivateKey(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        newWalletCrypto.setWallet(walletfound);

        return walletCryptoRepo.save(newWalletCrypto);
    }

    public List<WalletCrypto> findAllWalletsCrypto(long walletId/*,int page, int size*/) {
//        if (size > 10) size = 10;
//        Pageable pageable = PageRequest.of(page, size);
        Wallet walletfound = walletServ.findWalletById(walletId);
        authService.validateUserAccessToWallet(walletfound);
        return this.walletCryptoRepo.findAllByWallet_id(walletId);
    }


    public WalletCrypto getWalletCryptoById(long walletCryptoId) {
        WalletCrypto walletCryptoFound = walletCryptoRepo.findById(walletCryptoId)
                .orElseThrow(() -> new NotFoundException(walletCryptoId));
        authService.validateUserAccessToWallet(walletCryptoFound.getWallet());
        return  walletCryptoFound;
    }

    public WalletCrypto updateWalletCrypto(long walletCryptoId, WalletCryptoDTO body) {
        WalletCrypto walletCrypto = getWalletCryptoById(walletCryptoId);

        authService.validateUserAccessToWallet(walletCrypto.getWallet());

        walletCrypto.setSaldo(body.saldo());
        return walletCryptoRepo.save(walletCrypto);
    }

    public void deleteWalletCrypto(long walletCryptoId) {
        WalletCrypto walletCrypto = getWalletCryptoById(walletCryptoId);
        authService.validateUserAccessToWallet(walletCrypto.getWallet());
        walletCryptoRepo.delete(walletCrypto);
    }
}


