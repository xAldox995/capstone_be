package aldovalzani.capstone_be.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransazioneDTO(
        @NotNull(message = "L'ID del wallet è obbligatorio") long idWallet,
        @NotNull(message = "La quantità è obbligatoria") @Min(value = 0, message = "La quantità deve essere maggiore di 0") double quantita,
        @NotBlank(message = "Il tipo di transazione è obbligatorio") String tipoTransazione
) {}
