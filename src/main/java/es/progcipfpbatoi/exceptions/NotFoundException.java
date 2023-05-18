package es.progcipfpbatoi.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException(String tipo) {
        super("La entidad " +  tipo + " identificador no ha sido encontrada");
    }

}

