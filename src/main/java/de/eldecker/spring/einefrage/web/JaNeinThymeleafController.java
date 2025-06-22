package de.eldecker.spring.einefrage.web;

import static java.lang.String.format;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.eldecker.spring.einefrage.db.janein.JaNeinFrageEntity;
import de.eldecker.spring.einefrage.db.janein.JaNeinFrageRepo;


/**
 * Controller zur Erzeugung von Web-Seiten mit Template-Engine "Thymeleaf"
 * für Ja-Nein-Fragen.
 */
@Controller
public class JaNeinThymeleafController {

    private Logger LOG = LoggerFactory.getLogger( JaNeinThymeleafController.class );
    
    /** Bean für Zugriff auf DB-Tabelle mit Ja-Nein-Fragen. */
    @Autowired
    private JaNeinFrageRepo _jaNeinFrageRepo;
    
    
    /**
     * Seite für eine einzelne Ja/Nein-Frage anzeigen.
     * 
     * @param frageSchluessel ID/Key der Frage
     * 
     * @param model Objekt für Platzhalterwertete in Thymeleaf-Template
     * 
     * @return Template-Datei "jn-frage" oder "fehler" 
     */
    @GetMapping( "/jn/{frageSchluessel}" )
    public String getSingleChoiceFrage( @PathVariable String frageSchluessel, 
                                        Model model ) {
        
        final Optional<JaNeinFrageEntity> jaNeinFrageOptional = 
                                _jaNeinFrageRepo.findById( frageSchluessel );
        if ( jaNeinFrageOptional.isPresent() ) {
            
            final JaNeinFrageEntity jaNeinFrage = jaNeinFrageOptional.get();
            
            model.addAttribute( "frage", jaNeinFrage );
            
            return "jn-frage";
            
        } else {
            
            final String fehlermeldung = 
                    format( "Keine Ja/nein-Frage mit ID \"%s\" gefunden.",
                            frageSchluessel );
            
            LOG.warn( fehlermeldung );
            model.addAttribute( "fehlermeldung", fehlermeldung );
            
            return "fehler";
        }
    }
}
