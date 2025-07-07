package key.com.service;

import key.com.dto.AlumnoDto;
import key.com.dto.NotaDto;
import key.com.entity.Alumno;
import key.com.repository.AlumnoMateriaRepository;
import key.com.repository.AlumnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final AlumnoMateriaRepository alumnoMateriaRepository;

    public List<AlumnoDto>  findAll() {
        return alumnoRepository.findAll()
                .stream()
                .map(alumno -> {
                    AlumnoDto dto = new AlumnoDto();
                    dto.setNombres(alumno.getNombres());
                    dto.setApellidos(alumno.getApellidos());
                    dto.setEmail(alumno.getEmail());
                    return dto;
                }).toList();
    }
    public AlumnoDto obtenerPerfil(String email) {
        Alumno alumno = alumnoRepository.findByEmail(email).orElseThrow();
        AlumnoDto dto = new AlumnoDto();
        dto.setNombres(alumno.getNombres());
        dto.setApellidos(alumno.getApellidos());
        dto.setDireccion(alumno.getDireccion());
        dto.setTelefono(alumno.getTelefono());
        return dto;
    }

    public void actualizarPerfil(String email, AlumnoDto dto) {
        Alumno alumno = alumnoRepository.findByEmail(email).orElseThrow();
        alumno.setDireccion(dto.getDireccion());
        alumno.setEmail(email);
        alumno.setTelefono(dto.getTelefono());
        alumnoRepository.save(alumno);
    }

    public List<NotaDto> obtenerNotas(String email) {
        Alumno alumno = alumnoRepository.findByEmail(email).orElseThrow();
        return alumnoMateriaRepository.findByAlumnoId(alumno.getId())
                .stream()
                .map(am -> {
                    NotaDto nota = new NotaDto();
                    nota.setMateriaId(am.getMateria().getId());
                    nota.setNombreMateria(am.getMateria().getNombreMateria());
                    nota.setNotaFinal(am.getNotaFinal());
                    return nota;
                }).toList();
    }
}