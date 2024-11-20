package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.Wallet;
import aldovalzani.capstone_be.services.UtenteServ;
import aldovalzani.capstone_be.services.WalletServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
http://localhost:3001/wallets
 */
@RestController
@RequestMapping("/wallets")
public class WalletController {
    @Autowired
    private WalletServ walletServ;

    @GetMapping("/{id_wallet}")
    public Wallet findWalletById(@PathVariable long id_wallet) {
        return this.walletServ.findWalletById(id_wallet);
    }
}
