package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.dto.WalletCryptoDTO;
import aldovalzani.capstone_be.entities.WalletCrypto;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.services.WalletCryptoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/*
http://localhost:3001/wallets/cryptos
 */
@RestController
@RequestMapping("/wallets/cryptos")
public class WalletCryptoController {
    @Autowired
    private WalletCryptoServ walletCryptoServ;

    @PostMapping
    public WalletCrypto postWalletCrypto(@RequestBody @Validated WalletCryptoDTO body,
                                         BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload " + msg);
        }
        return this.walletCryptoServ.postWalletCrypto(body.idWallet(), body);
    }
}
