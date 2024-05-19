package co.edu.uniquindio.envio.modelo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Factura implements Serializable {
    private String codigoEnvio;
    private String total;
    private String subtotal;
}
