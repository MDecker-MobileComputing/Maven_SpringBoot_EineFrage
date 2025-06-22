package de.eldecker.spring.einefrage.logik;


/**
 * Klasse mit Hilfsmethoden.
 */
public class Helferlein {
        
    /**
     * Schneidet von {@code inputZahl} alle Nachkommastellen nach der ersten
     * Nachkommastelle ab.
     *  
     * @param inputZahl Zahl, von der alle Nachkommastellen ab der zweiten
     *                  abgeschnitten werden sollen
     * 
     * @return {@code inputZahl} mit höchstens einer Nachkommastelle
     */
    public static double nachkommastellenAbschneiden( double inputZahl ) {
        
        return (int)(inputZahl * 10) / 10.0;
    }
    
}
