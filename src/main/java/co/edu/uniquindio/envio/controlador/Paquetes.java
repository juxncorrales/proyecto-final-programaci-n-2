package co.edu.uniquindio.envio.controlador;

import co.edu.uniquindio.envio.controlador.ControladorPrincipal;
import co.edu.uniquindio.envio.modelo.Paquete;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Paquetes implements Initializable {
    public TextField txtCode;
    public TableView<Paquete> tablaPaquetes;
    public TableColumn<Paquete, String> descri;
    public TableColumn<Paquete, String> pesos;
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public Paquetes() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descri.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        pesos.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getPeso()));

    }
    public void buscar(ActionEvent actionEvent) {
        System.out.println("SIENTERS");
        List<Paquete> paquetes = controladorPrincipal.obtenerPaquetesPorCodigo(txtCode.getText());
        tablaPaquetes.setItems(FXCollections.observableArrayList(paquetes));

    }


}
