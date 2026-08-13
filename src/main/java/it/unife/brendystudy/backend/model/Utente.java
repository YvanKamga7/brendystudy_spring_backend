package it.unife.brendystudy.backend.model;

import it.unife.brendystudy.backend.model.enums.RuoloUtente;
import it.unife.brendystudy.backend.model.enums.StatoUtente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "utente")
@Getter
@Setter
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUtente")
    private Long idUtente;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false)
    private RuoloUtente ruolo = RuoloUtente.STUDENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoUtente stato = StatoUtente.ATTIVO;
}