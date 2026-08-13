package it.unife.brendystudy.backend.controller;

import it.unife.brendystudy.backend.model.Appuntamento;
import it.unife.brendystudy.backend.model.enums.ModalitaAppuntamento;
import it.unife.brendystudy.backend.service.AppuntamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppuntamentoController {

    private final AppuntamentoService appuntamentoService;

    public AppuntamentoController(
            AppuntamentoService appuntamentoService
    ) {
        this.appuntamentoService = appuntamentoService;
    }

    @GetMapping("/appuntamenti")
    public ResponseEntity<?> getAppuntamenti() {

        try {

            List<Appuntamento> appuntamenti =
                    appuntamentoService.findAll();

            return ResponseEntity.ok(appuntamenti);

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "erroreMsg",
                                    e.getMessage()
                            )
                    );
        }
    }

    @GetMapping("/utenti/{idUtente}/appuntamenti")
    public ResponseEntity<?> getAppuntamentiByUtente(
            @PathVariable Long idUtente
    ) {

        try {

            List<Appuntamento> appuntamenti =
                    appuntamentoService.findByUtente(idUtente);

            return ResponseEntity.ok(appuntamenti);

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "erroreMsg",
                                    e.getMessage()
                            )
                    );
        }
    }

    @PostMapping("/appuntamenti")
    public ResponseEntity<?> createAppuntamento(
            @RequestBody Map<String, Object> body
    ) {

        try {

            LocalDate dataAppuntamento =
                    LocalDate.parse(
                            body.get("dataAppuntamento").toString()
                    );

            LocalTime oraAppuntamento =
                    LocalTime.parse(
                            body.get("oraAppuntamento").toString()
                    );

            ModalitaAppuntamento modalita =
                    ModalitaAppuntamento.valueOf(
                            body.get("modalita").toString()
                    );

            Object descrizioneObj = body.get("descrizione");

            String descrizione =
                    descrizioneObj == null
                            ? null
                            : descrizioneObj.toString();

            Long idRichiesta =
                    Long.valueOf(
                            body.get("idRichiesta").toString()
                    );

            Appuntamento appuntamento =
                    appuntamentoService.create(
                            dataAppuntamento,
                            oraAppuntamento,
                            modalita,
                            descrizione,
                            idRichiesta
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(appuntamento);

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "erroreMsg",
                                    e.getMessage()
                            )
                    );
        }
    }

    @PutMapping("/appuntamenti/{idAppuntamento}/annulla")
    public ResponseEntity<?> annullaAppuntamento(
            @PathVariable Long idAppuntamento
    ) {

        try {

            boolean ok =
                    appuntamentoService.annulla(idAppuntamento);

            if (!ok) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(
                                Map.of(
                                        "erroreMsg",
                                        "Appuntamento non trovato"
                                )
                        );
            }

            return ResponseEntity.ok(
                    Map.of(
                            "annullato",
                            true
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "erroreMsg",
                                    e.getMessage()
                            )
                    );
        }
    }
}