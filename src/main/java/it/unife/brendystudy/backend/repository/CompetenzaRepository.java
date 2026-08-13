package it.unife.brendystudy.backend.repository;

import it.unife.brendystudy.backend.model.Competenza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompetenzaRepository extends JpaRepository<Competenza, Long> {

    List<Competenza> findByUtente_IdUtenteOrderByMateria_NomeAsc(Long idUtente);

    List<Competenza> findByMateria_IdMateria(Long idMateria);

    Optional<Competenza> findByIdCompetenzaAndUtente_IdUtente(
            Long idCompetenza,
            Long idUtente
    );

    boolean existsByUtente_IdUtenteAndMateria_IdMateria(
            Long idUtente,
            Long idMateria
    );

    boolean existsByMateria_IdMateria(Long idMateria);
}