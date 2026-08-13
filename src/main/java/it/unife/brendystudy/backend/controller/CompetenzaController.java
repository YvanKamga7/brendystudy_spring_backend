package it.unife.brendystudy.backend.controller;

import it.unife.brendystudy.backend.model.Competenza;
import it.unife.brendystudy.backend.model.enums.LivelloCompetenza;
import it.unife.brendystudy.backend.service.CompetenzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CompetenzaController {

    private final CompetenzaService competenzaService;

    public CompetenzaController(
            CompetenzaService competenzaService
    ) {
        this.competenzaService = competenzaService;
    }

    @GetMapping("/utenti/{idUtente}/competenze")
    public ResponseEntity<?> getCompetenzeByUtente(
            @PathVariable Long idUtente
    ) {

        try {

            List<Competenza> competenze =
                    competenzaService.findByUtente(idUtente);

            return ResponseEntity.ok(competenze);

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

    @GetMapping("/materie/{idMateria}/tutor")
    public ResponseEntity<?> getTutorByMateria(
            @PathVariable Long idMateria
    ) {

        try {

            List<Competenza> tutor =
                    competenzaService
                            .findTutorByMateria(idMateria);

            return ResponseEntity.ok(tutor);

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

    @PostMapping("/competenze")
    public ResponseEntity<?> createCompetenza(
            @RequestBody Map<String, Object> body
    ) {

        try {

            Competenza competenza =
                    new Competenza();

            competenza.setLivello(
                    LivelloCompetenza.valueOf(
                            body.get("livello").toString()
                    )
            );

            Object descrizione =
                    body.get("descrizione");

            competenza.setDescrizione(
                    descrizione == null
                            ? null
                            : descrizione.toString()
            );

            Long idUtente =
                    Long.valueOf(
                            body.get("idUtente").toString()
                    );

            Long idMateria =
                    Long.valueOf(
                            body.get("idMateria").toString()
                    );

            Competenza nuovaCompetenza =
                    competenzaService.create(
                            competenza,
                            idUtente,
                            idMateria
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuovaCompetenza);

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

    @PutMapping("/competenze/{idCompetenza}")
    public ResponseEntity<?> updateCompetenza(
            @PathVariable Long idCompetenza,
            @RequestBody Map<String, Object> body
    ) {

        try {

            Competenza competenza =
                    new Competenza();

            competenza.setLivello(
                    LivelloCompetenza.valueOf(
                            body.get("livello").toString()
                    )
            );

            Object descrizione =
                    body.get("descrizione");

            competenza.setDescrizione(
                    descrizione == null
                            ? null
                            : descrizione.toString()
            );

            Long idUtente =
                    Long.valueOf(
                            body.get("idUtente").toString()
                    );

            Long idMateria =
                    Long.valueOf(
                            body.get("idMateria").toString()
                    );

            boolean ok =
                    competenzaService.update(
                            idCompetenza,
                            idUtente,
                            idMateria,
                            competenza
                    );

            return ResponseEntity.ok(
                    Map.of(
                            "updated",
                            ok
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

    @DeleteMapping("/competenze/{idCompetenza}")
    public ResponseEntity<?> deleteCompetenza(
            @PathVariable Long idCompetenza,
            @RequestBody Map<String, Object> body
    ) {

        try {

            Long idUtente =
                    Long.valueOf(
                            body.get("idUtente").toString()
                    );

            boolean ok =
                    competenzaService.delete(
                            idCompetenza,
                            idUtente
                    );

            return ResponseEntity.ok(
                    Map.of(
                            "deleted",
                            ok
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