package aldovalzani.capstone_be.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransazioneDTO(
        @NotNull long idWallet,
        @NotNull long idCrypto,
        @NotNull double quantita,
        @NotNull double prezzo,
        @NotBlank String tipoTransazione
) {}
