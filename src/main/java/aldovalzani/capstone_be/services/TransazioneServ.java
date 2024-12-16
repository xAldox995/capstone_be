package aldovalzani.capstone_be.services;

import aldovalzani.capstone_be.dto.TransazioneDTO;
import aldovalzani.capstone_be.entities.Transazione;
import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.Wallet;
import aldovalzani.capstone_be.entities.WalletCrypto;
import aldovalzani.capstone_be.entities.enums.TipoTransazione;
import aldovalzani.capstone_be.exceptions.BadRequestException;
import aldovalzani.capstone_be.exceptions.NotFoundException;
import aldovalzani.capstone_be.repositories.TransazioneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransazioneServ {
    @Autowired
    private TransazioneRepo transazioneRepo;
    @Autowired
    private WalletCryptoServ walletCryptoServ;
    @Autowired
    private WalletServ walletServ;
    @Autowired
    private CryptoCompareServ cryptoCompareServ;

    public Transazione postTransazione(Utente utenteAutenticato, TransazioneDTO body, String symbol, String currency) {
        //RECUPERO IL WALLET
        Wallet walletFound = walletServ.getWalletByUtente(utenteAutenticato);

        //OTTENGO IL PREZZO CORRENTE RICAVANDO DALL'API LA MAPPA (<SYMBOLO,PREZZP>) E POI DA LI' PRENDO IL PREZZO CORRENTE
        if (symbol == null) throw new BadRequestException("Il simbolo della cripto è obbligatorio");
        Map<String, Double> cryptoMap = cryptoCompareServ.getCryptoPrice(symbol, currency);
        Double prezzoCrypto = cryptoMap.get(currency);
        if (prezzoCrypto == null)
            throw new BadRequestException("Coppia simbolo/valuta non valida: " + symbol + "-" + currency);
        //Calcolo il valore della transazione
        double transactionValue = body.quantita() * prezzoCrypto;
        //QUI VERIFICO CHE IL WALLETCRYPTO ESISTA
        WalletCrypto walletCryptoFound;
        try {
            walletCryptoFound = walletCryptoServ.findWalletCryptoBySimbolo(utenteAutenticato, symbol);
        } catch (NotFoundException e) {
            walletCryptoFound = walletCryptoServ.postWalletCrypto(utenteAutenticato, symbol, 0);
        }
        //VERIFICO IL TIPO TRANSAZIONE ED AGGIORNO IL SALDO DEL WALLET CRYPTO DECURTANDO DAL WALLET
        TipoTransazione tipoTransazione = TipoTransazione.stringToEnum(body.tipoTransazione());
        if (tipoTransazione == TipoTransazione.ACQUISTO) {
            //IN CASO IN CUI L'IMPORTO DEL WALLET NON COPRE L'ACQUISTO
            if (walletFound.getImporto() < transactionValue) {
                throw new BadRequestException("Saldo wallet insufficiente per completare l'acquisto.");
            }
            walletCryptoFound.setSaldo(walletCryptoFound.getSaldo() + body.quantita());
            walletServ.aggiornaSaldoWallet(utenteAutenticato, -transactionValue);
        } else if (tipoTransazione == TipoTransazione.VENDITA) {
            //IN CASO DI VENDITA MI ACCERTO DI AVERE LA QUANTITA' DI CRYPTO DA VENDERE
            if (walletCryptoFound.getSaldo() < body.quantita()) {
                throw new BadRequestException("Saldo crypto insufficiente per completare la vendita.");
            }
            walletCryptoFound.setSaldo(walletCryptoFound.getSaldo() - body.quantita());
            walletServ.aggiornaSaldoWallet(utenteAutenticato, transactionValue);
        } else {
            //Altrimneti lancio un errore
            throw new BadRequestException("Tipo di transazione non valido.");
        }
        walletCryptoServ.saveWalletCrypto(walletCryptoFound);
        Transazione newTransazione = new Transazione(walletFound, walletCryptoFound, body.quantita(), prezzoCrypto, tipoTransazione);
        return transazioneRepo.save(newTransazione);

    }

}
