package aldovalzani.capstone_be.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets_cryptos")
public class WalletCrypto {
    @Id
    @GeneratedValue
    private long id;
    private String nome;
    private double saldo; //-> indico la quantità di crypto che detiene il cliente
    private String indirizzo; //-> indirizzo della blockchain
    @Column(name = "public_key")
    private String publicKey;
    @Column(name = "private_key")
    private String privateKey;

    @ManyToOne
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;


}
