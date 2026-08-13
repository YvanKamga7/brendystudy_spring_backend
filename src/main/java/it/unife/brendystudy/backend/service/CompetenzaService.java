package it.unife.brendystudy.backend.service;

import it.unife.brendystudy.backend.model.Competenza;
import it.unife.brendystudy.backend.model.Materia;
import it.unife.brendystudy.backend.model.Utente;
import it.unife.brendystudy.backend.model.enums.StatoUtente;
import it.unife.brendystudy.backend.repository.CompetenzaRepository;
import it.unife.brendystudy.backend.repository.MateriaRepository;
import it.unife.brendystudy.backend.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenzaService {

    private final CompetenzaRepository competenzaRepository;
    private final UtenteRepository utenteRepository;
    private final MateriaRepository materiaRepository;

    public CompetenzaService(
            CompetenzaRepository competenzaRepository,
            UtenteRepository utenteRepository,
            MateriaRepository materiaRepository
    ) {
        this.competenzaRepository = competenzaRepository;
        this.utenteRepository = utenteRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<Competenza> findByUtente(Long idUtente) {

        return competenzaRepository
                .findByUtente_IdUtenteOrderByMateria_NomeAsc(
                        idUtente
                );
    }

    public List<Competenza> findTutorByMateria(Long idMateria) {

        return competenzaRepository
                .findByMateria_IdMateria(idMateria)
                .stream()
                .filter(c ->
                        c.getUtente().getStato()
                                == StatoUtente.ATTIVO
                )
                .toList();
    }

    public Competenza create(
            Competenza competenza,
            Long idUtente,
            Long idMateria
    ) {

        Optional<Utente> utente =
                utenteRepository.findById(idUtente);

        Optional<Materia> materia =
                materiaRepository.findById(idMateria);

        if (
                utente.isEmpty() ||
                materia.isEmpty()
        ) {
            throw new IllegalArgumentException(
                    "Utente o materia non trovati."
            );
        }

        /*
         * Un utente può avere una sola competenza
         * per ogni materia.
         */
        boolean esisteGia =
                competenzaRepository
                        .findByMateria_IdMateria(idMateria)
                        .stream()
                        .anyMatch(c ->
                                c.getUtente()
                                        .getIdUtente()
                                        .equals(idUtente)
                        );

        if (esisteGia) {

            throw new IllegalArgumentException(
                    "Hai già una competenza per questa materia."
            );
        }

        competenza.setIdCompetenza(null);

        competenza.setUtente(
                utente.get()
        );

        competenza.setMateria(
                materia.get()
        );

        return competenzaRepository.save(
                competenza
        );
    }

    public boolean update(
            Long idCompetenza,
            Long idUtente,
            Long idMateria,
            Competenza dati
    ) {

        Optional<Competenza> optionalCompetenza =
                competenzaRepository
                        .findByIdCompetenzaAndUtente_IdUtente(
                                idCompetenza,
                                idUtente
                        );

        Optional<Materia> materia =
                materiaRepository.findById(
                        idMateria
                );

        if (
                optionalCompetenza.isEmpty() ||
                materia.isEmpty()
        ) {
            return false;
        }

        Competenza competenza =
                optionalCompetenza.get();

        /*
         * Se in futuro permettiamo di cambiare
         * anche la materia durante la modifica,
         * evitiamo comunque i duplicati.
         */
        boolean duplicato =
                competenzaRepository
                        .findByMateria_IdMateria(idMateria)
                        .stream()
                        .anyMatch(c ->
                                c.getUtente()
                                        .getIdUtente()
                                        .equals(idUtente)
                                &&
                                !c.getIdCompetenza()
                                        .equals(idCompetenza)
                        );

        if (duplicato) {

            throw new IllegalArgumentException(
                    "Hai già una competenza per questa materia."
            );
        }

        competenza.setLivello(
                dati.getLivello()
        );

        competenza.setDescrizione(
                dati.getDescrizione()
        );

        competenza.setMateria(
                materia.get()
        );

        competenzaRepository.save(
                competenza
        );

        return true;
    }

    public boolean delete(
            Long idCompetenza,
            Long idUtente
    ) {

        Optional<Competenza> competenza =
                competenzaRepository
                        .findByIdCompetenzaAndUtente_IdUtente(
                                idCompetenza,
                                idUtente
                        );

        if (competenza.isEmpty()) {
            return false;
        }

        competenzaRepository.delete(
                competenza.get()
        );

        return true;
    }
}