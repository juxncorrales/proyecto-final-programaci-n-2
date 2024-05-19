package co.edu.uniquindio.envio.controlador;

import co.edu.uniquindio.envio.modelo.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.servicio.Parametrizable;

import java.net.URL;
import java.util.ResourceBundle;

public class Receptor implements Initializable, Parametrizable {
    @FXML
    public TextField txtIdentificacion;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtDireccion;
    @FXML
    public TextField txtCiudad;
    @FXML
    public TextField txtNuTele;
    @FXML
    public TextField txtCorreo;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    Boolean dataPersona = false;
    String idEmisor;

    public Receptor() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtIdentificacion.textProperty().addListener((observable, oldValue, newValue) -> {
            Persona persona = null;
            try {
                persona = controladorPrincipal.obtenerPersonas(txtIdentificacion.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(persona != null){
                System.out.println("El valor de txtIdentificacion ha cambiado a: " + persona.getCedula());
                txtNombre.setText(persona.getNombre());
                txtDireccion.setText(persona.getDireccion());
                txtCiudad.setText(persona.getCiudad());
                txtNuTele.setText(persona.getNumero());
                txtCorreo.setText(persona.getCorreo());
                dataPersona = true;
            }
            else{
                txtNombre.setText("");
                txtDireccion.setText("");
                txtCiudad.setText("");
                txtNuTele.setText("");
                txtCorreo.setText("");
                dataPersona = false;
                System.out.println("NO SE ENCUENTRA");
            }
        });
    }

    @Override
    public void datosPersona(Object... parametros) {
        if (parametros.length >= 1) {
            // Extraer los parámetros recibidos
            idEmisor = (String) parametros[0];
            System.out.println(idEmisor);

        } else {
            System.out.println("No se proporcionaron suficientes parámetros");
        }
    }
    public void siguiRecep(ActionEvent actionEvent) {
        if (dataPersona){
            try {
                controladorPrincipal.actualizarPersona(txtIdentificacion.getText(),txtNombre.getText(),txtDireccion.getText(),txtCiudad.getText(),txtNuTele.getText(),txtCorreo.getText());
                System.out.println("se actualizó");
                controladorPrincipal.navegar("/regisPaquete.fxml", "Enviaos",idEmisor,txtIdentificacion.getText(),txtNuTele.getText());
                controladorPrincipal.cerrarVentana(txtIdentificacion);
            }catch (Exception e){
                System.out.println("Problemas para actualizar");
            }
        }
        else{
            try {
                controladorPrincipal.agregarPersonas(txtIdentificacion.getText(),txtNombre.getText(),txtDireccion.getText(),txtCiudad.getText(),txtNuTele.getText(),txtCorreo.getText());
                System.out.println("Creado");
                controladorPrincipal.navegar("/regisPaquete.fxml", "Enviaos",idEmisor,txtIdentificacion.getText());
                controladorPrincipal.cerrarVentana(txtIdentificacion);
            }catch (Exception e){
                controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}