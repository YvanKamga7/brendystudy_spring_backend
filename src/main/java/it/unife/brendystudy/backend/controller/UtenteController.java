package it.unife.brendystudy.backend.controller;

import it.unife.brendystudy.backend.model.Utente;
import it.unife.brendystudy.backend.model.enums.StatoUtente;
import it.unife.brendystudy.backend.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/utenti")
    public List<Utente> getUtenti() {
        return utenteService.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        Optional<Utente> optionalUtente =
                utenteService.login(username, password);

        if (optionalUtente.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "erroreMsg",
                            "Credenziali non valide"
                    ));
        }

        Utente utente = optionalUtente.get();

        if (utente.getStato() == StatoUtente.BLOCCATO) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of(
                            "erroreMsg",
                            "Il tuo account è bloccato.\n\n" +
                            "Contatta un amministratore per richiedere la riattivazione.\n" +
                            "Email: admin@brendystudy.it"
                    ));
        }

        return ResponseEntity.ok(utente);
    }

    @PostMapping("/utenti")
    public ResponseEntity<?> createUtente(
            @RequestBody Utente utente
    ) {

        utente.setIdUtente(null);
        utente.setRuolo(
                it.unife.brendystudy.backend.model.enums.RuoloUtente.STUDENTE
        );
        utente.setStato(StatoUtente.ATTIVO);

        Utente nuovoUtente =
                utenteService.create(utente);

        if (nuovoUtente == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "erroreMsg",
                            "Utente non creato"
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuovoUtente);
    }

    @PutMapping("/utenti/{idUtente}/blocco")
    public Map<String, Boolean> bloccaUtente(
            @PathVariable Long idUtente
    ) {

        boolean ok =
                utenteService.bloccaUtente(idUtente);

        return Map.of(
                "blocked",
                ok
        );
    }

    @PutMapping("/utenti/{idUtente}/sblocco")
    public Map<String, Boolean> sbloccaUtente(
            @PathVariable Long idUtente
    ) {

        boolean ok =
                utenteService.sbloccaUtente(idUtente);

        return Map.of(
                "unblocked",
                ok
        );
    }
}