package it.unife.brendystudy.backend.controller;

import it.unife.brendystudy.backend.model.Materia;
import it.unife.brendystudy.backend.service.MateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping("/materie")
    public ResponseEntity<?> getMaterie() {

        try {
            List<Materia> materie =
                    materiaService.findAll();

            return ResponseEntity.ok(materie);

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

    @PostMapping("/materie")
    public ResponseEntity<?> createMateria(
            @RequestBody Materia materia
    ) {

        try {

            materia.setIdMateria(null);

            Materia nuovaMateria =
                    materiaService.create(materia);

            if (nuovaMateria == null) {

                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(
                                Map.of(
                                        "erroreMsg",
                                        "Materia non creata"
                                )
                        );
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuovaMateria);

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

    @DeleteMapping("/materie/{idMateria}")
    public ResponseEntity<?> deleteMateria(
            @PathVariable Long idMateria
    ) {

        try {

            boolean ok =
                    materiaService.delete(idMateria);

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