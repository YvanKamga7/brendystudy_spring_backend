package it.unife.brendystudy.backend.model;

import it.unife.brendystudy.backend.model.enums.LivelloCompetenza;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "competenza",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_competenza_utente_materia",
                        columnNames = {"idUtente", "idMateria"}
                )
        }
)
@Getter
@Setter
public class Competenza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompetenza")
    private Long idCompetenza;

    @Enumerated(EnumType.STRING)
    @Column(name = "livello", nullable = false)
    private LivelloCompetenza livello;

    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "idUtente", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "idMateria", nullable = false)
    private Materia materia;
}