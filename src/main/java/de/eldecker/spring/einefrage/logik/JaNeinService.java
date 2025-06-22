package de.eldecker.spring.einefrage.logik;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.eldecker.spring.einefrage.db.janein.JaNeinFrageEntity;
import de.eldecker.spring.einefrage.db.janein.JaNeinFrageRepo;


/**
 * Bean-Klasse mit Geschäftslogik für Ja/nein-Fragen.
 */
@Service
public class JaNeinService {

    private Logger LOG = LoggerFactory.getLogger( JaNeinService.class );
    
    
    /** Repo-Bean für Zugriff auf DB-Tabelle mit Ja/Nein-Fragen. */
    @Autowired
    private JaNeinFrageRepo _jaNeinFrageRepo;
    
    
    /**
     * Antwort auf Ja/Nein-Frage mit pessimistischer Zeilensperre verbuchen.
     * <br><br>
     * 
     * Diese Methode muss mit {@code Transactional} annotiert sein.
     * 
     * @param frageSchluessel ID/Key der Ja/Nein-Frage
     * 
     * @param istJa {@code true} für Ja, {@code false} für Nein
     * 
     * @return aktualisierte Ja/Nein-Frage mit verbuchter Antwort;
     *         wird nicht {@code null} sein
     * 
     * @throws UmfrageException wenn die Frage nicht gefunden wurde oder
     *                          wenn die Antwort nicht verbucht werden konnte
     *                          (weil nicht rechtzeitig eine Zeilensperre
     *                           erhalten werden konnte).
     */
    @Transactional
    public JaNeinFrageEntity verbucheAntwort( String frageSchluessel, boolean istJa ) 
                throws UmfrageException {
     
        try {
            
            final Optional<JaNeinFrageEntity> jaNeinFrageOptional = 
                    _jaNeinFrageRepo.findWithLockingById( frageSchluessel ); // Sperre anfordern
            
            if ( jaNeinFrageOptional.isEmpty() ) {
                
                throw new UmfrageException( 
                        "Ja/Nein-Frage mit ID '" + frageSchluessel + "' nicht gefunden." );
            }
        
            final JaNeinFrageEntity jaNeinFrage = jaNeinFrageOptional.get();
            
            if ( istJa ) {
                
                jaNeinFrage.inkrementJa();
                
            } else {
                
                jaNeinFrage.inkrementNein();
            }
        
            final JaNeinFrageEntity ergebnisEntity = _jaNeinFrageRepo.save( jaNeinFrage ); // Sperre freigeben
            
            LOG.info( "Antwort auf Ja/Nein-Frage \"{}\" verbucht: {}", 
                      frageSchluessel, istJa ? "Ja" : "Nein" );
            
            return ergebnisEntity;
        }
        catch ( Exception ex ) { // Exception wenn Sperre nicht rechtzeitig erhalten wurde
            
            LOG.error( "Fehler beim Verbuchen der Antwort auf Ja/Nein-Frage mit ID='{}'.",
                       frageSchluessel, ex );

            final String msg = 
                    "Fehler beim Lesen der Ja/Nein-Frage mit ID='" + frageSchluessel + "': " + 
                    ex.getMessage();
            
            throw new UmfrageException( msg );
        }
    }
    
}
