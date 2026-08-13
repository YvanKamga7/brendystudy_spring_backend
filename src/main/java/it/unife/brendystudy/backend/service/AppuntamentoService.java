package it.unife.brendystudy.backend.service;

import it.unife.brendystudy.backend.model.Appuntamento;
import it.unife.brendystudy.backend.model.RichiestaSupporto;
import it.unife.brendystudy.backend.model.enums.ModalitaAppuntamento;
import it.unife.brendystudy.backend.model.enums.StatoAppuntamento;
import it.unife.brendystudy.backend.repository.AppuntamentoRepository;
import it.unife.brendystudy.backend.repository.RichiestaSupportoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppuntamentoService {

    private final AppuntamentoRepository appuntamentoRepository;
    private final RichiestaSupportoRepository richiestaSupportoRepository;

    public AppuntamentoService(
            AppuntamentoRepository appuntamentoRepository,
            RichiestaSupportoRepository richiestaSupportoRepository
    ) {
        this.appuntamentoRepository = appuntamentoRepository;
        this.richiestaSupportoRepository = richiestaSupportoRepository;
    }

    public List<Appuntamento> findAll() {
        return appuntamentoRepository.findAll();
    }

    public List<Appuntamento> findByUtente(Long idUtente) {
        return appuntamentoRepository
                .findByRichiesta_Richiedente_IdUtenteOrRichiesta_Tutor_IdUtenteOrderByDataAppuntamentoAscOraAppuntamentoAsc(
                        idUtente,
                        idUtente
                );
    }

    public Appuntamento create(
            LocalDate dataAppuntamento,
            LocalTime oraAppuntamento,
            ModalitaAppuntamento modalita,
            String descrizione,
            Long idRichiesta
    ) {

        if (
                dataAppuntamento == null ||
                oraAppuntamento == null
        ) {
            throw new IllegalArgumentException(
                    "Data e ora dell'appuntamento sono obbligatorie."
            );
        }

        LocalDateTime dataOraAppuntamento =
                LocalDateTime.of(
                        dataAppuntamento,
                        oraAppuntamento
                );

        if (
                !dataOraAppuntamento.isAfter(
                        LocalDateTime.now()
                )
        ) {
            throw new IllegalArgumentException(
                    "La data e l'orario dell'appuntamento devono essere nel futuro."
            );
        }

        Optional<RichiestaSupporto> richiesta =
                richiestaSupportoRepository.findById(idRichiesta);

        if (richiesta.isEmpty()) {
            throw new IllegalArgumentException(
                    "Richiesta non trovata"
            );
        }

        if (
                appuntamentoRepository
                        .existsByRichiesta_IdRichiesta(idRichiesta)
        ) {
            throw new IllegalStateException(
                    "Esiste già un appuntamento per questa richiesta."
            );
        }

        Appuntamento appuntamento =
                new Appuntamento();

        appuntamento.setDataAppuntamento(
                dataAppuntamento
        );

        appuntamento.setOraAppuntamento(
                oraAppuntamento
        );

        appuntamento.setModalita(
                modalita
        );

        appuntamento.setDescrizione(
                descrizione
        );

        appuntamento.setRichiesta(
                richiesta.get()
        );

        appuntamento.setStato(
                StatoAppuntamento.ATTIVO
        );

        return appuntamentoRepository.save(
                appuntamento
        );
    }

    public boolean annulla(
            Long idAppuntamento
    ) {

        Optional<Appuntamento> appuntamento =
                appuntamentoRepository.findById(
                        idAppuntamento
                );

        if (appuntamento.isEmpty()) {
            return false;
        }

        appuntamento.get().setStato(
                StatoAppuntamento.ANNULLATO
        );

        appuntamentoRepository.save(
                appuntamento.get()
        );

        return true;
    }
}