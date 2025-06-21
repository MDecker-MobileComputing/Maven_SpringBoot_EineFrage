package de.eldecker.spring.einefrage.web;

import java.util.Optional;

import static java.lang.String.format;

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
                    format( "Keine Single-Choice-Frage mit Schlüssel \"%s\" gefunden.",
                            frageSchluessel );
            
            LOG.warn( fehlermeldung );
            
            model.addAttribute( "fehlermeldung", fehlermeldung );
            
            return "fehler";
        }
    }
    
}
