package key.com.controller;

import key.com.dto.AlumnoDto;
import key.com.dto.MateriaDto;
import key.com.dto.ProfesorDto;
import key.com.service.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registro")
@PreAuthorize("hasRole('REGISTRO') or hasRole('ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegistroController {

    private final RegistroService registroService;

    // Alumnos
    @GetMapping("/alumnos")
    public List<AlumnoDto> listarAlumnos() {
        return registroService.listarAlumnos();
    }

    @PostMapping("/alumnos")
    public void crearAlumno(@RequestBody AlumnoDto dto) {
        registroService.crearAlumno(dto);
    }

    // Profesores
    @GetMapping("/profesores")
    public List<ProfesorDto> listarProfesores() {
        return registroService.listarProfesores();
    }

    @PostMapping("/profesores")
    public void crearProfesor(@RequestBody ProfesorDto dto) {
        registroService.crearProfesor(dto);
    }

    // Materias
    @GetMapping("/materias")
    public List<MateriaDto> listarMaterias() {
        return registroService.listarMaterias();
    }

    @PostMapping("/materias")
    public void crearMateria(@RequestBody MateriaDto dto) {
        registroService.crearMateria(dto);
    }
}
