package key.com.config;

import key.com.entity.Alumno;
import key.com.entity.AlumnoMateria;
import key.com.entity.Materia;
import key.com.entity.Profesor;
import key.com.repository.AlumnoMateriaRepository;
import key.com.repository.AlumnoRepository;
import key.com.repository.MateriaRepository;
import key.com.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AlumnoRepository alumnoRepository;
    private final ProfesorRepository profesorRepository;
    private final MateriaRepository materiaRepository;
    private final AlumnoMateriaRepository alumnoMateriaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!alumnoRepository.findAll().isEmpty()) return;

        // === PROFESOR ===
        Profesor profesor = new Profesor();
        profesor.setNombres("Lucía");
        profesor.setApellidos("Sánchez");
        profesor.setFechaIngreso(LocalDate.of(2022, 3, 10));
        profesor.setEmail("profesor@demo.com"); // Este email debe existir en Firebase con role: PROFESOR
        profesorRepository.save(profesor);

        // === MATERIA ===
        Materia materia = new Materia();
        materia.setNombreMateria("Ciencias Sociales");
        materia.setProfesor(profesor);
        materiaRepository.save(materia);

        // === ALUMNO ===
        Alumno alumno = new Alumno();
        alumno.setNombres("Carlos");
        alumno.setApellidos("Perez");
        alumno.setFechaIngreso(LocalDate.of(2023, 1, 15));
        alumno.setDireccion("San Salvador");
        alumno.setTelefono("7000-0000");
        alumno.setEmail("alumno@demo.com");
        alumnoRepository.save(alumno);

        // === INSCRIPCIÓN + NOTA ===
        AlumnoMateria inscripcion = new AlumnoMateria();
        inscripcion.setAlumno(alumno);
        inscripcion.setMateria(materia);
        inscripcion.setCiclo("01-2024");
        inscripcion.setNotaFinal(9.2);
        alumnoMateriaRepository.save(inscripcion);

        // === REGISTRO ACADÉMICO (solo email) ===
        // No tiene entidad separada, pero su existencia se valida por el email en el token
        System.out.println("✔ Usuarios creados:");
        System.out.println("  - ALUMNO: alumno@demo.com");
        System.out.println("  - PROFESOR: profesor@demo.com");
        System.out.println("  - REGISTRO: registro@demo.com (no necesita entidad)");
    }
}
