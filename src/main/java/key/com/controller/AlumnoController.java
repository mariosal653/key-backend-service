package key.com.controller;

import key.com.dto.AlumnoDto;
import key.com.dto.NotaDto;
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
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping("/perfil")
    public ResponseEntity<AlumnoDto> getPerfil(Authentication auth) {
        return ResponseEntity.ok(alumnoService.obtenerPerfil(auth.getName()));
    }

    @PutMapping("/perfil")
    public ResponseEntity<Void> actualizarPerfil(@RequestBody AlumnoDto alumnoDto, Authentication auth) {
        alumnoService.actualizarPerfil(auth.getName(), alumnoDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/notas")
    public ResponseEntity<List<NotaDto>> verNotas(Authentication auth) {
        return ResponseEntity.ok(alumnoService.obtenerNotas(auth.getName()));
    }
}