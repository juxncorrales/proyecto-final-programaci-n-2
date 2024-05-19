package co.edu.uniquindio.envio.modelo;

import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Estados {
    private LocalDate fecha;
    private String descripcion;
    private TipEstado estado;

}
