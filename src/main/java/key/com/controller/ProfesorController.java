package key.com.controller;

import com.google.gson.Gson;
import key.com.dto.MateriaDto;
import key.com.dto.NotaDto;
import key.com.entity.Materia;
import key.com.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesor")
@PreAuthorize("hasRole('PROFESOR') or hasRole('REGISTRO') or hasRole('ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://key-frontend-app.onrender.com"
}, allowCredentials = "true")
@Slf4j
public class ProfesorController {

    private final ProfesorService profesorService;

    @GetMapping("/materias")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESOR', 'REGISTRO')")
    public ResponseEntity<List<MateriaDto>> verMaterias(Authentication auth) {
        return ResponseEntity.ok(profesorService.obtenerMaterias(auth.getName()));
    }

    @GetMapping("/all-materias")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESOR', 'REGISTRO')")
    public ResponseEntity<List<Materia>> findAll(Authentication auth) {
        return ResponseEntity.ok(profesorService.obtenerAllMaterias());
    }

    @PutMapping("/notas")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESOR', 'REGISTRO')")
    public ResponseEntity<Void> actualizarNotas(@RequestBody List<NotaDto> notas, Authentication auth) {
        log.info("Actualizando notas para el profesor: {}", auth.getName());
        log.info("Notas recibidas: {}", new Gson().toJson(notas));
        if (notas.isEmpty()) {
            log.warn("No se recibieron notas para actualizar");
            return ResponseEntity.badRequest().build();
        }
        profesorService.actualizarNotas(notas.get(0).getEmailAlumno(), notas);
        return ResponseEntity.noContent().build();
    }
}