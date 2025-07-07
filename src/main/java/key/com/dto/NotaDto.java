package key.com.dto;

import lombok.Data;

@Data
public class NotaDto {
    private Long materiaId;
    private String emailAlumno;
    private String nombreMateria;
    private Double notaFinal;
}
