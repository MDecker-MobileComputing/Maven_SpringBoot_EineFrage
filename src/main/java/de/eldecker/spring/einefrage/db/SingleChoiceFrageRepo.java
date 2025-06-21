package de.eldecker.spring.einefrage.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SingleChoiceFrageRepo extends JpaRepository<SingleChoiceFrageEntity, Long> {

}
