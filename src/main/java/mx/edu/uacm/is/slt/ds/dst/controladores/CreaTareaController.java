package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;
import mx.edu.uacm.is.slt.ds.dst.servicios.Mensajes;

import java.io.IOException;

public class CreaTareaController {

    @FXML
    private TextField nombre;
    @FXML
    private TextArea descripcion;
    @FXML
    private CheckBox pausar;
    @FXML
    private ComboBox<String> prioridad;
    @FXML
    private ComboBox<String> previa;
    private String prioridadElegida = "";
    private final Gestor gestor = Gestor.getInstance();
    private final Mensajes mensaje = new Mensajes();

    @FXML
    public void initialize() {
        prioridad.getItems().addAll("Inicial", "Paralela", "Tarea previa");
        prioridad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    prioridadElegida = newValue;
                }
            }
        });
    }

    @FXML
    public void btnRegresar() {
        try {
            gestor.navega("crea-operacion.fxml", "Drunk-Tasker");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void creaTarea() {
        Tarea nueva;
        if (gestor.getOperacionSleccionada().getTareas().isEmpty()) {
            int id = gestor.getOperacionSleccionada().getId() * 10 ;
            nueva = new Tarea(id + 1,
                    nombre.getText(), descripcion.getText(),
                    "CREADA",prioridadElegida,"",pausar.isSelected());
            gestor.agregaTarea(nueva);
            mensaje.mostrarExito("Tarea agregada correctamente!");
        } else {
            int tam = gestor.getOperacionSleccionada().getTareas().size();
            if (tam > 8) {
                mensaje.mostrarError("No es posible agregar Tarea, limite de tareas alcanzado");
            } else {
                nueva = new Tarea(gestor.getOperacionSleccionada().getTareas().get(tam - 1).getId() + 1,
                        nombre.getText(), descripcion.getText(),
                        "CREADA", prioridadElegida, "", pausar.isSelected());
                gestor.agregaTarea(nueva);
                mensaje.mostrarExito("Tarea agregada correctamente!");
            }
        }
        try {
            gestor.navega("crea-operacion.fxml", "Crea Operacion");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void validaCrea() {
        if (nombre.getText().isEmpty() ||
        descripcion.getText().isEmpty() ||
        prioridadElegida.isEmpty()) {
            mensaje.mostrarError("Debe llenar todos los campos obligatoros *");
        } else {
            if (mensaje.mostrarConfirmacion("Confirmacion", "Agregar esta Tarea a la operacion?")) {
                creaTarea();
            } else {
                mensaje.mostrarError("No se agrego Tarea!");
            }
        }
    }
}
