package key.com.service;

import key.com.dto.MateriaDto;
import key.com.dto.NotaDto;
import key.com.entity.AlumnoMateria;
import key.com.entity.Profesor;
import key.com.repository.AlumnoMateriaRepository;
import key.com.repository.MateriaRepository;
import key.com.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfesorService {
    private final ProfesorRepository profesorRepository;
    private final MateriaRepository materiaRepository;
    private final AlumnoMateriaRepository alumnoMateriaRepository;

    public List<MateriaDto> obtenerMaterias(String email) {
        Profesor profesor = profesorRepository.findByEmail(email).orElseThrow();
        return materiaRepository.findByProfesorId(profesor.getId())
                .stream()
                .map(m -> new MateriaDto(m.getId(), m.getNombreMateria()))
                .toList();
    }

    public void actualizarNotas(String email, List<NotaDto> notas) {
        for (NotaDto nota : notas) {
            AlumnoMateria am = alumnoMateriaRepository
                    .findByMateriaIdAndAlumnoEmail(nota.getMateriaId(), email)
                    .orElseThrow();
            am.setNotaFinal(nota.getNotaFinal());
            alumnoMateriaRepository.save(am);
        }
    }
}
