package aldovalzani.capstone_be.exceptions;


import aldovalzani.capstone_be.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class) // Tra le parentesi indico quale eccezione dovrà essere gestita da questo metodo
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorResponseDTO handleBadrequest(BadRequestException ex) {
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorResponseDTO handleUnauthorized(UnauthorizedException ex) {
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorResponseDTO handleForbidden(AuthorizationDeniedException ex) {
        return new ErrorResponseDTO("Non hai i permessi per accedere", LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorResponseDTO handleNotFound(NotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorResponseDTO handleGeneric(Exception ex) {
        ex.printStackTrace(); // Non dimentichiamoci che è estremamente utile sapere dove è stata generata un'eccezione per poterla facilmente fixare
        return new ErrorResponseDTO("Problema lato server! Giuro che risolveremo presto!", LocalDateTime.now());
    }
}
