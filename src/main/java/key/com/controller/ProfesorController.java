package key.com.controller;

import key.com.dto.MateriaDto;
import key.com.dto.NotaDto;
import key.com.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesor")
@PreAuthorize("hasRole('PROFESOR') or hasRole('REGISTRO') or hasRole('ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProfesorController {

    private final ProfesorService profesorService;

    @GetMapping("/materias")
    public ResponseEntity<List<MateriaDto>> verMaterias(Authentication auth) {
        return ResponseEntity.ok(profesorService.obtenerMaterias(auth.getName()));
    }

    @PutMapping("/notas")
    public ResponseEntity<Void> actualizarNotas(@RequestBody List<NotaDto> notas, Authentication auth) {
        profesorService.actualizarNotas(auth.getName(), notas);
        return ResponseEntity.noContent().build();
    }
}