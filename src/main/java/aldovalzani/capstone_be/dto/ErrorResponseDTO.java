package aldovalzani.capstone_be.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String message, LocalDateTime timestamp) {
}
