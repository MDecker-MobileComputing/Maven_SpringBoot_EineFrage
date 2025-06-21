package de.eldecker.spring.einefrage.web;

import java.util.Optional;

import static java.lang.String.format;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.eldecker.spring.einefrage.db.SingleChoiceFrageEntity;
import de.eldecker.spring.einefrage.db.SingleChoiceFrageRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class ThymeleafController {

    private Logger LOG = LoggerFactory.getLogger( ThymeleafController.class );
    
    @Autowired
    private SingleChoiceFrageRepo _singleChoiceFrageRepo; 
    
    
    @GetMapping( "/sc/{frageSchluessel}" )
    public String getSingleChoiceFrage( @PathVariable String frageSchluessel, 
                                        Model model ) {

        final Optional<SingleChoiceFrageEntity> singleChoiceOptional = 
                                _singleChoiceFrageRepo.findById( frageSchluessel );
        
        if ( singleChoiceOptional.isPresent() ) {
            
            final SingleChoiceFrageEntity singleChoiceFrage = singleChoiceOptional.get();
            
            model.addAttribute( "frage", singleChoiceFrage );
            
            return "sc-frage";
            
        } else {

            final String fehlermeldung = 
                    format( "Keine Single-Choice-Frage mit ID \"%s\" gefunden.",
                            frageSchluessel );
            
            LOG.warn( fehlermeldung );
            
            model.addAttribute( "fehlermeldung", fehlermeldung );
            
            return "fehler";
        }
    }
    
    
    /**
     * Verbucht eine Antwort auf eine Single-Choice-Frage.
     * 
     * @param frageSchluessel ID der Frage
     * 
     * @param antwortNr 1-basierte Nummer der Antwort
     * 
     * @param model Objekt für Platzhalterwertete in Thymeleaf-Template
     * 
     * @return Template-Datei "antwort-verbucht" oder "fehler" 
     */
    @GetMapping( "/sc/{frageSchluessel}/antwort/{antwortNr}" )
    public String verbucheSingleChoiceAntwort( @PathVariable String frageSchluessel, 
                                               @PathVariable @Min(1) @Max(4) int antwortNr,
                                               Model model ) {
        
        final Optional<SingleChoiceFrageEntity> singleChoiceOptional = 
                            _singleChoiceFrageRepo.findById( frageSchluessel );
        
        if ( singleChoiceOptional.isEmpty() ) {
            
            final String fehlermeldung = 
                    format( "Frage mit ID \"%s\" nicht gefunden, kann Antwort nicht verbuchen.",
                            frageSchluessel );
            
            LOG.warn( fehlermeldung );
            model.addAttribute( "fehlermeldung", fehlermeldung );
            
            return "fehler";
        }
        
        final SingleChoiceFrageEntity singleChoiceFrage = singleChoiceOptional.get();
        
        if ( antwortNr > singleChoiceFrage.getMaxAntwortNr() ) {
         
            final String fehlermeldung = 
                    format( "Antwort-Nr %d ist für Frage ID \"%s\" nicht erlaubt.",
                            antwortNr, frageSchluessel );
            
            LOG.warn( fehlermeldung );
            model.addAttribute( "fehlermeldung", fehlermeldung );
            
            return "fehler";
        }
        
        // TODO: Antwort verbuchen
        
        model.addAttribute( "antworttext", singleChoiceFrage.getAntwortText( antwortNr ) );
        model.addAttribute( "fragetext"  , singleChoiceFrage.getFragetext() );
        
        return "antwort-verbucht";
    }
    
}
