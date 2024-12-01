package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.dto.WalletDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.Wallet;
import aldovalzani.capstone_be.services.WalletServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/*
http://localhost:3001/wallets
 */
@RestController
@RequestMapping("/wallets")
public class WalletController {
    @Autowired
    private WalletServ walletServ;

    @GetMapping("/me")
    public Wallet findWalletByMe(@AuthenticationPrincipal Utente utenteAutenticato){
        return walletServ.getWalletByUtente(utenteAutenticato);
    }

    @PatchMapping("/me/saldo")
    public Wallet updateWalletSaldo(@AuthenticationPrincipal Utente utenteAutenticato, @RequestBody WalletDTO body) {
        return walletServ.updateWalletImporto(utenteAutenticato, body);
    }

    @GetMapping("/{id_wallet}")
    public Wallet findWalletById(@PathVariable long id_wallet) {
        return this.walletServ.findWalletById(id_wallet);
    }
}
