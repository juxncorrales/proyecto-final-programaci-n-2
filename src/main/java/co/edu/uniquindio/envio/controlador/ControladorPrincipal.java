package co.edu.uniquindio.envio.controlador;
import co.edu.uniquindio.envio.modelo.*;
import co.edu.uniquindio.envio.modelo.enums.Ciudad;
import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import co.edu.uniquindio.envio.modelo.enums.TipoEnvio;
import co.edu.uniquindio.envio.utils.Persistencia;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.servicio.EnvioServicio;
import org.example.servicio.Parametrizable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que representa el controlador principal de la aplicación
 */

@Getter
public class ControladorPrincipal implements EnvioServicio {

    private final Envios envios;
    public static ControladorPrincipal INSTANCIA;
//    private final Persistencia persistencia;

    private ControladorPrincipal() throws Exception {
        envios = new Envios();

//        this.persistencia = new Persistencia();
    }

    public static ControladorPrincipal getInstancia()throws Exception{
        if(INSTANCIA == null){
            INSTANCIA = new ControladorPrincipal();
        }
        return INSTANCIA;
    }


    public void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta - Hotel");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public FXMLLoader navegar(String nombreVista, String titulo, Object... parametros){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreVista));
            Parent root = loader.load();

            Object controller = loader.getController();

//            recibe los parametros para enviar entre controladores
            if (controller instanceof Parametrizable) {
                ((Parametrizable) controller).datosPersona(parametros);
            }

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(titulo);

            // Mostrar la nueva ventana
            stage.show();

            return loader;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void cerrarVentana(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }


    @Override
    public Persona agregarPersonas(String cedula, String nombre, String direccion, String ciudad, String numero, String correo) throws Exception {
        return envios.agregarPersonas(cedula, nombre, direccion, ciudad, numero, correo);
    }

    @Override
    public void guardarDatos() throws Exception {

    }

    @Override
    public void cargarDatos() throws Exception {

    }

    @Override
    public Persona obtenerPersonas(String cedula) throws Exception {
        return envios.obtenerPersonas(cedula);
    }

    @Override
    public void actualizarPersona(String cedula, String nombre, String direccion, String ciudad, String numero, String correo) throws Exception {
        envios.actualizarPersona(cedula, nombre, direccion, ciudad, numero, correo);
    }

    @Override
    public Paquete agregarPaquete(String descripcion, String peso) throws Exception {
        return envios.agregarPaquete(descripcion, peso);
    }

    @Override
    public String generarCodigo(TipoEnvio tipo) {
        return envios.generarCodigo(tipo);
    }

    @Override
    public double calcularPrecio(float distancia, TipoEnvio tipo, float peso, int cantidadPaquetes)throws Exception {
        return envios.calcularPrecio(distancia,tipo,peso,cantidadPaquetes);
    }

    @Override
    public EnvioHistorico crearHistorial(String codigoEnvio, String remitente, String destinatario, List<Paquete> paquetes, TipoEnvio tipo, Ciudad ciudad, TipEstado estados, LocalDate fecha, float distancia, float valor) throws Exception {
        return envios.crearHistorial(codigoEnvio,remitente,destinatario,paquetes,tipo,ciudad,estados, fecha,distancia,valor);
    }

    @Override
    public List<EnvioHistorico> filtrarDatos(LocalDate fecha, TipoEnvio tipo, TipEstado estado){
        return envios.filtrarDatos(fecha,tipo,estado);
    }

    @Override
    public List<EnvioHistorico> datos() {
        return envios.datos();
    }

    public EnvioHistorico cargarEnvio(String codigo){
        return envios.cargarEnvio(codigo);
    }
    public void actualizarEnvio(String codigo, TipEstado estado)throws Exception{
        envios.actualizarEnvio(codigo, estado);
    }
    public List<Paquete> obtenerPaquetesPorCodigo(String codigo){
        return envios.obtenerPaquetesPorCodigo(codigo);
    }
    public List<Paquete> paquetesCargar(){
        return envios.paquetesCargar();
    }

    @Override
    public Factura crearFactura(String codigoEnvio, String total, String subtotal) throws Exception {
        return envios.crearFactura(codigoEnvio,total,subtotal);
    }

    @Override
    public double calcularPericoSubTotal(float distancia, TipoEnvio tipo, float peso, int cantidadPaquetes){
        return envios.calcularPericoSubTotal(distancia,tipo,peso,cantidadPaquetes);
    }

}