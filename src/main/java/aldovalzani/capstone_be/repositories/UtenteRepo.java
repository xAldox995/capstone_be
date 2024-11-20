package aldovalzani.capstone_be.repositories;

import aldovalzani.capstone_be.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepo extends JpaRepository<Utente,Long> {
    Optional<Utente> findByUsername(String username);
    Optional<Utente> findByEmail(String email);
}
