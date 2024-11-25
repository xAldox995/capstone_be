package aldovalzani.capstone_be.entities.enums;

import aldovalzani.capstone_be.exceptions.BadRequestException;

public enum Ruolo {
    CLIENTE,ADMIN;

    public static Ruolo stringToEnum(String ruolo) {
        switch (ruolo.toUpperCase()) {
            case "ADMIN":
                return Ruolo.ADMIN;
            case "USER":
                return Ruolo.CLIENTE;
            default:
                if (ruolo.toUpperCase().contains(Ruolo.ADMIN.toString())) {
                    return Ruolo.ADMIN;
                } else if (ruolo.toUpperCase().contains(Ruolo.CLIENTE.toString())) {
                    return Ruolo.CLIENTE;
                } else {
                    throw new BadRequestException("Tipologia di ruolo non valido");
                }
        }
    }
}
