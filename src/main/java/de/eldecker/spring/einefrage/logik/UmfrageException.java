package de.eldecker.spring.einefrage.logik;


/**
 * Eigene Exception-Klasse
 */
@SuppressWarnings("serial")
public class UmfrageException extends Exception {

    /**
     * Konstruktor zur Erzeugung einer Exception mit Fehlerbeschreibung.
     * 
     * @param message Beschreibung des Fehlers
     */
    public UmfrageException( String message ) {
        
        super( message );
    }
}
