package aldovalzani.capstone_be.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transazioni")
@Getter
@Setter
@NoArgsConstructor
public class Transazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;
    @ManyToOne
    @JoinColumn(name = "id_crypto")
    private WalletCrypto walletCrypto;
    private double quantita;
    private double prezzo;
    @Column(name = "data_acquisto")
    private LocalDateTime dataAcquisto;

    public Transazione(Wallet wallet, WalletCrypto walletCrypto, double quantita, double prezzo) {
        this.wallet = wallet;
        this.walletCrypto = walletCrypto;
        this.quantita = quantita;
        this.prezzo = prezzo;
        this.dataAcquisto = LocalDateTime.now();
    }
}
