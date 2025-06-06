package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.io.IOException;

public class GestionOperacionesController {

    @FXML
    private TableView<Operacion> operacionesTable;

    private final Gestor gestor = Gestor.getInstance();

    @FXML
    public void initialize() {
        operacionesTable.setItems(gestor.getOperaciones());
    }

    @FXML
    public void irNuevaOperacion() {
        if (gestor.getOperaciones().isEmpty()) {
            gestor.setOperacionSleccionada(new Operacion(1));
        } else {
            int tam = gestor.getOperaciones().size();
            gestor.setOperacionSleccionada(new Operacion(
                    gestor.getOperaciones().get(tam - 1).getId() + 1
            ));
        }
        try {
            gestor.navega("crea-operacion.fxml", "Gestion de Operaciones");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnRegresar() {
        try {
            gestor.navega("hello-view.fxml", "Drunk-Tasker");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
