package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.dto.WalletCryptoDTO;
import aldovalzani.capstone_be.entities.Wallet;
import aldovalzani.capstone_be.entities.WalletCrypto;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.repositories.WalletCryptoRepo;
import aldovalzani.capstone_be.tools.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Base64;

@Service
public class WalletCryptoServ {
    @Autowired
    private WalletCryptoRepo walletCryptoRepo;
    @Autowired
    private WalletServ walletServ;
    @Autowired
    private KeyGenerator keyGenerator;

    public WalletCrypto postWalletCrypto(long walletId, WalletCryptoDTO body) {
        Wallet walletfound = walletServ.findWalletById(walletId);
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
}


