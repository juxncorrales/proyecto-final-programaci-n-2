package co.edu.uniquindio.envio.controlador;

import co.edu.uniquindio.envio.modelo.EnvioHistorico;
import co.edu.uniquindio.envio.modelo.Persona;
import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import co.edu.uniquindio.envio.utils.EnvioEmail;
import co.edu.uniquindio.envio.utils.EnvioSms;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.example.servicio.Parametrizable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DataEnvio implements Parametrizable,Initializable {
    @FXML
    public Label txtTipo;
    @FXML
    public ChoiceBox<TipEstado> selectestados;
    @FXML
    public TableView<EnvioHistorico> tablaSegui;
    @FXML
    public Label txtDistan;
    @FXML
    public Label txtCiudad;
    @FXML
    public Label txtFecha;
    @FXML
    public Label txtValor;
    public Label idReceptor;
    public Label idEmisor;
    String code;
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public DataEnvio() throws Exception {
    }

    @Override
    public void datosPersona(Object... parametros) {
        if (parametros.length >= 2) {
            // Extraer los parámetros recibidos
            code = (String) parametros[0];
//            Se llama la tabla
            this.tablaSegui = (TableView<EnvioHistorico>) parametros[1];
            cargar();
        } else {
            System.out.println("No se proporcionaron suficientes parámetros");
        }
    }

    public void cargar(){
        EnvioHistorico envio = null;
        envio = controladorPrincipal.cargarEnvio(code);
        System.out.println(envio);
        System.out.println("ESTE ES EL CODIGO QUE SE RECIVE "+code);
        if(envio != null){
            txtTipo.setText(String.valueOf(envio.getTipo()));
            selectestados.setValue(envio.getEstados());
            txtCiudad.setText(String.valueOf(envio.getCiudad()));
            txtDistan.setText(String.valueOf(envio.getDistancia()));
            txtFecha.setText(String.valueOf(envio.getFecha()));
            txtValor.setText(String.valueOf(envio.getValor()));
            idReceptor.setText(String.valueOf(envio.getDestinatario()));
            idEmisor.setText(String.valueOf(envio.getRemitente()));
            System.out.println("ESTI ES LO QQUE CARGA "+idEmisor+"         "+String.valueOf(idReceptor));
        }
    }
    public void initialize(URL location, ResourceBundle resources) {
        selectestados.setItems(FXCollections.observableArrayList(TipEstado.values()));
    }
    public void Guardar(ActionEvent actionEvent) {
        TipEstado estado = selectestados.getValue();
        System.out.println(estado);
        try{
            controladorPrincipal.actualizarEnvio(code, estado);
            controladorPrincipal.mostrarAlerta("Actualizado", Alert.AlertType.CONFIRMATION);
            controladorPrincipal.cerrarVentana(selectestados);
            if (tablaSegui != null) {
                // Limpiar la tabla y luego agregar los nuevos datos
                tablaSegui.getItems().clear();
            }
            tablaSegui.setItems(FXCollections.observableArrayList(controladorPrincipal.datos()));
            String mensajeRecep = "Sus paquetes llegaran Pronto, codigo de seguimiento: " + code + "y se encuentra en estado: " +  String.valueOf(selectestados.getValue());
            String mensajeEmi = "Sus paquetes serán entregados pronto a su lugar de destino, codigo de seguimiento: " + code + "y se encuentra en estado: " +  String.valueOf(selectestados.getValue());
            enviarMensaje(idReceptor.getText(),code,mensajeRecep);
            enviarMensaje(idEmisor.getText(),code,mensajeEmi);
            enviarCorreo(idReceptor.getText(),code,mensajeRecep);
            enviarCorreo(idEmisor.getText(),code,mensajeEmi);
//            enviarCorreo(idEmisor.getText(),code,mensajeEmi);
            System.out.println("EL ID QUE RECIBE: "+idReceptor.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void enviarMensaje(String idPersona, String codigo,String mensaje) throws Exception {
        Persona persona = controladorPrincipal.obtenerPersonas(idPersona);
        if (persona != null) {
            String numero = persona.getNumero();
            System.out.println(numero);
            if (numero != null && !numero.isBlank()) {
                EnvioSms envioSms = new EnvioSms();
                envioSms.crearConexion();
                envioSms.mensaje = mensaje;
                envioSms.numero = numero;
                envioSms.enviarNotificacion();
            } else {
                controladorPrincipal.mostrarAlerta("El número de teléfono del usuario no está disponible.", Alert.AlertType.WARNING);
            }
        } else {
            controladorPrincipal.mostrarAlerta("No se pudo encontrar la persona con el ID proporcionado.", Alert.AlertType.WARNING);
        }
    }
    private void enviarCorreo(String idPersona, String codigo,String mensaje) throws Exception {
        System.out.println("ESTO ES LO QUE RECIVE PARA BUSCAR"+ idPersona);
        Persona persona = controladorPrincipal.obtenerPersonas(idPersona);
        if (persona != null) {
            String email = persona.getCorreo();
            if (email != null && !email.isBlank()) {
                EnvioEmail envioEmail = new EnvioEmail();
                // Podrías considerar pasar las credenciales de correo electrónico como argumentos o leerlas de alguna configuración
                envioEmail.destinatario = email;
                envioEmail.asunto = "Notificación de entrega de paquete";
                envioEmail.mensaje = mensaje;

                // Intentar enviar el correo electrónico
                try {
                    envioEmail.enviarNotificacion();
                } catch (Exception e) {
                    controladorPrincipal.mostrarAlerta("Error al enviar el correo electrónico: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                controladorPrincipal.mostrarAlerta("El correo electrónico del usuario no está disponible.", Alert.AlertType.WARNING);
            }
        } else {
            controladorPrincipal.mostrarAlerta("No se pudo encontrar la persona con el ID proporcionado.", Alert.AlertType.WARNING);
        }
    }

}
