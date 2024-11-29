package aldovalzani.capstone_be.controllers;

import aldovalzani.capstone_be.dto.WalletCryptoDTO;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.WalletCrypto;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.services.WalletCryptoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
http://localhost:3001/wallets/cryptos
 */
@RestController
@RequestMapping("/wallets/me/cryptos")
public class WalletCryptoController {
    @Autowired
    private WalletCryptoServ walletCryptoServ;

    @PostMapping
    public WalletCrypto postWalletCrypto(@RequestBody @Validated WalletCryptoDTO body,
                                         BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload " + msg);
        }
        String utenteIdAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.walletCryptoServ.postWalletCrypto(Long.parseLong(utenteIdAutenticato), body);
    }

    @GetMapping
    public List<WalletCrypto> findAllWalletsCrypto(/*@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size*/) {
        String utenteIdAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.walletCryptoServ.findAllWalletsCrypto(Long.parseLong(utenteIdAutenticato));
    }

    @GetMapping("/{walletCryptoId}")
    public WalletCrypto getWalletCryptoById(@PathVariable long walletCryptoId) {
        return walletCryptoServ.getWalletCryptoById(walletCryptoId);
    }

    @PutMapping("/{walletCryptoId}")
    public WalletCrypto updateWalletCrypto(@PathVariable long walletCryptoId, @RequestBody WalletCryptoDTO body) {
        return walletCryptoServ.updateWalletCrypto(walletCryptoId, body);
    }

    @DeleteMapping("/{walletCryptoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWalletCrypto(@PathVariable long walletCryptoId) {
        walletCryptoServ.deleteWalletCrypto(walletCryptoId);
    }
}
