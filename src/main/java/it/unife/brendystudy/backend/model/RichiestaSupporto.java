package it.unife.brendystudy.backend.model;

import it.unife.brendystudy.backend.model.enums.StatoRichiesta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "richiesta_supporto")
@Getter
@Setter
public class RichiestaSupporto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRichiesta")
    private Long idRichiesta;

    @Column(
            name = "dataRichiesta",
            nullable = false
    )
    private LocalDateTime dataRichiesta = LocalDateTime.now();

    @Column(
            name = "messaggio",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String messaggio;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoRichiesta stato = StatoRichiesta.IN_ATTESA;

    @ManyToOne
    @JoinColumn(name = "idRichiedente", nullable = false)
    private Utente richiedente;

    @ManyToOne
    @JoinColumn(name = "idTutor", nullable = false)
    private Utente tutor;

    @ManyToOne
    @JoinColumn(name = "idMateria", nullable = false)
    private Materia materia;
}