package co.edu.uniquindio.envio.modelo;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Paquete implements Serializable {
    private String descripcion;
    private  float peso;
//    private  String valor;
}
