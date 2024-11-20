package aldovalzani.capstone_be.repositories;

import aldovalzani.capstone_be.entities.WalletCrypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletCryptoRepo extends JpaRepository<WalletCrypto,Long> {
    Optional<WalletCrypto> findByNomeCryptoAndWallet_Id(String nome, long walletId);
}
