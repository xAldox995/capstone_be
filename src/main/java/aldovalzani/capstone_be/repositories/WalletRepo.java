package aldovalzani.capstone_be.repositories;

import aldovalzani.capstone_be.entities.Utente;
import aldovalzani.capstone_be.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface WalletRepo extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUtente(Utente utente);
}
