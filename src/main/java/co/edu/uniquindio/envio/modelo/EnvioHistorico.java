package co.edu.uniquindio.envio.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import co.edu.uniquindio.envio.modelo.enums.Ciudad;
import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import co.edu.uniquindio.envio.modelo.enums.TipoEnvio;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnvioHistorico implements Serializable {
    @EqualsAndHashCode.Include
    private String codigoEnvio;
    private String remitente;
    private String destinatario;
    private transient  List<Paquete> paquetes;
    private TipoEnvio tipo;
    private Ciudad ciudad;
    private TipEstado estados;
    private LocalDate fecha;
    private  float distancia;
    private float valor;

}
