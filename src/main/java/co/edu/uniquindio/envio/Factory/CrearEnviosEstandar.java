package co.edu.uniquindio.envio.Factory;

import co.edu.uniquindio.envio.modelo.EnvioHistorico;
import co.edu.uniquindio.envio.modelo.Paquete;
import co.edu.uniquindio.envio.modelo.enums.Ciudad;
import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import co.edu.uniquindio.envio.modelo.enums.TipoEnvio;
import org.example.servicio.CrearEnvio;

import java.time.LocalDate;
import java.util.List;

public class CrearEnviosEstandar implements CrearEnvio {
    @Override
    public EnvioHistorico crearHistorial(String codigoEnvio, String remitente, String destinatario, List<Paquete> paquetes, TipoEnvio tipo, Ciudad ciudad, TipEstado estados, LocalDate fecha, float distancia, float valor){
        EnvioHistorico envios =EnvioHistorico.builder()
                .codigoEnvio(codigoEnvio)
                .remitente(remitente)
                .destinatario(destinatario)
                .paquetes(paquetes)
                .ciudad(ciudad)
                .tipo(tipo)
                .estados(estados)
                .fecha(fecha)
                .distancia(distancia)
                .valor(valor)
                .build();
        return envios;
    }
}
