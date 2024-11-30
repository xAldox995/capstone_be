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

    public Transazione postTransazione(Utente utenteAutenticato, long walletCryptoId, TransazioneDTO body, String tipoOperazione) {
        // RECUPERIAMO I DATI ESSENZIALI (WALLET, WALLETCRYPTO E IL VALORE DELLA TRANSAZIONE)
        Wallet walletFound = walletServ.getWalletByUtente(utenteAutenticato);
        WalletCrypto walletCryptoFound = walletCryptoServ.getWalletCryptoById(utenteAutenticato, walletCryptoId);
        double transactionValue = body.quantita() * body.prezzo();

        //CONTROLLO DI ACQUISTO SULLA CRYPTO
        if (tipoOperazione.equalsIgnoreCase("ACQUISTO")) {
            if (walletFound.getImporto() < transactionValue) {
                throw new BadRequestException("Saldo del wallet insuficcente per completare la transazione");
            }
            walletCryptoServ.aggiornaSaldoWalletCrypto(utenteAutenticato, walletCryptoId, body.quantita());
            walletServ.aggiornaSaldoWallet(utenteAutenticato, -transactionValue);
        }
        //CONTROLLO SULLA VENDITA DI CRYPTO
        else if (tipoOperazione.equalsIgnoreCase("VENDITA")) {
            if (walletCryptoFound.getSaldo() < body.quantita()) {
                throw new BadRequestException("Quantità insufficente di criptovaluta per completare la vendita");
            }
            walletCryptoServ.aggiornaSaldoWalletCrypto(utenteAutenticato, walletCryptoId, -body.quantita());
            walletServ.aggiornaSaldoWallet(utenteAutenticato, transactionValue);
        }
        // CASO DI OPERAZIONE NON VALIDA
        else {
            throw new BadRequestException("Tipo di operazione non valida. Deve essere un acquisto o una vendita");
        }
        //CREO E SALVO LA TRANSAZIONE
        Transazione newTransazione =new Transazione(walletFound,walletCryptoFound, body.quantita(), body.prezzo());
        return transazioneRepo.save(newTransazione);
    }

}
