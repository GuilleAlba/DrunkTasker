package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import mx.edu.uacm.is.slt.ds.dst.HelloApplication;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.io.IOException;
import java.util.ArrayList;

import static mx.edu.uacm.is.slt.ds.dst.HelloApplication.primaryStage;

public class HelloController {

    private final Gestor gestor = Gestor.getInstance();

    @FXML
    protected void irOperacionesButtonClick() throws IOException {
        gestor.navega("gestion-operaciones.fxml", "Gestion de Operaciones");
    }

    @FXML
    protected void  irAcercaDe() throws IOException {
        gestor.navega("acerca-de.fxml","Acerca de");
    }
}