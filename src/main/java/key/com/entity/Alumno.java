package key.com.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {
    @Id
    @GeneratedValue
    private Long id;
    private String nombres;
    private String apellidos;
    @Column(unique = true)
    private String email;
    private LocalDate fechaIngreso;
    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "alumno")
    private List<AlumnoMateria> materias;
}
