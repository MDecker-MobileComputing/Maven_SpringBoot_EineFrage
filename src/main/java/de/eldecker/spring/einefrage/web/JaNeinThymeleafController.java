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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


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
    public String getJaNeinFrage( @PathVariable String frageSchluessel, 
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
    
    /**
     * Seite für Verbuchung einer "Ja"-Antwort auf eine Ja/Nein-Frage.
     * 
     * @param frageSchluessel ID/Key der Frage
     * 
     * @param model Objekt für Platzhalterwerte in Thymeleaf-Template
     * 
     * @return "jn-antwort-verbucht" oder "fehler"
     */
    @GetMapping( "/jn/{frageSchluessel}/ja" )
    public String verbucheJaAntwort( @PathVariable String frageSchluessel, 
                                     Model model ) {
        
        return verbucheJaNeinAntwort( frageSchluessel, model, true );
    }
    
    
    /**
     * Seite für Verbuchung einer "Nein"-Antwort auf eine Ja/Nein-Frage.
     * 
     * @param frageSchluessel ID/Key der Frage
     * 
     * @param model Objekt für Platzhalterwerte in Thymeleaf-Template
     * 
     * @return "jn-antwort-verbucht" oder "fehler"
     */
    @GetMapping( "/jn/{frageSchluessel}/nein" )
    public String verbucheNeinAntwort( @PathVariable String frageSchluessel, 
                                       Model model ) {
        
        return verbucheJaNeinAntwort( frageSchluessel, model, false );
    }
    
    /**
     * 
     * @param frageSchluessel ID/Key der Frage
     * 
     * @param model Objekt für Platzhalterwerte in Thymeleaf-Template
     * 
     * @param istJa {@code true}  für "Ja"-Antwort, 
     *              {@code false} für "Nein"-Antwort
     * 
     * @return "jn-antwort-verbucht" oder "fehler"
     */
    private String verbucheJaNeinAntwort( String frageSchluessel, 
                                          Model model, 
                                          boolean istJa ) {
        
        model.addAttribute( "fehlermeldung", "Noch nicht implementiert." );
        
        return "fehler";
    }
    
}
