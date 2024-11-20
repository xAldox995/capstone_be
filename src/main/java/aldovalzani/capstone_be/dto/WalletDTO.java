package aldovalzani.capstone_be.dto;

import jakarta.validation.constraints.NotNull;

public record WalletDTO(
        @NotNull long idUtente,
        @NotNull double importo
) {}
