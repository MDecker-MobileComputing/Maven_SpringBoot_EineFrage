package de.eldecker.spring.einefrage.db;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import jakarta.persistence.Version;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Diese Klasse definiert eine Tabelle, in der jede Zeile für eine
 * einzelne Single-Choice-Frage steht.
 */
@Entity
@Table( name = "SINGLE_CHOICE_FRAGE" )
public class SingleChoiceFrageEntity {

	/** ID ist ein String, muss deshalb selbst gesetzt werden. */
    @Id
    private String id;

    private String fragetext;

	private String antwort1text;
    private String antwort2text;
    private String antwort3text;
    private String antwort4text;

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

	public void setAntwort1zaehler(int antwort1zaehler) {

		this.antwort1zaehler = antwort1zaehler;
	}


	public int getAntwort2zaehler() {
	    
		return antwort2zaehler;
	}

	public void setAntwort2zaehler( int antwort2zaehler ) {
	    
		this.antwort2zaehler = antwort2zaehler;
	}


	public int getAntwort3zaehler() {

		return antwort3zaehler;
	}

	public void setAntwort3zaehler( int antwort3zaehler ) {

		this.antwort3zaehler = antwort3zaehler;
	}


	public int getAntwort4zaehler() {

		return antwort4zaehler;
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

}

