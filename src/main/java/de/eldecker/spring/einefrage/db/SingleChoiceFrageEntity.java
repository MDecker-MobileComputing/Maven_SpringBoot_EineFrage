package de.eldecker.spring.einefrage.db;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;


/**
 * Diese Klasse definiert eine Tabelle, in der jede Zeile für eine
 * einzelne Single-Choice-Frage steht.
 * <br><br>
 * 
 * Die müssen zumindest die ersten beiden Fragen gesetzt sein;
 * wenn Frage 4 gesetzt ist, dann muss auch Frage 3 gesetzt sein
 * (also keine Lücken).
 */
@Entity
@Table( name = "SINGLE_CHOICE_FRAGE" )
public class SingleChoiceFrageEntity {

	/** ID ist ein String, muss deshalb selbst gesetzt werden. */
    @Id
    private String id;

    private String fragetext;

	private String antwort1text = "";
    private String antwort2text = "";
    private String antwort3text = "";
    private String antwort4text = "";

    private int antwort1zaehler = 0;
    private int antwort2zaehler = 0;
    private int antwort3zaehler = 0;
    private int antwort4zaehler = 0;
    
    private LocalDateTime zeitpunktErzeugung;
    private LocalDateTime zeitpunktLetzteAntwort;
    
    /**
     * Version für Optimistic Locking; wird automatisch von JPA verwaltet.
     * Annotation {@code Version} aus Paket {@code jakarta.persistence},
     * nicht {@code org.springframework.data.annotation.Version}.
     */
	@Version
	private Long version;


	/**
	 * Default-Konstruktor, obligatorisch für <i>Spring Data JPA</i>
	 */
	public SingleChoiceFrageEntity() {}

	public SingleChoiceFrageEntity( String id, String frageText,
	                                String antwort1text, String antwort2text, String antwort3text, String antwort4text ) {

		this.id        = id;
		this.fragetext = frageText;

		this.antwort1text = antwort1text;
		this.antwort2text = antwort2text;
		this.antwort3text = antwort3text;
		this.antwort4text = antwort4text;
		
		zeitpunktErzeugung = now();
	}


	public String getId() {

		return id;
	}
	
	public void setId( String id ) {

		this.id = id; 
	}	

	
    public String getFragetext() {

		return fragetext;
	}

	public void setFragetext( String fragetext ) {

		this.fragetext = fragetext;
	}


	public String getAntwort1text() {

		return antwort1text;
	}

	public void setAntwort1text( String antwort1text ) {

		this.antwort1text = antwort1text;
	}


	public String getAntwort2text() {

		return antwort2text;
	}

	public void setAntwort2text( String antwort2text ) {

		this.antwort2text = antwort2text;
	}


	public String getAntwort3text() {

		return antwort3text;
	}

	public void setAntwort3text( String antwort3text ) {
		this.antwort3text = antwort3text;
	}


	public String getAntwort4text() {

		return antwort4text;
	}

	public void setAntwort4text( String antwort4text ) {

		this.antwort4text = antwort4text;
	}


	public int getAntwort1zaehler() {

		return antwort1zaehler;
	}
	
	public double getAntwort1zaehlerProzent() {
	    
	    final double prozent = this.antwort1zaehler * 100.0 / getGesamtzahlAntworten();
	    return nachkommastellenAbschneiden( prozent ); 
	}

	public void setAntwort1zaehler(int antwort1zaehler) {

		this.antwort1zaehler = antwort1zaehler;
	}


	public int getAntwort2zaehler() {
	    
		return antwort2zaehler;
	}
	
	public double getAntwort2zaehlerProzent() {
	        
        final double prozent = this.antwort2zaehler * 100.0 / getGesamtzahlAntworten();
        return nachkommastellenAbschneiden( prozent ); 
	}

	public void setAntwort2zaehler( int antwort2zaehler ) {
	    
		this.antwort2zaehler = antwort2zaehler;
	}


	public int getAntwort3zaehler() {

		return antwort3zaehler;
	}
	
    public double getAntwort3zaehlerProzent() {
        
        final double prozent = this.antwort3zaehler * 100.0 / getGesamtzahlAntworten();
        return nachkommastellenAbschneiden( prozent ); 
    }
	
	public void setAntwort3zaehler( int antwort3zaehler ) {

		this.antwort3zaehler = antwort3zaehler;
	}


	public int getAntwort4zaehler() {

		return antwort4zaehler;
	}
	
    public double getAntwort4zaehlerProzent() {
        
        final double prozent = this.antwort4zaehler * 100.0 / getGesamtzahlAntworten();
        return nachkommastellenAbschneiden( prozent ); 
    }
	
	public void setAntwort4zaehler( int antwort4zaehler ) {
	    
		this.antwort4zaehler = antwort4zaehler;
	}


	public Long getVersion() {

		return version;
	}

	public void setVersion( Long version ) {
	    
		this.version = version;
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
     * Methode gibt Summe der Stimmen für alle Antwortoptionen zurück.
     * 
     * @return Gesamtanzahl der Stimmen
     */
    public int getGesamtzahlAntworten() {
    
        return antwort1zaehler + antwort2zaehler + antwort3zaehler + antwort4zaehler; 
    }
    
    
    /**
     * Nummer der letzten Antwort, die gesetzt ist, bestimmen.
     * 
     * @return Nummer der max. Antwort, entweder 2, 3 oder 4
     *         (die Fragen 1+2 müssen immer gesetzt sein).
     */
    public int getMaxAntwortNr() {
     
        if ( antwort3text == null || antwort3text.isBlank() ) {
            
            return 2;
        }
        if ( antwort4text == null || antwort4text.isBlank() ) {
            
            return 3;
        }

        return 4;
    }
    
    
    /**
     * Hilfsmethode: Gibt Text von Antwort mit {@code antwortNr} zurück.
     * 
     * @param antwortNr Antwort-Nr (1..4)
     * 
     * @return Text der Antwortoption
     */
    public String getAntwortText( int antwortNr ) {

        switch ( antwortNr ) {
        
            case 1:
                return antwort1text;
            case 2:
                return antwort2text;
            case 3:
                return antwort3text;
            case 4:
                return antwort4text;
            default:
                throw new IllegalArgumentException( "Ungültige Antwort-Nr: " + antwortNr );
        }
    }
    
    
    /**
     * Methode gibt String-Repräsentation des Objekts zurück.
     * 
     * @return String mit ID/Key der Frage
     */
    @Override
    public String toString() {
        
        return String.format(  "Single-Choice-Frage mit ID/Key \"%s\".", id );
    }
    
    
    /**
     * Schneidet von {@code inputZahl} alle Nachkommastellen nach der ersten
     * Nachkommastelle ab.
     *  
     * @param inputZahl Zahl, von der alle Nachkommastellen ab der zweiten
     *                  abgeschnitten werden sollen
     * 
     * @return {@code inputZahl} mit höchstens einer Nachkommastelle
     */
    private double nachkommastellenAbschneiden( double inputZahl ) {
        
        double gerundet = (int)(inputZahl * 10) / 10.0;
        return gerundet;
    }

}

