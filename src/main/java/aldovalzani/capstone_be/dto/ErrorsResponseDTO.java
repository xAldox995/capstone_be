package aldovalzani.capstone_be.dto;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(String message, LocalDateTime timestamp) {
}
