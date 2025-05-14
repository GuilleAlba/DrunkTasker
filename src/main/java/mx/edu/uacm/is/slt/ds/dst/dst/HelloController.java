package mx.edu.uacm.is.slt.ds.dst.dst;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.edu.uacm.is.slt.ds.dst.dst.servicios.Gestor;

public class HelloController {


    @FXML
    private Label welcomeText;
    @FXML
    private Label operacion1;
    @FXML
    private Label tarea1;
    @FXML
    private Label tarea2;
    @FXML
    private Label estado;

    @FXML
    protected void onHelloButtonClick() {
        Gestor gestor = new Gestor();
        String nombreOp = gestor.getOperaciones().toString();
        System.out.println(nombreOp);
        welcomeText.setText("Welcome to Drunk Tasker!");
        operacion1.setText("Operacion1 : Levantamiento de Requisitos");
        tarea1.setText("   Tarea1 : Entrevista con el cliente");
        tarea2.setText("   Tarea2 : Documentar requisitos");
        estado.setText("Estado : Creada");
    }
}