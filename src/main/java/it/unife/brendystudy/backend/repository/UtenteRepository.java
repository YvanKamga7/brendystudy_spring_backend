package it.unife.brendystudy.backend.repository;

import it.unife.brendystudy.backend.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByUsernameAndPassword(
            String username,
            String password
    );
}