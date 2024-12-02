package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.dto.WalletCryptoDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.WalletCrypto;
import aldovalzani.capstone_be.exceptions.UnauthorizedException;
import aldovalzani.capstone_be.services.WalletCryptoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
http://localhost:3001/wallets/me/cryptos
 */
@RestController
@RequestMapping("/wallets/me/cryptos")
public class WalletCryptoController {
    @Autowired
    private WalletCryptoServ walletCryptoServ;

//    @PostMapping
//    public WalletCrypto postWalletCrypto(@AuthenticationPrincipal Utente utenteAutenticato, @RequestBody WalletCryptoDTO body) {
//        if (utenteAutenticato == null) {
//            throw new UnauthorizedException("Utente non autenticato");
//        }
//        System.out.println("Utente autenticato: " + utenteAutenticato);
//        return walletCryptoServ.postWalletCrypto(utenteAutenticato, body);
//    }

    @GetMapping
    public List<WalletCrypto> getAllWalletCryptos(@AuthenticationPrincipal Utente utenteAutenticato) {
        return walletCryptoServ.findAllWalletsCrypto(utenteAutenticato);
    }

    @GetMapping("/{walletCryptoId}")
    public WalletCrypto getWalletCryptoById(@AuthenticationPrincipal Utente utenteAutenticato, @PathVariable long walletCryptoId) {
        return walletCryptoServ.getWalletCryptoById(utenteAutenticato, walletCryptoId);
    }

    @PutMapping("/{walletCryptoId}")
    public WalletCrypto updateWalletCrypto(@AuthenticationPrincipal Utente utenteAutenticato, @PathVariable long walletCryptoId, @RequestBody WalletCryptoDTO body) {
        return walletCryptoServ.updateWalletCrypto(utenteAutenticato, walletCryptoId, body);
    }

//    @DeleteMapping("/{walletCryptoId}")
//    public void deleteWalletCrypto(@AuthenticationPrincipal Utente utenteAutenticato, @PathVariable long walletCryptoId) {
//        walletCryptoServ.deleteWalletCrypto(utenteAutenticato, walletCryptoId);
//    }
}
