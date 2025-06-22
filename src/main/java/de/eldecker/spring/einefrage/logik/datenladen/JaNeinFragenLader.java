package de.eldecker.spring.einefrage.logik.datenladen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.einefrage.db.janein.JaNeinFrageEntity;
import de.eldecker.spring.einefrage.db.janein.JaNeinFrageRepo;


/**
 * Ja/Nein-Fragen in Tabelle laden, wenn die Tabelle ganz leer ist.
 */
@Component
public class JaNeinFragenLader implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger( JaNeinFragenLader.class );

    
    /** Repo-Bean für Zugriff auf Tabelle {@code JANEIN_FRAGE}. */
    @Autowired
    private JaNeinFrageRepo _frageRepo;
    
    
    /**
     * Diese Methode wird unmittelbar nach Start der Anwendung ausgeführt.
     */
    @Override
    public void run( ApplicationArguments args ) throws Exception {
       
        final long anzahlVorher = _frageRepo.count();
        LOG.info( "Anzahl Ja/Nein-Fragen in DB: {}", anzahlVorher );
        if ( anzahlVorher > 0 ) {
            
            LOG.info( "Datenbank enthaelt schon Fragen, lade deshalb keine Demo-Fragen." );
            return;     
        }
        

        LOG.info( "Datenbank ist leer, lade JaNein-Fragen." );
        
        erzeugeJaNeinFrage1();
        erzeugeJaNeinFrage2();
        
        final long anzahlNachher = _frageRepo.count();
        LOG.info( "Anzahl JaNein-Fragen in DB nach Laden von Demo-Daten: {}", anzahlNachher );     
    }
    
    
    /**
     * Erzeugt Ja/Nein-Frage: "Ist die Erde eine Scheibe?".
     */
    private void erzeugeJaNeinFrage1() {
        
        final JaNeinFrageEntity frage = 
                new JaNeinFrageEntity( 
                        "scheibe", "Ist die Erde eine Scheibe?" );
        
        _frageRepo.save( frage );
    }

    
    /**
     * Erzeugt Ja/Nein-Frage: 
     * "Soll die Vorlesung am Freitag von 14 auf 16 Uhr verschoben werden?".
     */
    private void erzeugeJaNeinFrage2() {
        
        final JaNeinFrageEntity frage = 
                new JaNeinFrageEntity( 
                        "verschieb", 
                        "Soll die Vorlesung am Freitag von 14 auf 16 Uhr verschoben werden?" );
        
        _frageRepo.save( frage );
    }
    
}
