package aldovalzani.capstone_be.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    @OneToOne
    @JoinColumn(name = "id_utente",referencedColumnName = "id")
    private Utente utente;
    private double importo;

    public Wallet(Utente utente, double importo) {
        this.utente = utente;
        this.importo = importo;
    }
}
