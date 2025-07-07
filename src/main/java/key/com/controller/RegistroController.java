package key.com.controller;

import com.google.gson.Gson;
import key.com.dto.AlumnoDto;
import key.com.dto.MateriaDto;
import key.com.dto.ProfesorDto;
import key.com.service.RegistroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registro")
@PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR') or hasRole('REGISTRO')")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://key-frontend-app.onrender.com"
}, allowCredentials = "true")
public class RegistroController {

    private final RegistroService registroService;

    // Alumnos
    @GetMapping("/alumnos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR') or hasRole('REGISTRO')")
    public List<AlumnoDto> listarAlumnos() {
        return registroService.listarAlumnos();
    }

    @PostMapping("/alumnos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR') or hasRole('REGISTRO')")
    public void crearAlumno(@RequestBody AlumnoDto dto) {
        log.info("Creating alumno: " + new Gson().toJson(dto));
        log.info("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
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
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESOR', 'REGISTRO')")
    public void crearMateria(@RequestBody MateriaDto dto) {
        registroService.crearMateria(dto);
    }
}
