package mx.edu.uacm.is.slt.ds.dst.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.modelos.EnumEstadoTarea;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.time.LocalDate;

/**
 * Controlador para el diálogo Crear/Editar Tarea (formulario-tarea.fxml).
 */
public class EditorTareaController {

    @FXML private TextField tfTitulo;                 // Campo de texto para el título
    @FXML private TextArea taDescripcion;             // Área de texto para la descripción
    @FXML private DatePicker dpFechaLimite;           // Selector de fecha límite
    @FXML private ComboBox<EnumEstadoTarea> cbEstado; // ComboBox para elegir estado de la tarea
    @FXML private TextArea taPrecondiciones;          // Área de texto para precondiciones
    @FXML private TextArea taPostcondiciones;         // Área de texto para postcondiciones
    @FXML private TextArea taComportamiento;          // Área de texto para comportamiento

    @FXML private ButtonType btnGuardar;              // Botón Guardar
    @FXML private ButtonType btnCancelar;             // Botón Cancelar

    @FXML private DialogPane dialogPane;              // Panel raíz del diálogo

    private Gestor gestor;                            // Referencia al singleton Gestor
    private Operacion operacionActual;                // Operación a la que se asocia la tarea
    private Tarea tareaEdit;                          // Si es distinto de null, estamos editando

    @FXML
    public void initialize() {
        // Poblar el combo con los estados de tarea
        cbEstado.getItems().setAll(EnumEstadoTarea.values());

        // Interceptar el botón “Guardar” para validar antes de cerrar el diálogo
        Button botonGuardar = (Button) dialogPane.lookupButton(btnGuardar);
        botonGuardar.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    if (!hacerGuardar()) {
                        // Si la validación falla, cancelamos el cierre
                        event.consume();
                    }
                }
        );
    }

    /**
     * Realiza la validación y, en caso exitoso, crea o edita la tarea.
     *
     * @return true si pasa las validaciones y se crea/edita la tarea; false si hay error.
     */
    private boolean hacerGuardar() {
        // Validar título
        String titulo = tfTitulo.getText().trim();
        if (titulo.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "El título es obligatorio.").showAndWait();
            return false;
        }

        // Validar fecha límite
        LocalDate fechaLim = dpFechaLimite.getValue();
        if (fechaLim == null) {
            new Alert(Alert.AlertType.ERROR, "La fecha límite es obligatoria.").showAndWait();
            return false;
        }

        // Recoger campos opcionales
        String desc = taDescripcion.getText().trim();
        EnumEstadoTarea estSel = cbEstado.getSelectionModel().getSelectedItem();
        if (estSel == null) {
            estSel = EnumEstadoTarea.PENDIENTE;
        }
        String pre = taPrecondiciones.getText().trim();
        String post = taPostcondiciones.getText().trim();
        String comp = taComportamiento.getText().trim();

        // Verificar que exista la operación actual
        if (operacionActual == null) {
            new Alert(Alert.AlertType.ERROR, "No existe Operación seleccionada.").showAndWait();
            return false;
        }

        if (tareaEdit == null) {
            // Crear nueva tarea
            Tarea tNueva = gestor.crearTarea(
                    operacionActual.getId(),
                    titulo,
                    desc,
                    fechaLim
            );
            if (tNueva != null) {
                // Asignamos los valores adicionales de estado y texto
                tNueva.setEstado(estSel);
                tNueva.setPrecondiciones(pre);
                tNueva.setPostcondiciones(post);
                tNueva.setComportamiento(comp);
            }
        } else {
            // Editar tarea existente
            gestor.editarTarea(
                    operacionActual.getId(),
                    tareaEdit.getId(),
                    titulo,
                    desc,
                    fechaLim,
                    estSel,
                    pre,
                    post,
                    comp
            );
        }

        return true;
    }

    /**
     * Se setea el Gestor (singleton compartido) y la operación actual.
     *
     * @param gestor Instancia única de Gestor.
     * @param op     Operación a la que pertenece la nueva tarea.
     */
    public void setGestorYOperacion(Gestor gestor, Operacion op) {
        this.gestor = gestor;
        this.operacionActual = op;
    }

    /**
     * Si venimos a “Editar” una tarea, pasamos la instancia para precargar campos.
     *
     * @param t Tarea a editar.
     */
    public void setTareaEdit(Tarea t) {
        this.tareaEdit = t;
        if (t != null) {
            tfTitulo.setText(t.getTitulo());
            taDescripcion.setText(t.getDescripcion());
            dpFechaLimite.setValue(t.getFechaLimite());
            cbEstado.getSelectionModel().select(t.getEstado());
            taPrecondiciones.setText(t.getPrecondiciones());
            taPostcondiciones.setText(t.getPostcondiciones());
            taComportamiento.setText(t.getComportamiento());
        }
    }
}
