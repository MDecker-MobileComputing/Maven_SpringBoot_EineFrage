package de.eldecker.spring.einefrage.logik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.einefrage.db.SingleChoiceFrageEntity;
import de.eldecker.spring.einefrage.db.SingleChoiceFrageRepo;


/**
 * Demo-Daten in DB-Tabelle laden, wenn die Tabelle ganz leer ist.
 */
@Component
public class DemoDatenLader implements ApplicationRunner {

	private static final Logger LOG = LoggerFactory.getLogger( DemoDatenLader.class );
	
	/** Repo-Bean für Zugriff auf Tabelle {@code SINGLE_CHOICE_FRAGE}. */
	@Autowired
	private SingleChoiceFrageRepo _frageRepo;
	
	
	/**
	 * Diese Methode wird unmittelbar nach Start der Anwendung ausgeführt.
	 */
	@Override
	public void run( ApplicationArguments args ) throws Exception {
		
		final long anzahlVorher = _frageRepo.count();
		LOG.info( "Anzahl Fragen in DB: {}", anzahlVorher );
		
		if ( anzahlVorher > 0 ) {
			
			LOG.info( "Datenbank enthält schon Fragen, lade deshalb keine Demo-Fragen." );
			
		} else {
			
			LOG.info( "Datenbank ist leer, lade Demo-Fragen." );
			
			erzeugeSC1();
			erzeugeSC2();
			erzeugeSC3();
		}
		
		final long anzahlNachher = _frageRepo.count();
		LOG.info( "Anzahl Fragen in DB: {}", anzahlNachher );		
	}
	
	
	private void erzeugeSC1() {
	        
	    final SingleChoiceFrageEntity scf = 
                   new SingleChoiceFrageEntity( "hunger",
                                                "Was soll es heute zum Mittagessen geben?", 
                                                "Schnitzel", "Sushi", "Schaschlik", "Salamipizza" );
        _frageRepo.save( scf );
	}
	
	private void erzeugeSC2() {
	    
	    final SingleChoiceFrageEntity scf = 
	            new SingleChoiceFrageEntity( "haustier",
	                                         "Welches dieser Tiere wäre das schlechteste Haustier?", 
	                                         "Ein Tintenfisch mit Schluckauf", 
	                                         "Ein hyperaktives Nutria", 
	                                         "Ein stotternder Papagei", 
	                                         "Ein Eichhörnchen mit Koffeinsucht" );
	    _frageRepo.save( scf );
	}
	
	private void erzeugeSC3() {
	        
	    final SingleChoiceFrageEntity scf = 
	                new SingleChoiceFrageEntity( "db-thema",
	                                             "Welche Datenbank soll am nächsten Vorlesungstermin behandelt werden?", 
	                                             "Cassandra", 
	                                             "Redis", 
	                                             "MariaDB", 
	                                             "MongoDB" );
	    _frageRepo.save( scf );
	}
	
}
