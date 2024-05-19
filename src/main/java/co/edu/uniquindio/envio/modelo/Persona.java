package co.edu.uniquindio.envio.modelo;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Persona implements Serializable {
    @EqualsAndHashCode.Include
    private String cedula;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String numero;
    private String correo;

}
