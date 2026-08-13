package it.unife.brendystudy.backend.repository;

import it.unife.brendystudy.backend.model.RichiestaSupporto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RichiestaSupportoRepository
        extends JpaRepository<RichiestaSupporto, Long> {

    List<RichiestaSupporto>
    findByRichiedente_IdUtenteOrTutor_IdUtenteOrderByIdRichiestaDesc(
            Long idRichiedente,
            Long idTutor
    );

    boolean existsByMateria_IdMateria(Long idMateria);
}