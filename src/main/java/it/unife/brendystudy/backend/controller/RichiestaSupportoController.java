package it.unife.brendystudy.backend.controller;

import it.unife.brendystudy.backend.model.RichiestaSupporto;
import it.unife.brendystudy.backend.model.enums.StatoRichiesta;
import it.unife.brendystudy.backend.service.RichiestaSupportoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RichiestaSupportoController {

    private final RichiestaSupportoService richiestaSupportoService;

    public RichiestaSupportoController(
            RichiestaSupportoService richiestaSupportoService
    ) {
        this.richiestaSupportoService =
                richiestaSupportoService;
    }

    @GetMapping("/richieste")
    public ResponseEntity<?> getRichieste() {

        try {

            List<RichiestaSupporto> richieste =
                    richiestaSupportoService.findAll();

            return ResponseEntity.ok(richieste);

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

    @GetMapping("/utenti/{idUtente}/richieste")
    public ResponseEntity<?> getRichiesteByUtente(
            @PathVariable Long idUtente
    ) {

        try {

            List<RichiestaSupporto> richieste =
                    richiestaSupportoService
                            .findByUtente(idUtente);

            return ResponseEntity.ok(richieste);

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

    @PostMapping("/richieste")
    public ResponseEntity<?> createRichiesta(
            @RequestBody Map<String, Object> body
    ) {

        try {

            String messaggio =
                    body.get("messaggio").toString();

            Long idRichiedente =
                    Long.valueOf(
                            body.get("idRichiedente").toString()
                    );

            Long idTutor =
                    Long.valueOf(
                            body.get("idTutor").toString()
                    );

            Long idMateria =
                    Long.valueOf(
                            body.get("idMateria").toString()
                    );

            RichiestaSupporto richiesta =
                    richiestaSupportoService.create(
                            messaggio,
                            idRichiedente,
                            idTutor,
                            idMateria
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(richiesta);

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

    @PutMapping("/richieste/{idRichiesta}/stato")
    public ResponseEntity<?> updateStato(
            @PathVariable Long idRichiesta,
            @RequestBody Map<String, String> body
    ) {

        try {

            StatoRichiesta stato =
                    StatoRichiesta.valueOf(
                            body.get("stato")
                    );

            boolean updated =
                    richiestaSupportoService
                            .updateStato(
                                    idRichiesta,
                                    stato
                            );

            if (!updated) {

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(
                                Map.of(
                                        "erroreMsg",
                                        "Richiesta non trovata"
                                )
                        );
            }

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "Stato richiesta aggiornato correttamente"
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