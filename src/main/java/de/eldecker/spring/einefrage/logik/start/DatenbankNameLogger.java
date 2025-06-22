package de.eldecker.spring.einefrage.logik.start;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * Bean, die nach dem Start der Anwendung ausgeführt wird, um ins Log zu schreiben,
 * welche Datenbank (H2 oder Postgres) gerade verwendet wird.
 * Die Annotation "@Order" sorgt dafür, dass diese Bean vor allen anderen
 * {@code CommandLineRunner}-Beans ausgeführt wird.
 */
@Component
@Order( Ordered.HIGHEST_PRECEDENCE )
public class DatenbankNameLogger implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DatenbankNameLogger.class);

    
    /** Spring-Bean für Zugriff auf Umgebungsvariablen und Profile. */
    @Autowired
    private Environment _environment;

    
    /**
     * Diese Methode wird beim Start der Anwendung aufgerufen.
     * Sie prüft, welches Profil aktiv ist und gibt eine entsprechende
     * Meldung auf den Logger aus.
     * 
     * @param args Wird nicht ausgewertet
     */
    @Override
    public void run( String... args ) throws Exception {

        final String[] activeProfiles = _environment.getActiveProfiles();

        final List<String> profilListe = Arrays.asList( activeProfiles );  
        
        String dbName = "H2";
        if ( profilListe.contains( "postgres" ) ) {

            dbName = "Postgres";
        }
        
        LOG.info( "Verwendete Datenbank: {}", dbName );
    }
    
}