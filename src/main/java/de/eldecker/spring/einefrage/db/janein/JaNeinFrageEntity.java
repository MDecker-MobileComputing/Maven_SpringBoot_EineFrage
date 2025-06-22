package de.eldecker.spring.einefrage.db.janein;

import static java.lang.String.format;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Tabelle für Ja/Nein-Fragen.
 */
@Entity
@Table( name = "JANEIN_FRAGE" )
public class JaNeinFrageEntity {

    /** ID ist ein String, muss deshalb selbst gesetzt werden. */
    @Id
    private String id;

    private String fragetext = "";
    
    private int zaehlerJa = 0;
    private int zaehlerNein = 0;
    
    private LocalDateTime zeitpunktErzeugung;
    private LocalDateTime zeitpunktLetzteAntwort;
    
    
    /**
     * Default-Konstruktor, obligatorisch für Spring Data JPA
     */
    public JaNeinFrageEntity() {
    }
    
    public JaNeinFrageEntity( String id, String frageText ) {

        this.id        = id;
        this.fragetext = frageText;

        zeitpunktErzeugung = now();
    }

    public String getId() {
        
        return id;
    }

    public String getFragetext() {
        
        return fragetext;
    }

    public void setFragetext( String fragetext ) {
        
        this.fragetext = fragetext;
    }

    public int getZaehlerJa() {
        
        return zaehlerJa;
    }

    public void setZaehlerJa( int zaehlerJa ) {
        
        this.zaehlerJa = zaehlerJa;
    }

    public int getZaehlerNein() {
        
        return zaehlerNein;
    }

    public void setZaehlerNein( int zaehlerNein ) {
        
        this.zaehlerNein = zaehlerNein;
    }

    public LocalDateTime getZeitpunktErzeugung() {
        
        return zeitpunktErzeugung;
    }

    public void setZeitpunktErzeugung( LocalDateTime zeitpunktErzeugung ) {
        
        this.zeitpunktErzeugung = zeitpunktErzeugung;
    }

    public LocalDateTime getZeitpunktLetzteAntwort() {
        
        return zeitpunktLetzteAntwort;
    }

    public void setZeitpunktLetzteAntwort( LocalDateTime zeitpunktLetzteAntwort ) {
        
        this.zeitpunktLetzteAntwort = zeitpunktLetzteAntwort;
    }

    /**
     * Convenience-Methode, die die Summe der Ja- und Nein-Zähler zurückliefert.
     * 
     * @return AnzahlJa + AnzahlNein
     */
    public int getGesamtzahlAntworten() {

        return zaehlerJa + zaehlerNein;
    }
    
    /**
     * Convenience-Methode, die die Anzahl der Ja-Stimmen inkrementiert.
     * 
     * @return Anzahl der Ja-Stimmen nach Inkrementierung
     */
    public int inkrementJa() {
     
        zaehlerJa++;
        zeitpunktLetzteAntwort = now();
        return zaehlerJa;
    }

    /**
     * Convenience-Methode, die die Anzahl der Nein-Stimmen inkrementiert.
     * 
     * @return Anzahl der Nein-Stimmen nach Inkrementierung
     */
    public int inkrementNein() {
        
        zaehlerNein++;
        zeitpunktLetzteAntwort = now();
        return zaehlerNein;
    }
    
    /**
     * String-Repräsentation des aufrufenden Objekts.
     * 
     * @return String mit ID und Anzahl der Ja- und Nein-Stimmen
     */
    @Override
    public String toString() {

        return format( "Ja-Nein-Frage mit ID=\"%s\", #Ja=%d, %Nein=%d.", 
                       id, zaehlerJa, zaehlerNein );
    }
    
}
