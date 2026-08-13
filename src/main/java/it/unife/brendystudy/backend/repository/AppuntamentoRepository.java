package it.unife.brendystudy.backend.repository;

import it.unife.brendystudy.backend.model.Appuntamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppuntamentoRepository
        extends JpaRepository<Appuntamento, Long> {

    List<Appuntamento>
    findByRichiesta_Richiedente_IdUtenteOrRichiesta_Tutor_IdUtenteOrderByDataAppuntamentoAscOraAppuntamentoAsc(
            Long idRichiedente,
            Long idTutor
    );

    boolean existsByRichiesta_IdRichiesta(Long idRichiesta);
}