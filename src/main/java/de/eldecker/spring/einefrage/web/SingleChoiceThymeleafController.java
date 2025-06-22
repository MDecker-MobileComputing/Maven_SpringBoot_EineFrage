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

import de.eldecker.spring.einefrage.db.singlechoice.SingleChoiceFrageEntity;
import de.eldecker.spring.einefrage.db.singlechoice.SingleChoiceFrageRepo;
import de.eldecker.spring.einefrage.logik.SingleChoiceLogik;
import de.eldecker.spring.einefrage.logik.UmfrageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Controller zur Erzeugung von Web-Seiten mit Template-Engine "Thymeleaf"
 * für Single-Choice-Fragen.
 */
@Controller
public class SingleChoiceThymeleafController {

    private Logger LOG = LoggerFactory.getLogger( SingleChoiceThymeleafController.class );

    
    /** Bean für Zugriff auf DB-Tabelle mit Single-Choice-Fragen. */ 
    @Autowired
    private SingleChoiceFrageRepo _singleChoiceFrageRepo; 
    
    /** Bean mit Geschäftslogik für Single-Choice-Fragen. */
    @Autowired 
    private SingleChoiceLogik _singleChoiceLogik;
    
    
    /**
     * Seite für eine einzelne Single-Choice-Frage anzeigen.
     * 
     * @param frageSchluessel ID/Key der Frage
     * 
     * @param model Objekt für Platzhalterwertete in Thymeleaf-Template
     * 
     * @return Template-Datei "sc-frage" oder "fehler" 
     */
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
     * @param frageSchluessel ID/Key der Frage
     * 
     * @param antwortNr 1-basierte Nummer der Antwort; man beachte die
     *                  beiden Annotationen {@code Min} und {@code Max}
     *                  aus der <i>Jakarta Bean Validation</i>.
     * 
     * @param model Objekt für Platzhalterwertete in Thymeleaf-Template
     * 
     * @return Template-Datei "sc-antwort-verbucht" oder "fehler" 
     */
    @GetMapping( "/sc/{frageSchluessel}/antwort/{antwortNr}" )
    public String verbucheSingleChoiceAntwort( @PathVariable String frageSchluessel, 
                                               @PathVariable @Min(1) @Max(4) int antwortNr,
                                               Model model ) {
        try {
            
            final SingleChoiceFrageEntity frageEntity = 
                    _singleChoiceLogik.verbucheAntwort( frageSchluessel, antwortNr );

            model.addAttribute( "frage"      , frageEntity );
            model.addAttribute( "antworttext", frageEntity.getAntwortText( antwortNr ) );            
            
            return "sc-antwort-verbucht";
        }
        catch ( UmfrageException ex ) {
            
            LOG.error( "Fehler beim Verbuchen der Antwort: {}", ex.getMessage() );
            model.addAttribute( "fehlermeldung", ex.getMessage() );
            return "fehler";
        }
    }
    
    
    /**
     * Anzeige der Auswertung zu einer Single-Choice-Frage: Seite zeigt an,
     * wie viele Stimmen (absolut und in Prozent) jede der Antwortoptionen 
     * bekommen hat 
     * 
     * @param frageSchluessel ID/Key der Frage
     * 
     * @param model Objekt für Platzhalterwertete in Thymeleaf-Template
     * 
     * @return Template-Datei "sc-auswertung" oder "fehler" 
     */
    @GetMapping( "/sc/auswertung/{frageSchluessel}" )    
    public String auswertungSingleChoiceFrage( @PathVariable String frageSchluessel, 
                                               Model model ) {
        
        final Optional<SingleChoiceFrageEntity> singleChoiceOptional = 
                                _singleChoiceFrageRepo.findById( frageSchluessel );

        if ( singleChoiceOptional.isPresent() ) {
        
            final SingleChoiceFrageEntity singleChoiceFrage = 
                                                singleChoiceOptional.get();
        
            model.addAttribute( "frage", singleChoiceFrage );
        
            return "sc-auswertung";
        
        } else {
        
            final String fehlermeldung = 
                    format( "Keine Single-Choice-Frage mit ID \"%s\" gefunden.",
                            frageSchluessel );
        
            LOG.warn( fehlermeldung );
            model.addAttribute( "fehlermeldung", fehlermeldung );
        
            return "fehler";
        }        
    }

}
