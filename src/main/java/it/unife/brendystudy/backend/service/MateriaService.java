package it.unife.brendystudy.backend.service;

import it.unife.brendystudy.backend.model.Materia;
import it.unife.brendystudy.backend.repository.CompetenzaRepository;
import it.unife.brendystudy.backend.repository.MateriaRepository;
import it.unife.brendystudy.backend.repository.RichiestaSupportoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final CompetenzaRepository competenzaRepository;
    private final RichiestaSupportoRepository richiestaSupportoRepository;

    public MateriaService(
            MateriaRepository materiaRepository,
            CompetenzaRepository competenzaRepository,
            RichiestaSupportoRepository richiestaSupportoRepository
    ) {
        this.materiaRepository = materiaRepository;
        this.competenzaRepository = competenzaRepository;
        this.richiestaSupportoRepository = richiestaSupportoRepository;
    }

    public List<Materia> findAll() {

        List<Materia> materie = materiaRepository.findAll();

        materie.sort(
                Comparator.comparing(
                        Materia::getNome,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        return materie;
    }

    public Materia create(Materia materia) {
        return materiaRepository.save(materia);
    }

    public boolean materiaUsata(Long idMateria) {

        return competenzaRepository
                .existsByMateria_IdMateria(idMateria)
                ||
                richiestaSupportoRepository
                .existsByMateria_IdMateria(idMateria);
    }

    public boolean delete(Long idMateria) {

        if (materiaUsata(idMateria)) {
            throw new IllegalStateException(
                    "Impossibile eliminare la materia perché è utilizzata nel sistema."
            );
        }

        if (!materiaRepository.existsById(idMateria)) {
            return false;
        }

        materiaRepository.deleteById(idMateria);

        return true;
    }
}