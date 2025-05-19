package mx.edu.uacm.is.slt.ds.dst;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.util.ArrayList;

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
        Gestor gestor = new Gestor(new ArrayList<Operacion>());
        boolean nombreOp = gestor.getOperaciones().isEmpty();
        System.out.println(nombreOp);
        for (Operacion op: gestor.getOperaciones()) {
            System.out.println(op.getNombre());
            for (Tarea ta: op.getTareas()){
                System.out.println(ta.getNombre());
            }
        }
        welcomeText.setText("Welcome to Drunk Tasker!");
        operacion1.setText("Operacion1 : Levantamiento de Requisitos");
        tarea1.setText("   Tarea1 : Entrevista con el cliente");
        tarea2.setText("   Tarea2 : Documentar requisitos");
        estado.setText("Estado : Creada");
    }
}