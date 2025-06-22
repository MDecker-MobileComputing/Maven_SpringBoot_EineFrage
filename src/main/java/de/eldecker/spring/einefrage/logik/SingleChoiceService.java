package de.eldecker.spring.einefrage.logik;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import de.eldecker.spring.einefrage.db.singlechoice.SingleChoiceFrageEntity;
import de.eldecker.spring.einefrage.db.singlechoice.SingleChoiceFrageRepo;


/**
 * Bean-Klasse mit Geschäftslogik für Single-Choice-Fragen.
 */
@Service
public class SingleChoiceService {
    
    private Logger LOG = LoggerFactory.getLogger( SingleChoiceService.class );
    
    
    /** Max. Anzahl der Versuche für Speichern eines geänderten Datensatzes. */
    private static final int MAX_ANZAHL_VERSUCHE = 3;
    

    /** Repo-Bean für Zugriff auf Datenbanktabelle {@code SINGLE_CHOICE_FRAGE}. */
    @Autowired
    private SingleChoiceFrageRepo _singleChoiceFrageRepo;
    
    
    /**
     * Einzelne Antwort für eine Single-Choice-Frage verbuchen.
     * Implementiert einen Retry-Mechanismus für den Fall von konkurrierenden Zugriffen.
     * <br><br>
     * 
     * Retry müsste auch mit Annotation {@code @Retryable} möglich sein:
     * <pre>
     * @Retryable(
     *   value = ObjectOptimisticLockingFailureException.class,
     *   maxAttempts = 3,
     *   backoff = @Backoff(delay = 200)
     *)
     * </pre>
     * 
     * @param frageSchluessel ID/Key der Single-Choice-Frage, für die eine Antwort verbucht werden soll
     * 
     * @param antwortNr Nummer der zu verbuchenden Antwort; muss eine Zahl zwischen 1 und 4 sein
     * 
     * @return Frage-Objekt mit aktualisierten Zählern für die Antworten und dem Zeitpunkt der letzten Antwort
     * 
     * @throws UmfrageException Frage nicht gefunden, unzulässige Antwortnummer oder nach mehreren
     *                          Versuchen immer noch konkurrierender Zugriff.
     */
    public SingleChoiceFrageEntity verbucheAntwort( String frageSchluessel, 
                                                    int antwortNr ) throws UmfrageException {
    
        for ( int i = 1; i <= MAX_ANZAHL_VERSUCHE; i++ ) {
            
            try {
                
                final SingleChoiceFrageEntity entity = 
                        versucheAntwortZuVerbuchen( frageSchluessel, antwortNr );
                
                LOG.info( "Antwort {} auf Single-Choice-Frage mit ID \"{}\" verbucht, Version ist jetzt {}.", 
                          antwortNr, frageSchluessel, entity.getVersion() );
                          
                return entity;                 
            }
            catch ( ObjectOptimisticLockingFailureException ex ) { // sollte nur selten passieren
                
                LOG.warn( "Versuch {} von {} von Verbuchung von Antwort {} für Frage \"{}\" fehlgeschlagen.",
                          i, MAX_ANZAHL_VERSUCHE, antwortNr, frageSchluessel );
                
                if (i != MAX_ANZAHL_VERSUCHE) {

                    // Kurze Pause, bevor der nächste Versuch gestartet wird
                    try {
                        Thread.sleep( 200 ); // 0,2 Sekunde
                    }
                    catch ( InterruptedException ex2 ) {
                        
                        LOG.error( "Thread wurde während der Wartezeit unterbrochen.", ex2 );
                    }
                }
            }
            
        } // for

        final String fehlertext = 
                format( "Konnte Antwort %d für Frage-ID \"%s\" nach %d Versuchen nicht verbuchen.", 
                        antwortNr, frageSchluessel, MAX_ANZAHL_VERSUCHE ); 
        
        LOG.error( fehlertext );                               
        throw new UmfrageException( fehlertext ); 
    }


    /**
     * Führt einen einzelnen Versuch durch, eine Antwort zu verbuchen. Diese Methode wird in 
     * einer eigenen Transaktion ausgeführt. Wenn ein {@code ObjectOptimisticLockingFailureException}
     * auftritt, wird die Transaktion zurückgerollt und die aufrufende Methode kann einen neuen
     * Versuch durchführen.
     *
     * @param frageSchluessel ID/Key der Frage, für die eine Antwort verbucht werden soll
     * 
     * @param antwortNr Nummer der Antwort, für die der Zähler um {@code +1} erhöht werden soll
     * 
     * @return Die gespeicherte Entität bei Erfolg; wird nicht {@code null} sein
     * 
     * @throws UmfrageException bei Geschäftslogikfehlern (z.B. Frage nicht gefunden)
     * 
     * @throws ObjectOptimisticLockingFailureException wenn die Entität mittlerweile von 
     *                                                 einem anderen Prozess geändert wurde
     */
    private SingleChoiceFrageEntity versucheAntwortZuVerbuchen( String frageSchluessel, int antwortNr ) 
            throws UmfrageException, ObjectOptimisticLockingFailureException  {

        final Optional<SingleChoiceFrageEntity> singleChoiceOptional = 
                                    _singleChoiceFrageRepo.findById( frageSchluessel );
        
        if ( singleChoiceOptional.isEmpty() ) {
            
            throw new UmfrageException( "Keine Single-Choice-Frage mit ID \"" + frageSchluessel + 
                                        "\" gefunden." );
        }
        
        final SingleChoiceFrageEntity singleChoiceFrage = singleChoiceOptional.get();
        
        if ( antwortNr > singleChoiceFrage.getMaxAntwortNr() ) {
            
            throw new UmfrageException( "Antwortnummer " + antwortNr + " ist größer als die maximale Antwortnummer " + 
                                        singleChoiceFrage.getMaxAntwortNr() + " für ID \"" + frageSchluessel + "\"." );
        }
        
        switch ( antwortNr ) {

            case 1 -> singleChoiceFrage.setAntwort1zaehler( singleChoiceFrage.getAntwort1zaehler() + 1 );
            
            case 2 -> singleChoiceFrage.setAntwort2zaehler( singleChoiceFrage.getAntwort2zaehler() + 1 );
            
            case 3 -> singleChoiceFrage.setAntwort3zaehler( singleChoiceFrage.getAntwort3zaehler() + 1 );
            
            case 4 -> singleChoiceFrage.setAntwort4zaehler( singleChoiceFrage.getAntwort4zaehler() + 1 );
            
            default -> throw new UmfrageException( "Illegale Antwortnummer " + antwortNr + " für ID \"" + frageSchluessel + "\"." );
        }
        
        singleChoiceFrage.setZeitpunktLetzteAntwort( now() );
        
        return _singleChoiceFrageRepo.save( singleChoiceFrage ); // throws ObjectOptimisticLockingFailureException
    }

}
