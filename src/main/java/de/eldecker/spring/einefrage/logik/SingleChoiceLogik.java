package de.eldecker.spring.einefrage.logik;

import static java.time.LocalDateTime.now;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import de.eldecker.spring.einefrage.db.SingleChoiceFrageEntity;
import de.eldecker.spring.einefrage.db.SingleChoiceFrageRepo;


/**
 * Bean-Klasse mit Geschäftslogik für Single-Choice-Fragen.
 */
@Service
public class SingleChoiceLogik {
    
    private Logger LOG = LoggerFactory.getLogger( SingleChoiceLogik.class );
    

    /** Repo-Bean für Zugriff auf Datenbanktabelle {@code SINGLE_CHOICE_FRAGE}. */
    @Autowired
    private SingleChoiceFrageRepo _singleChoiceFrageRepo;
    
    
    /**
     * Einzelne Antwort für eine Single-Choice-Frage verbuchen.
     * 
     * @param frageSchluessel ID/Key der Single-Choice-Frage, für die eine Antwort verbucht werden soll
     * 
     * @param antwortNr Nummer der zu verbuchenden Antwort; muss eine Zahl zwischen 1 und 4 sein
     * 
     * @return Frage-Objekt mit aktualisierten Zählern für die Antworten und dem Zeitpunkt der letzten Antwort
     * 
     * @throws UmfrageException Frage nicht gefunden oder unzulässige Antwortnummer angegeben
     */
    public SingleChoiceFrageEntity verbucheAntwort( String frageSchluessel, 
                                                    int antwortNr ) throws UmfrageException {
    
        final Optional<SingleChoiceFrageEntity> singleChoiceOptional = 
                                _singleChoiceFrageRepo.findById( frageSchluessel );
        
        if ( singleChoiceOptional.isEmpty() ) {
            
            throw new UmfrageException("Keine Single-Choice-Frage mit ID \"" + frageSchluessel + "\" gefunden.");
        }
        
        final SingleChoiceFrageEntity singleChoiceFrage = singleChoiceOptional.get();
        
        if ( antwortNr > singleChoiceFrage.getMaxAntwortNr() ) {
            
            throw new UmfrageException( "Antwortnummer " + antwortNr + " ist größer als die maximale Antwortnummer "
                    + singleChoiceFrage.getMaxAntwortNr() + " für ID \"" + frageSchluessel + "\"." );
        }
        
        switch ( antwortNr ) {
        
            case 1 -> singleChoiceFrage.setAntwort1zaehler( singleChoiceFrage.getAntwort1zaehler() + 1 );
            case 2 -> singleChoiceFrage.setAntwort2zaehler( singleChoiceFrage.getAntwort2zaehler() + 1 );
            case 3 -> singleChoiceFrage.setAntwort3zaehler( singleChoiceFrage.getAntwort3zaehler() + 1 );
            case 4 -> singleChoiceFrage.setAntwort4zaehler( singleChoiceFrage.getAntwort4zaehler() + 1 );
            default -> throw new UmfrageException(
                "Illegale Antwortnummer " + antwortNr + " für ID \"" + frageSchluessel + "\".");
        }
        
        singleChoiceFrage.setZeitpunktLetzteAntwort( now() );
        
        _singleChoiceFrageRepo.save( singleChoiceFrage ); // aktualisiert Attribut "Version" automatisch
        // throws OptimisticLockingFailureException
        
        LOG.info( "Antwort {} auf Single-Choice-Frage mit ID \"{}\" verbucht.", 
                  antwortNr, frageSchluessel );
        
        return singleChoiceFrage;
    }
}
