package aldovalzani.capstone_be.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WalletCryptoDTO(
        @NotNull long idWallet,
        @NotBlank String nome,
        @NotBlank String simbolo,
        @NotNull double saldo,
        String indirizzo,
        String pubblicKey,
        String privateKey
) {}
