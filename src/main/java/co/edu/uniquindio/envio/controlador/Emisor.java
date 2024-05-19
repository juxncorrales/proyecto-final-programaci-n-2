package co.edu.uniquindio.envio.controlador;

import co.edu.uniquindio.envio.modelo.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Emisor implements Initializable {

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

    public Emisor() throws Exception {
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

    public void siguiRemi(ActionEvent actionEvent) {
        if (dataPersona){
            try {
                controladorPrincipal.actualizarPersona(txtIdentificacion.getText(),txtNombre.getText(),txtDireccion.getText(),txtCiudad.getText(),txtNuTele.getText(),txtCorreo.getText());
                System.out.println("se actualizó");
                controladorPrincipal.navegar("/receptor.fxml", "Enviaos",txtIdentificacion.getText());
                controladorPrincipal.cerrarVentana(txtIdentificacion);
            }catch (Exception e){
                System.out.println("Problemas para actualizar");
            }
        }
        else{
            try {
                controladorPrincipal.agregarPersonas(txtIdentificacion.getText(),txtNombre.getText(),txtDireccion.getText(),txtCiudad.getText(),txtNuTele.getText(),txtCorreo.getText());
                System.out.println("Creado");
                controladorPrincipal.navegar("/receptor.fxml", "Enviaos",txtIdentificacion.getText());
                controladorPrincipal.cerrarVentana(txtIdentificacion);
            }catch (Exception e){
                controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
