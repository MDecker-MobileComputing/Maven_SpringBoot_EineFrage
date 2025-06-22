package de.eldecker.spring.einefrage.db.singlechoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repo-Bean für Zugriff auf DB-Tabelle {@code SINGLE_CHOICE_FRAGE}.
 * <br><br>
 * 
 * Achtung: der zweite Typ-Parameter ist ein {@code String}, nicht ein
 * {@code Long}.
 */
@Repository
public interface SingleChoiceFrageRepo extends JpaRepository<SingleChoiceFrageEntity, String> {

}
