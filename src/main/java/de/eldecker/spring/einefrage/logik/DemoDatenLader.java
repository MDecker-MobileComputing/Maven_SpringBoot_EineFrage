package de.eldecker.spring.einefrage.logik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.einefrage.db.SingleChoiceFrage;
import de.eldecker.spring.einefrage.db.SingleChoiceFrageRepo;


@Component
public class DemoDatenLader implements ApplicationRunner {

	private static final Logger LOG = LoggerFactory.getLogger( DemoDatenLader.class );
	
	
	@Autowired
	private SingleChoiceFrageRepo _frageRepo;
	
	@Override
	public void run( ApplicationArguments args ) throws Exception {
		
		final long anzahlVorher = _frageRepo.count();
		LOG.info( "Anzahl Fragen in DB: {}", anzahlVorher );
		
		if ( anzahlVorher > 0 ) {
			
			LOG.info( "Datenbank enthält schon Fragen, lade deshalb keine Demo-Fragen." );
			
		} else {
			
			LOG.info( "Datenbank ist leer, lade Demo-Fragen." );
			
			SingleChoiceFrage scf1 = new SingleChoiceFrage( "hunger",
					                                        "Was soll es heute zum Mittagessen geben?", 
					                                        "Schnitzel", "Sushi", "Schaschlik", "Salamipizza" );					                                        					                                        					                                        			
			_frageRepo.save( scf1 );
		}
		
		final long anzahlNachher = _frageRepo.count();
		LOG.info( "Anzahl Fragen in DB: {}", anzahlNachher );		
	}
	
}
