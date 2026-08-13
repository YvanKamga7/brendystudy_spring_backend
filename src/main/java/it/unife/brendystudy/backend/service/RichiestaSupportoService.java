package it.unife.brendystudy.backend.service;

import it.unife.brendystudy.backend.model.Materia;
import it.unife.brendystudy.backend.model.RichiestaSupporto;
import it.unife.brendystudy.backend.model.Utente;
import it.unife.brendystudy.backend.model.enums.StatoRichiesta;
import it.unife.brendystudy.backend.repository.MateriaRepository;
import it.unife.brendystudy.backend.repository.RichiestaSupportoRepository;
import it.unife.brendystudy.backend.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RichiestaSupportoService {

    private final RichiestaSupportoRepository richiestaSupportoRepository;
    private final UtenteRepository utenteRepository;
    private final MateriaRepository materiaRepository;

    public RichiestaSupportoService(
            RichiestaSupportoRepository richiestaSupportoRepository,
            UtenteRepository utenteRepository,
            MateriaRepository materiaRepository
    ) {
        this.richiestaSupportoRepository = richiestaSupportoRepository;
        this.utenteRepository = utenteRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<RichiestaSupporto> findAll() {
        return richiestaSupportoRepository.findAll();
    }

    public List<RichiestaSupporto> findByUtente(Long idUtente) {
        return richiestaSupportoRepository
                .findByRichiedente_IdUtenteOrTutor_IdUtenteOrderByIdRichiestaDesc(
                        idUtente,
                        idUtente
                );
    }

    public RichiestaSupporto create(
            String messaggio,
            Long idRichiedente,
            Long idTutor,
            Long idMateria
    ) {

        Optional<Utente> richiedente =
                utenteRepository.findById(idRichiedente);

        Optional<Utente> tutor =
                utenteRepository.findById(idTutor);

        Optional<Materia> materia =
                materiaRepository.findById(idMateria);

        if (richiedente.isEmpty()
                || tutor.isEmpty()
                || materia.isEmpty()) {

            throw new IllegalArgumentException(
                    "Richiedente, tutor o materia non trovati"
            );
        }

        RichiestaSupporto richiesta =
                new RichiestaSupporto();

        richiesta.setMessaggio(messaggio);
        richiesta.setRichiedente(richiedente.get());
        richiesta.setTutor(tutor.get());
        richiesta.setMateria(materia.get());
        richiesta.setDataRichiesta(LocalDateTime.now());
        richiesta.setStato(StatoRichiesta.IN_ATTESA);

        return richiestaSupportoRepository.save(richiesta);
    }

    public boolean updateStato(
            Long idRichiesta,
            StatoRichiesta stato
    ) {

        Optional<RichiestaSupporto> richiesta =
                richiestaSupportoRepository.findById(idRichiesta);

        if (richiesta.isEmpty()) {
            return false;
        }

        richiesta.get().setStato(stato);

        richiestaSupportoRepository.save(
                richiesta.get()
        );

        return true;
    }
}