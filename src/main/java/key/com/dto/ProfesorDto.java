package key.com.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfesorDto {
    private String nombres;
    private String apellidos;
    private String email;
    private LocalDate fechaIngreso;
}
