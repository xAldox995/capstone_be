package aldovalzani.capstone_be.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
public class Utente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private String username;
    private String email;
    private String password;

    public Utente(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
