package co.edu.uniquindio.envio.controlador;

import co.edu.uniquindio.envio.modelo.EnvioHistorico;
import co.edu.uniquindio.envio.modelo.Paquete;
import co.edu.uniquindio.envio.modelo.Persona;
import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import co.edu.uniquindio.envio.modelo.enums.TipoEnvio;
import co.edu.uniquindio.envio.utils.EnvioSms;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class Seguimiento implements Initializable {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    public TableView<EnvioHistorico> tablaSegui;
    public TableColumn<EnvioHistorico, String> code;
    public TableColumn<EnvioHistorico, String> fecha;
    public TableColumn<EnvioHistorico, String> estado;
    public TableColumn<EnvioHistorico, String> tipo;
    public TableColumn<EnvioHistorico, String> ciudad;
    public TableColumn<EnvioHistorico, String> valor;
    public DatePicker dataFecha;
    public ChoiceBox selectestados;
    public ChoiceBox selecttipo;
    public Label idReceptor;

    public Seguimiento() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
        {
            // Configurar el PropertyValueFactory para cada columna de la tabla
            code.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigoEnvio()));
            fecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
            estado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstados().toString()));
            tipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
            ciudad.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getCiudad()));
            valor.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getValor()));
            tablaSegui.setItems(FXCollections.observableArrayList(controladorPrincipal.datos()));
            //ESCUCHA LOS CLICK DE LA TABLA Y SE EJECUTA
            tablaSegui.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    EnvioHistorico selectedEnvio = tablaSegui.getSelectionModel().getSelectedItem();
                    if (selectedEnvio != null) {
                        System.out.println("Código del envío seleccionado: " + selectedEnvio.getCodigoEnvio());
                        controladorPrincipal.navegar("/dataEnvio.fxml","Data Envios",selectedEnvio.getCodigoEnvio(), tablaSegui);
                    }
                }
            });

        }

    public void filtar(ActionEvent actionEvent) {
        if(dataFecha.getValue()==null || selectestados.getValue()==null || selecttipo.getValue()==null){
            controladorPrincipal.mostrarAlerta("Todos los campos son obligatorios para filtrar", Alert.AlertType.ERROR);
        }
        else {
            String tipos = (String) selecttipo.getValue();
            TipoEnvio tipo = TipoEnvio.valueOf(tipos);
            String estados = (String) selectestados.getValue();
            TipEstado estado = TipEstado.valueOf(estados);
            LocalDate date = dataFecha.getValue();

            List<EnvioHistorico> enviosFiltrados = controladorPrincipal.filtrarDatos(date, tipo, estado);
            tablaSegui.setItems(FXCollections.observableArrayList(enviosFiltrados));
        }
    }
}

