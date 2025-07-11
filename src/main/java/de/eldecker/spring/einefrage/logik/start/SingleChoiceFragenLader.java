package de.eldecker.spring.einefrage.logik.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.einefrage.db.singlechoice.SingleChoiceFrageEntity;
import de.eldecker.spring.einefrage.db.singlechoice.SingleChoiceFrageRepo;


/**
 * Single-Choice-Fragen in DB-Tabelle laden, wenn die Tabelle ganz leer ist.
 */
@Component
public class SingleChoiceFragenLader implements ApplicationRunner {

	private static final Logger LOG = LoggerFactory.getLogger( SingleChoiceFragenLader.class );
	
	
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
			
			LOG.info( "Datenbank enthaelt schon SingleChoice-Fragen, lade deshalb keine Demo-Fragen." );
			return;			
		}
			
		LOG.info( "Datenbank ist leer, lade SingleChoice-Fragen." );
			
		erzeugeSC1();
		erzeugeSC2();
		erzeugeSC3();
		
		final long anzahlNachher = _frageRepo.count();
		LOG.info( "Anzahl SingleChoice-Fragen in DB nach Laden von Demo-Daten: {}", anzahlNachher );		
	}
	
	
	/**
	 * Erzeugt Single-Choice-Frage: "Was soll es heute zum Mittagessen geben?"
	 */
	private void erzeugeSC1() {
	        
	    final SingleChoiceFrageEntity scf = 
                   new SingleChoiceFrageEntity( "hunger",
                                                "Was soll es heute zum Mittagessen geben?", 
                                                "Schnitzel", "Sushi", "Schaschlik", "Salamipizza" );
        _frageRepo.save( scf );
	}

	
	/**
     * Erzeugt Single-Choice-Frage: "Welches dieser Tiere wäre das schlechteste Haustier?"
     */
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

	
    /**
     * Erzeugt Single-Choice-Frage: "Welche Datenbank soll am nächsten Vorlesungstermin behandelt werden?"
     */	
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
