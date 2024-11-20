package aldovalzani.capstone_be.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallets_crypto")
@Getter
@Setter
@NoArgsConstructor
public class WalletCrypto {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(name = "nome_crypto")
    private String nomeCrypto;
    private double saldo; //-> indico la quantità di crypto che detiene il cliente
    private String indirizzo; //-> indirizzo della blockchain
    @Column(name = "public_key")
    private String publicKey;
    @Column(name = "private_key")
    private String privateKey;

    @ManyToOne
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;

    public WalletCrypto(String nomeCrypto, double saldo, String indirizzo, String publicKey, String privateKey, Wallet wallet) {
        this.nomeCrypto = nomeCrypto;
        this.saldo = saldo;
        this.indirizzo = indirizzo;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.wallet = wallet;
    }
}
