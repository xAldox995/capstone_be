package aldovalzani.capstone_be.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UtenteDTO(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String password,
        String ruolo
) {
}
