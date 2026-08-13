package it.unife.brendystudy.backend;

import it.unife.brendystudy.backend.model.Competenza;
import it.unife.brendystudy.backend.model.Materia;
import it.unife.brendystudy.backend.model.Utente;
import it.unife.brendystudy.backend.model.enums.LivelloCompetenza;
import it.unife.brendystudy.backend.repository.CompetenzaRepository;
import it.unife.brendystudy.backend.repository.MateriaRepository;
import it.unife.brendystudy.backend.repository.UtenteRepository;
import it.unife.brendystudy.backend.service.CompetenzaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrendystudySpringBackendApplicationTests {

    private CompetenzaRepository competenzaRepository;
    private UtenteRepository utenteRepository;
    private MateriaRepository materiaRepository;

    private CompetenzaService competenzaService;

    @BeforeEach
    void setUp() {

        /*
         * Creiamo repository simulati tramite Mockito.
         * In questo modo il test verifica esclusivamente
         * la logica del CompetenzaService senza utilizzare
         * il database reale.
         */
        competenzaRepository =
                mock(CompetenzaRepository.class);

        utenteRepository =
                mock(UtenteRepository.class);

        materiaRepository =
                mock(MateriaRepository.class);

        competenzaService =
                new CompetenzaService(
                        competenzaRepository,
                        utenteRepository,
                        materiaRepository
                );
    }

    /**
     * Verifica la regola di business secondo cui
     * uno studente non può registrare due competenze
     * per la stessa materia.
     */
    @Test
    void createCompetenzaDuplicataDeveEssereRifiutata() {

        /*
         * Preparazione dei dati del test.
         */
        Long idUtente = 1L;
        Long idMateria = 1L;

        Utente utente =
                new Utente();

        utente.setIdUtente(idUtente);
        utente.setNome("Luca");
        utente.setCognome("Verdi");

        Materia materia =
                new Materia();

        materia.setIdMateria(idMateria);
        materia.setNome("Matematica");

        /*
         * Competenza già presente per Luca
         * nella materia Matematica.
         */
        Competenza competenzaEsistente =
                new Competenza();

        competenzaEsistente.setIdCompetenza(10L);
        competenzaEsistente.setUtente(utente);
        competenzaEsistente.setMateria(materia);
        competenzaEsistente.setLivello(
                LivelloCompetenza.AVANZATO
        );

        /*
         * Nuova competenza che Luca tenta
         * di inserire per la stessa materia.
         */
        Competenza nuovaCompetenza =
                new Competenza();

        nuovaCompetenza.setLivello(
                LivelloCompetenza.BASE
        );

        nuovaCompetenza.setDescrizione(
                "Seconda competenza in matematica"
        );

        /*
         * Configurazione dei repository simulati.
         */
        when(
                utenteRepository.findById(idUtente)
        ).thenReturn(
                Optional.of(utente)
        );

        when(
                materiaRepository.findById(idMateria)
        ).thenReturn(
                Optional.of(materia)
        );

        when(
                competenzaRepository
                        .findByMateria_IdMateria(idMateria)
        ).thenReturn(
                List.of(competenzaEsistente)
        );

        /*
         * Il service deve generare un'eccezione
         * perché la competenza esiste già.
         */
        IllegalArgumentException eccezione =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                competenzaService.create(
                                        nuovaCompetenza,
                                        idUtente,
                                        idMateria
                                )
                );

        /*
         * Verifichiamo anche il messaggio restituito.
         */
        assertEquals(
                "Hai già una competenza per questa materia.",
                eccezione.getMessage()
        );

        /*
         * Poiché la validazione fallisce,
         * il repository non deve mai eseguire save().
         */
        verify(
                competenzaRepository,
                never()
        ).save(
                nuovaCompetenza
        );
    }
}