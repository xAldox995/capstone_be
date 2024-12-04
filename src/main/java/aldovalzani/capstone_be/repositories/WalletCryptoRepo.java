package aldovalzani.capstone_be.repositories;

import aldovalzani.capstone_be.entities.WalletCrypto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletCryptoRepo extends JpaRepository<WalletCrypto, Long> {
    Optional<WalletCrypto> findBySimboloAndWallet_Id(String simbolo, long walletId);

    List<WalletCrypto> findAllByWallet_id(long walletId);
}
