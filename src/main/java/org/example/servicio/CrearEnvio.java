package org.example.servicio;

import co.edu.uniquindio.envio.modelo.EnvioHistorico;
import co.edu.uniquindio.envio.modelo.Envios;
import co.edu.uniquindio.envio.modelo.Paquete;
import co.edu.uniquindio.envio.modelo.enums.Ciudad;
import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import co.edu.uniquindio.envio.modelo.enums.TipoEnvio;

import java.time.LocalDate;
import java.util.List;

public interface CrearEnvio {
    EnvioHistorico crearHistorial(String codigoEnvio, String remitente, String destinatario, List<Paquete> paquetes, TipoEnvio tipo, Ciudad ciudad, TipEstado estados, LocalDate fecha, float distancia, float valor);
}