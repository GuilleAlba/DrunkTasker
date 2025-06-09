package mx.edu.uacm.is.slt.ds.dst.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controlador para la ventana "Acerca de DrunkTasker".
 */
public class AcercaDeController {

    @FXML private Button btnCerrar;

    @FXML
    public void initialize() {
        btnCerrar.setOnAction(e -> {
            Stage st = (Stage) btnCerrar.getScene().getWindow();
            st.close();
        });
    }
}

