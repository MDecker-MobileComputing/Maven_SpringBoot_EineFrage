package de.eldecker.spring.einefrage.db.janein;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import jakarta.persistence.QueryHint;


/**
 * Repository-Bean für Zugriff auf DB-Tabelle {@code JANEIN_FRAGE} mit
 * pessimistischen Sperren.
 */
@Repository
public interface JaNeinFrageRepo extends JpaRepository<JaNeinFrageEntity, String> {

    
    /**
     * Holt eine Ja/Nein-Frage mit Zeilensperre für <i>Pessimistic Locking</i>.
     * Timeout für Warten auf Zeilensperre: 5 Sekunden
     * 
     * @param id Key/ID der zu sperrenden Ja/Nein-Frage
     * 
     * @return Optional mit Objekt wenn gefunden und Sperre innerhalb des Timeouts
     *         erhalten wurde; 
     *         wenn nicht gefunden, dann ein leeres Optional;
     *         wenn Objekt vorhanden, aber Sperre nicht rechtzeitig erhalten,
     *         dann wird eine {@code PessimisticLockException} geworfen.
     * 
     * @throws jakarta.persistence.PessimisticLockException
     *         wenn die Zeilensperre nicht innerhalb des Timeouts erhalten werden
     */
    @Lock( PESSIMISTIC_WRITE )
    @QueryHints({ @QueryHint( name = "jakarta.persistence.lock.timeout", value = "5000") }) 
    Optional<JaNeinFrageEntity> findWithLockingById( String id );

}
