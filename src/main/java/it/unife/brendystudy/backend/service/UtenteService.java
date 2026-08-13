package it.unife.brendystudy.backend.service;

import it.unife.brendystudy.backend.model.Utente;
import it.unife.brendystudy.backend.model.enums.StatoUtente;
import it.unife.brendystudy.backend.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public Optional<Utente> findById(Long idUtente) {
        return utenteRepository.findById(idUtente);
    }

    public Optional<Utente> login(String username, String password) {
        return utenteRepository.findByUsernameAndPassword(username, password);
    }

    public Utente create(Utente utente) {
        return utenteRepository.save(utente);
    }

    public boolean bloccaUtente(Long idUtente) {

        Optional<Utente> optionalUtente =
                utenteRepository.findById(idUtente);

        if (optionalUtente.isEmpty()) {
            return false;
        }

        Utente utente = optionalUtente.get();
        utente.setStato(StatoUtente.BLOCCATO);

        utenteRepository.save(utente);

        return true;
    }

    public boolean sbloccaUtente(Long idUtente) {

        Optional<Utente> optionalUtente =
                utenteRepository.findById(idUtente);

        if (optionalUtente.isEmpty()) {
            return false;
        }

        Utente utente = optionalUtente.get();
        utente.setStato(StatoUtente.ATTIVO);

        utenteRepository.save(utente);

        return true;
    }
}