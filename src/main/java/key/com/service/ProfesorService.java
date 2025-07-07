package key.com.service;

import key.com.dto.MateriaDto;
import key.com.dto.NotaDto;
import key.com.entity.Alumno;
import key.com.entity.AlumnoMateria;
import key.com.entity.Materia;
import key.com.entity.Profesor;
import key.com.repository.AlumnoMateriaRepository;
import key.com.repository.AlumnoRepository;
import key.com.repository.MateriaRepository;
import key.com.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfesorService {
    private final ProfesorRepository profesorRepository;
    private final MateriaRepository materiaRepository;
    private final AlumnoRepository alumnoRepository;
    private final AlumnoMateriaRepository alumnoMateriaRepository;

    public List<MateriaDto> obtenerMaterias(String email) {
        Profesor profesor = profesorRepository.findByEmail(email).orElseThrow();
        return materiaRepository.findByProfesorId(profesor.getId())
                .stream()
                .map(m -> new MateriaDto(m.getId(), m.getNombreMateria()))
                .toList();
    }

    public List<Materia> obtenerAllMaterias() {
        return materiaRepository.findAll()
                .stream()
                .toList();
    }

    public void actualizarNotas(String email, List<NotaDto> notas) {
        if (notas.isEmpty()) {
            throw new IllegalArgumentException("No se recibieron notas para actualizar");
        }

        Alumno alumno = alumnoRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con email: " + email));

        for (NotaDto nota : notas) {
            Materia materia = materiaRepository.findById(nota.getMateriaId())
                    .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + nota.getMateriaId()));

            AlumnoMateria am = alumnoMateriaRepository
                    .findByMateriaIdAndAlumnoEmail(nota.getMateriaId(), email)
                    .orElse(null);

            if (am == null) {
                // Crear la relación si no existe
                am = new AlumnoMateria();
                am.setAlumno(alumno);
                am.setMateria(materia);
            }

            am.setNotaFinal(nota.getNotaFinal());
            alumnoMateriaRepository.save(am);
        }
    }

}
