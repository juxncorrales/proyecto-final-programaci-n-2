package org.example.servicio;

import co.edu.uniquindio.envio.modelo.EnvioHistorico;
import co.edu.uniquindio.envio.modelo.Factura;
import co.edu.uniquindio.envio.modelo.Paquete;
import co.edu.uniquindio.envio.modelo.Persona;
import co.edu.uniquindio.envio.modelo.enums.Ciudad;
import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import co.edu.uniquindio.envio.modelo.enums.TipoEnvio;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz que define los servicios que se pueden realizar en el hotel
 */
public interface EnvioServicio {
    Persona agregarPersonas(String cedula,String nombre,String direccion,String ciudad,String numero,String correo)throws Exception;

    void guardarDatos() throws Exception;
    void cargarDatos() throws Exception;

    Persona obtenerPersonas(String cedula)throws Exception;
    void actualizarPersona(String cedula,String nombre,String direccion,String ciudad,String numero,String correo)throws Exception;
    Paquete agregarPaquete(String descripcion,String peso) throws Exception;

    String generarCodigo(TipoEnvio tipo);

    double calcularPrecio(float distancia, TipoEnvio tipo, float peso, int cantidadPaquetes) throws Exception;
    double calcularPericoSubTotal(float distancia, TipoEnvio tipo, float peso, int cantidadPaquetes);
    EnvioHistorico crearHistorial(String codigoEnvio, String remitente, String destinatario, List<Paquete> paquetes, TipoEnvio tipo, Ciudad ciudad, TipEstado estados, LocalDate fecha, float distancia, float valor)throws Exception;
     List<EnvioHistorico> filtrarDatos(LocalDate fecha, TipoEnvio tipo, TipEstado estado);
     List<EnvioHistorico> datos();
     EnvioHistorico cargarEnvio(String codigo);
     void actualizarEnvio(String codigo, TipEstado estado)throws Exception;
     List<Paquete> obtenerPaquetesPorCodigo(String codigo);
     List<Paquete> paquetesCargar();
    Factura crearFactura(String codigoEnvio, String total, String subtotal) throws Exception;

}