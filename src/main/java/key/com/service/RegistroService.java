package key.com.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import key.com.dto.AlumnoDto;
import key.com.dto.MateriaDto;
import key.com.dto.ProfesorDto;
import key.com.entity.Alumno;
import key.com.entity.Materia;
import key.com.entity.Profesor;
import key.com.repository.AlumnoRepository;
import key.com.repository.MateriaRepository;
import key.com.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegistroService {
    private final AlumnoRepository alumnoRepository;
    private final ProfesorRepository profesorRepository;
    private final MateriaRepository materiaRepository;

    public List<AlumnoDto> listarAlumnos() {
        return alumnoRepository.findAll().stream().map(alumno -> {
            AlumnoDto dto = new AlumnoDto();
            dto.setNombres(alumno.getNombres());
            dto.setApellidos(alumno.getApellidos());
            dto.setDireccion(alumno.getDireccion());
            dto.setTelefono(alumno.getTelefono());
            return dto;
        }).toList();
    }

    public void crearAlumno(AlumnoDto dto) {
        Alumno alumno = new Alumno();
        alumno.setNombres(dto.getNombres());
        alumno.setApellidos(dto.getApellidos());
        alumno.setEmail(dto.getEmail());
        alumno.setDireccion(dto.getDireccion());
        alumno.setTelefono(dto.getTelefono());
        alumno.setFechaIngreso(LocalDate.now());
        alumnoRepository.save(alumno);

        Firestore firestore = FirestoreClient.getFirestore();

        Map<String, Object> data = new HashMap<>();
        data.put("nombres", dto.getNombres());
        data.put("apellidos", dto.getApellidos());
        data.put("email", dto.getEmail());
        data.put("direccion", dto.getDireccion());
        data.put("telefono", dto.getTelefono());
        data.put("fechaIngreso", LocalDate.now().toString());

        DocumentReference docRef = firestore.collection("alumnos").document(alumno.getId().toString());
        ApiFuture<WriteResult> result = docRef.set(data);
    }

    public List<ProfesorDto> listarProfesores() {
        return profesorRepository.findAll().stream().map(p -> {
            ProfesorDto dto = new ProfesorDto();
            dto.setNombres(p.getNombres());
            dto.setApellidos(p.getApellidos());
            dto.setEmail(p.getEmail());
            dto.setFechaIngreso(p.getFechaIngreso());
            return dto;
        }).toList();
    }

    public void crearProfesor(ProfesorDto dto) {
        Profesor p = new Profesor();
        p.setNombres(dto.getNombres());
        p.setEmail(dto.getEmail());
        p.setApellidos(dto.getApellidos());
        p.setFechaIngreso(dto.getFechaIngreso());
        profesorRepository.save(p);
    }

    public List<MateriaDto> listarMaterias() {
        return materiaRepository.findAll().stream().map(m -> {
            MateriaDto dto = new MateriaDto();
            dto.setId(m.getId());
            dto.setNombreMateria(m.getNombreMateria());
            return dto;
        }).toList();
    }

    public void crearMateria(MateriaDto dto) {
        Materia materia = new Materia();
        materia.setNombreMateria(dto.getNombreMateria());
        materiaRepository.save(materia);
    }
}