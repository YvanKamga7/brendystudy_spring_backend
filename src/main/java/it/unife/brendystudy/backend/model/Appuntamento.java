package it.unife.brendystudy.backend.model;

import it.unife.brendystudy.backend.model.enums.ModalitaAppuntamento;
import it.unife.brendystudy.backend.model.enums.StatoAppuntamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name = "appuntamento",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_appuntamento_richiesta",
                        columnNames = "idRichiesta"
                )
        }
)
@Getter
@Setter
public class Appuntamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAppuntamento")
    private Long idAppuntamento;

    @Column(name = "dataAppuntamento", nullable = false)
    private LocalDate dataAppuntamento;

    @Column(name = "oraAppuntamento", nullable = false)
    private LocalTime oraAppuntamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalita", nullable = false)
    private ModalitaAppuntamento modalita;

    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoAppuntamento stato = StatoAppuntamento.ATTIVO;

    @OneToOne
    @JoinColumn(
            name = "idRichiesta",
            nullable = false,
            unique = true
    )
    private RichiestaSupporto richiesta;
}