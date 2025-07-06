package key.com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {
    @Id
    @GeneratedValue
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private LocalDate fechaIngreso;

    @OneToMany(mappedBy = "profesor")
    private List<Materia> materias;
}