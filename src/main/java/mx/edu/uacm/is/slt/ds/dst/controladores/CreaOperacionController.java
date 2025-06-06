package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;
import mx.edu.uacm.is.slt.ds.dst.servicios.Mensajes;

import java.io.IOException;

public class CreaOperacionController {
    @FXML
    private TableView<Tarea> tareasTable;
    @FXML
    private TextField nombre;
    @FXML
    private TextArea descripcion;
    private final Gestor gestor = Gestor.getInstance();
    private final Mensajes mensaje = new Mensajes();

    @FXML
    public void initialize() {
        tareasTable.setItems(gestor.getOperacionSleccionada().getTareas());
    }

    @FXML
    public void regresar() throws IOException {
        gestor.navega("gestion-operaciones.fxml","Gestion de Operaciones");
    }

    @FXML
    public void irAgregarTarea() throws IOException {
        gestor.navega("crea-tarea.fxml","Creacion de Tarea");
    }

    @FXML
    public void validaCreaOp() {
        if (nombre.getText().isEmpty() ||
            descripcion.getText().isEmpty()) {
            mensaje.mostrarError("Debe llenar todos los campos obligatoros *");
        } else {
            if (mensaje.mostrarConfirmacion("Confirmacion", "Agregar esta Operacion?")) {
                creaOperacion();
            } else {
                mensaje.mostrarError("No se agrego Operacion!");
            }
        }
    }

    public void creaOperacion() {
        int tam = gestor.getOperaciones().size();
        if (tam > 8) {
            mensaje.mostrarError("No es posible agregar Operacion, limite de tareas alcanzado");
        } else {
            gestor.getOperacionSleccionada().setNombre(nombre.getText());
            gestor.getOperacionSleccionada().setDescripcion(descripcion.getText());
            gestor.getOperacionSleccionada().setEstado("CREADA");
            gestor.agregarOperacion(gestor.getOperacionSleccionada());
            mensaje.mostrarExito("Operacion agregada correctamente!");
        }
        try {
            gestor.navega("gestion-operaciones.fxml", "Gestion de Operaciones");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
