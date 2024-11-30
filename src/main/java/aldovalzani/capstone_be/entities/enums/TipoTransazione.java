package aldovalzani.capstone_be.entities.enums;

import aldovalzani.capstone_be.exceptions.BadRequestException;

public enum TipoTransazione {
    ACQUISTO,VENDITA;

    public static TipoTransazione stringToEnum(String tipoTransazione) {
        switch (tipoTransazione.toUpperCase()) {
            case "ACQUISTO":
                return TipoTransazione.ACQUISTO;
            case "VENDITA":
                return TipoTransazione.VENDITA;
            default:
                if (tipoTransazione.toUpperCase().contains(TipoTransazione.ACQUISTO.toString())) {
                    return TipoTransazione.ACQUISTO;
                } else if (tipoTransazione.toUpperCase().contains(TipoTransazione.VENDITA.toString())) {
                    return TipoTransazione.VENDITA;
                } else {
                    throw new BadRequestException("Tipologia di Transazione non valida");
                }
        }
    }
}
