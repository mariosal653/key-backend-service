package key.com.dto;

import lombok.Data;
import java.util.List;

@Data
public class AlumnoNotasDto {
    private String nombreCompleto;
    private List<NotaDto> notas;
}