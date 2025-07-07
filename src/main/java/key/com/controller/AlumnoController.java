package key.com.controller;

import key.com.dto.AlumnoDto;
import key.com.dto.AlumnoNotasDto;
import key.com.dto.NotaDto;
import key.com.entity.Alumno;
import key.com.repository.AlumnoRepository;
import key.com.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumno")
@PreAuthorize("hasRole('ALUMNO') or hasRole('PROFESOR') or hasRole('REGISTRO') or hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final AlumnoRepository alumnoRepository;

    @GetMapping("/perfil")
    public ResponseEntity<AlumnoDto> getPerfil(Authentication auth) {
        return ResponseEntity.ok(alumnoService.obtenerPerfil(auth.getName()));
    }

    @PutMapping("/perfil")
    public ResponseEntity<Void> actualizarPerfil(@RequestBody AlumnoDto alumnoDto, Authentication auth) {
        alumnoService.actualizarPerfil(auth.getName(), alumnoDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/notas")
    public ResponseEntity<AlumnoNotasDto> verNotas(Authentication auth) {
        AlumnoDto alumno = alumnoService.obtenerPerfil(auth.getName());
        List<NotaDto> notas = alumnoService.obtenerNotas(auth.getName());

        AlumnoNotasDto response = new AlumnoNotasDto();
        response.setNombreCompleto(alumno.getNombres() + " " + alumno.getApellidos());
        response.setNotas(notas);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    @GetMapping("/todos")
    public ResponseEntity<List<AlumnoDto>> obtenerTodosLosAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();

        List<AlumnoDto> resultado = alumnos.stream().map(alumno -> {
            AlumnoDto dto = new AlumnoDto();
            dto.setNombres(alumno.getNombres());
            dto.setApellidos(alumno.getApellidos());
            dto.setDireccion(alumno.getDireccion());
            dto.setTelefono(alumno.getTelefono());
            return dto;
        }).toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/todos-con-notas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESOR')")
    public ResponseEntity<List<AlumnoNotasDto>> verNotasTodos() {
        List<Alumno> alumnos = alumnoRepository.findAll();

        List<AlumnoNotasDto> resultado = alumnos.stream().map(alumno -> {
            List<NotaDto> notas = alumnoService.obtenerNotas(alumno.getEmail());

            AlumnoNotasDto dto = new AlumnoNotasDto();
            dto.setNombreCompleto(alumno.getNombres() + " " + alumno.getApellidos());
            dto.setNotas(notas);
            return dto;
        }).toList();

        return ResponseEntity.ok(resultado);
    }

}