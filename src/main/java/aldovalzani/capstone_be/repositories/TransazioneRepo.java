package aldovalzani.capstone_be.repositories;

import aldovalzani.capstone_be.entities.Transazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransazioneRepo extends JpaRepository<Transazione,Long> {
}
