package mx.edu.uacm.is.slt.ds.dst.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mx.edu.uacm.is.slt.ds.dst.modelos.EnumEstadoOperacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

/**
 * Controlador para el diálogo Crear/Editar Operación.
 */
public class EditorOperacionController {

    // Estos fx:id deben coincidir exactamente con los de formulario-operacion.fxml
    @FXML private TextField tfNombre;                  // Campo de texto para el nombre
    @FXML private TextArea taDescripcion;              // Área de texto para la descripción
    @FXML private ComboBox<EnumEstadoOperacion> cbEstado; // ComboBox para elegir el estado

    @FXML private ButtonType btnGuardar;               // Botón Guardar
    @FXML private ButtonType btnCancelar;              // Botón Cancelar

    @FXML private DialogPane dialogPane;               // Panel raíz del diálogo

    private Gestor gestor;                             // Referencia al singleton Gestor
    private Operacion operacionEdit;                   // Si es distinto de null, estamos editando

    @FXML
    public void initialize() {
        // Poblar el ComboBox con todos los valores de EnumEstadoOperacion
        cbEstado.getItems().setAll(EnumEstadoOperacion.values());

        // Capturamos el botón “Guardar” para validar antes de cerrar el diálogo
        Button botonGuardar = (Button) dialogPane.lookupButton(btnGuardar);
        botonGuardar.addEventFilter(ActionEvent.ACTION, event -> {
            if (!hacerGuardar()) {
                // Si la validación falla, cancelamos el cierre automático
                event.consume();
            }
        });
    }

    /**
     * Este método se dispara al hacer clic en “Guardar”:
     * - Si operacionEdit == null, crea una nueva operación.
     * - Si operacionEdit != null, aplica los cambios a la operación existente.
     *
     * @return true si el guardado fue exitoso; false si hay validación pendiente.
     */
    private boolean hacerGuardar() {
        String nombre = tfNombre.getText().trim();
        if (nombre.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "El nombre es obligatorio.").showAndWait();
            return false;
        }

        String descripcion = taDescripcion.getText().trim();
        EnumEstadoOperacion estadoSeleccionado = cbEstado.getSelectionModel().getSelectedItem();
        if (estadoSeleccionado == null) {
            estadoSeleccionado = EnumEstadoOperacion.NO_INICIADA;
        }

        if (operacionEdit == null) {
            // Creación de nueva operación
            Operacion nuevaOp = gestor.crearOperacion(nombre, descripcion);
            nuevaOp.setEstado(estadoSeleccionado);
        } else {
            // Edición de la operación existente
            gestor.editarOperacion(
                    operacionEdit.getId(),
                    nombre,
                    descripcion
            );
            operacionEdit.setEstado(estadoSeleccionado);
        }

        return true;
    }

    /**
     * Se setea el Gestor (singleton compartido) desde quien abre este diálogo.
     *
     * @param gestor Instancia única de Gestor.
     */
    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    /**
     * Si venimos a “Editar” una operación, pasamos la instancia para precargar campos.
     *
     * @param op Operación a editar.
     */
    public void setOperacionEdit(Operacion op) {
        this.operacionEdit = op;
        if (op != null) {
            tfNombre.setText(op.getNombre());
            taDescripcion.setText(op.getDescripcion());
            cbEstado.getSelectionModel().select(op.getEstado());
        }
    }

    ////////////////////////////
    // Getters y Setters adicionales (opcionales)
    ////////////////////////////
    public TextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(TextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public TextArea getTaDescripcion() {
        return taDescripcion;
    }

    public void setTaDescripcion(TextArea taDescripcion) {
        this.taDescripcion = taDescripcion;
    }

    public ComboBox<EnumEstadoOperacion> getCbEstado() {
        return cbEstado;
    }

    public void setCbEstado(ComboBox<EnumEstadoOperacion> cbEstado) {
        this.cbEstado = cbEstado;
    }

    public ButtonType getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(ButtonType btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public ButtonType getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(ButtonType btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public DialogPane getDialogPane() {
        return dialogPane;
    }

    public void setDialogPane(DialogPane dialogPane) {
        this.dialogPane = dialogPane;
    }

    public Operacion getOperacionEdit() {
        return operacionEdit;
    }

    public void setOperacionEditField(Operacion operacionEdit) {
        this.operacionEdit = operacionEdit;
    }
}




