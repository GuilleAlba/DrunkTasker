package mx.edu.uacm.is.slt.ds.dst.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.dst.modelos.EnumEstadoOperacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para la ventana de gestión (management.fxml). Muestra dos paneles:
 * - Panel izquierdo: lista de operaciones con botones para crear, editar, eliminar, pausar, reanudar, suspender.
 * - Panel derecho: lista de tareas de la operación seleccionada, con botones para CRUD de tareas.
 */
public class ManagementController {

    // ------------------------------------------------------------
    // FXML: Panel Operaciones
    // ------------------------------------------------------------
    @FXML private TableView<Operacion> tableOperaciones;
    @FXML private TableColumn<Operacion, String> colGNombre;
    @FXML private TableColumn<Operacion, String> colGEstado;
    @FXML private Button btnNuevaOp_G;
    @FXML private Button btnEditarOp_G;
    @FXML private Button btnEliminarOp_G;
    @FXML private Button btnPausarOp_G;
    @FXML private Button btnReanudarOp_G;
    @FXML private Button btnSuspenderOp_G;

    // ------------------------------------------------------------
    // FXML: Panel Tareas
    // ------------------------------------------------------------
    @FXML private Label lblTituloTareas;
    @FXML private TableView<Tarea> tableTareas;
    @FXML private TableColumn<Tarea, String> colTTitulo;
    @FXML private TableColumn<Tarea, String> colTEstado;
    @FXML private TableColumn<Tarea, String> colTFechaLimite;
    @FXML private Button btnNuevaTarea_G;
    @FXML private Button btnEditarTarea_G;
    @FXML private Button btnEliminarTarea_G;

    // ------------------------------------------------------------
    // FXML: Menú y barra de estado
    // ------------------------------------------------------------
    @FXML private MenuItem menuItemVolver;
    @FXML private MenuItem menuItemSalir;
    @FXML private MenuItem menuItemAcerca;
    @FXML private Label lblStatusGestion;

    // Referencia al singleton Gestor
    private final Gestor gestor = Gestor.getInstancia();

    // Listas observables para alimentar las TableViews
    private ObservableList<Operacion> operacionesData = FXCollections.observableArrayList();
    private ObservableList<Tarea> tareasData = FXCollections.observableArrayList();

    // Operación actualmente seleccionada en la tabla de operaciones
    private Operacion operacionSeleccionada;

    @FXML
    public void initialize() {
        configurarTablaOperaciones();
        configurarTablaTareas();
        cargarOperaciones();
        configurarEventos();
    }

    // ------------------------------------------------------------
    // Configuración de la tabla de operaciones
    // ------------------------------------------------------------
    private void configurarTablaOperaciones() {
        colGNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );
        colGEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado().name())
        );

        // Listener para detectar selección de operación
        tableOperaciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            operacionSeleccionada = newSel;
            if (newSel != null) {
                // Habilitar/Deshabilitar botones según estado
                habilitarBotonesOperacion(true);
                // Mostrar nombre en el panel de tareas
                lblTituloTareas.setText("Tareas de: " + newSel.getNombre());
                // Cargar las tareas de la operación seleccionada
                cargarTareas(newSel);
                tableTareas.setDisable(false);
                btnNuevaTarea_G.setDisable(false);
            } else {
                // No hay operación seleccionada: deshabilitar controles de tareas
                habilitarBotonesOperacion(false);
                lblTituloTareas.setText("Seleccione una Operación");
                tareasData.clear();
                tableTareas.setItems(tareasData);
                tableTareas.setDisable(true);
                habilitarBotonesTarea(false);
                btnNuevaTarea_G.setDisable(true);
            }
        });
    }

    // ------------------------------------------------------------
    // Configuración de la tabla de tareas
    // ------------------------------------------------------------
    private void configurarTablaTareas() {
        colTTitulo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitulo())
        );
        colTEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado().name())
        );

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colTFechaLimite.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaLimite().format(fmt))
        );

        // Listener para habilitar/deshabilitar botones de editar/eliminar tarea
        tableTareas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                habilitarBotonesTarea(true);
            } else {
                habilitarBotonesTarea(false);
            }
        });
    }

    // ------------------------------------------------------------
    // Métodos para habilitar/deshabilitar botones de operaciones
    // ------------------------------------------------------------
    private void habilitarBotonesOperacion(boolean habilitar) {
        btnEditarOp_G.setDisable(!habilitar);
        btnEliminarOp_G.setDisable(!habilitar);
        // Botones Pausar/Reanudar/Suspender dependen del estado actual
        if (habilitar && operacionSeleccionada != null) {
            switch (operacionSeleccionada.getEstado()) {
                case ACTIVA:
                    btnPausarOp_G.setDisable(false);
                    btnReanudarOp_G.setDisable(true);
                    btnSuspenderOp_G.setDisable(false);
                    break;
                case PAUSADA:
                case SUSPENDIDA:
                    btnPausarOp_G.setDisable(true);
                    btnReanudarOp_G.setDisable(false);
                    btnSuspenderOp_G.setDisable(true);
                    break;
                default:
                    btnPausarOp_G.setDisable(true);
                    btnReanudarOp_G.setDisable(true);
                    btnSuspenderOp_G.setDisable(true);
                    break;
            }
        } else {
            btnPausarOp_G.setDisable(true);
            btnReanudarOp_G.setDisable(true);
            btnSuspenderOp_G.setDisable(true);
        }
    }

    // ------------------------------------------------------------
    // Métodos para habilitar/deshabilitar botones de tareas
    // ------------------------------------------------------------
    private void habilitarBotonesTarea(boolean habilitar) {
        btnEditarTarea_G.setDisable(!habilitar);
        btnEliminarTarea_G.setDisable(!habilitar);
    }

    // ------------------------------------------------------------
    // Carga las operaciones del Gestor en la tabla
    // ------------------------------------------------------------
    private void cargarOperaciones() {
        List<Operacion> lista = gestor.obtenerOperacionesAgrupadasPorEstado();
        operacionesData.setAll(lista);
        tableOperaciones.setItems(operacionesData);
    }

    // ------------------------------------------------------------
    // Carga las tareas de una operación en la tabla
    // ------------------------------------------------------------
    private void cargarTareas(Operacion op) {
        List<Tarea> lista = op.getTareas();
        tareasData.setAll(lista);
        tableTareas.setItems(tareasData);
    }

    // ------------------------------------------------------------
    // Asociar eventos a botones y menús
    // ------------------------------------------------------------
    private void configurarEventos() {
        btnNuevaOp_G.setOnAction(this::accionNuevaOperacion);
        btnEditarOp_G.setOnAction(this::accionEditarOperacion);
        btnEliminarOp_G.setOnAction(this::accionEliminarOperacion);
        btnPausarOp_G.setOnAction(e -> cambiarEstadoOperacion(EnumEstadoOperacion.PAUSADA));
        btnReanudarOp_G.setOnAction(e -> cambiarEstadoOperacion(EnumEstadoOperacion.ACTIVA));
        btnSuspenderOp_G.setOnAction(e -> cambiarEstadoOperacion(EnumEstadoOperacion.SUSPENDIDA));

        btnNuevaTarea_G.setOnAction(this::accionNuevaTarea);
        btnEditarTarea_G.setOnAction(this::accionEditarTarea);
        btnEliminarTarea_G.setOnAction(this::accionEliminarTarea);

        // Menú "Volver a Dashboard"
        menuItemVolver.setOnAction(e -> {
            Stage st = (Stage) menuItemVolver.getParentPopup().getOwnerWindow();
            st.close();
        });
        // Menú "Salir"
        menuItemSalir.setOnAction(e -> System.exit(0));
        // Menú "Acerca de"
        menuItemAcerca.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/mx/edu/uacm/is/slt/ds/dst/vistas/acerca-de.fxml"
                        )
                );
                Scene escena = new Scene(loader.load());
                Stage ventana = new Stage();
                ventana.setTitle("Acerca de DrunkTasker");
                ventana.setScene(escena);
                ventana.show();
            } catch (Exception ex) {
                ex.printStackTrace();
                lblStatusGestion.setText("Error al abrir Acerca de.");
            }
        });
    }

    // ------------------------------------------------------------
    // Acciones sobre Operaciones
    // ------------------------------------------------------------

    /**
     * Muestra el diálogo para crear nueva operación.
     */
    @FXML
    private void accionNuevaOperacion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/mx/edu/uacm/is/slt/ds/dst/vistas/formulario-operacion.fxml"
                    )
            );
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(loader.load());

            // Configuramos el controlador del diálogo
            EditorOperacionController ctrl = loader.getController();
            ctrl.setGestor(gestor);

            dialog.showAndWait();
            cargarOperaciones();
            lblStatusGestion.setText("Operación creada.");
        } catch (Exception ex) {
            ex.printStackTrace();
            lblStatusGestion.setText("Error al crear Operación.");
        }
    }

    /**
     * Muestra el diálogo para editar la operación seleccionada.
     */
    private void accionEditarOperacion(ActionEvent event) {
        if (operacionSeleccionada == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/mx/edu/uacm/is/slt/ds/dst/vistas/formulario-operacion.fxml"
                    )
            );
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(loader.load());

            EditorOperacionController ctrl = loader.getController();
            ctrl.setGestor(gestor);
            ctrl.setOperacionEdit(operacionSeleccionada);

            dialog.showAndWait();
            cargarOperaciones();
            lblStatusGestion.setText("Operación editada.");
        } catch (Exception ex) {
            ex.printStackTrace();
            lblStatusGestion.setText("Error al editar Operación.");
        }
    }

    /**
     * Elimina la operación seleccionada (pide confirmación).
     */
    private void accionEliminarOperacion(ActionEvent event) {
        if (operacionSeleccionada == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Operación");
        alert.setHeaderText("¿Confirma eliminar la Operación “" + operacionSeleccionada.getNombre() + "”?");
        alert.setContentText("Esta acción no podrá deshacerse.");

        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.OK) {
                gestor.eliminarOperacion(operacionSeleccionada.getId());
                cargarOperaciones();
                lblStatusGestion.setText("Operación eliminada.");
            }
        });
    }

    /**
     * Cambia el estado de la operación seleccionada (pausar, reanudar o suspender).
     *
     * @param nuevoEstado Nuevo estado a asignar.
     */
    private void cambiarEstadoOperacion(EnumEstadoOperacion nuevoEstado) {
        if (operacionSeleccionada == null) return;
        operacionSeleccionada.setEstado(nuevoEstado);
        cargarOperaciones();
        lblStatusGestion.setText("Estado cambiado a " + nuevoEstado.name());
    }

    // ------------------------------------------------------------
    // Acciones sobre Tareas
    // ------------------------------------------------------------

    /**
     * Muestra el diálogo para crear una nueva tarea en la operación seleccionada.
     */
    private void accionNuevaTarea(ActionEvent event) {
        if (operacionSeleccionada == null) {
            lblStatusGestion.setText("Seleccione primero una Operación.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/mx/edu/uacm/is/slt/ds/dst/vistas/formulario-tarea.fxml"
                    )
            );
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(loader.load());

            EditorTareaController ctrl = loader.getController();
            ctrl.setGestorYOperacion(gestor, operacionSeleccionada);

            dialog.showAndWait();
            cargarTareas(operacionSeleccionada);
            lblStatusGestion.setText("Tarea creada.");
        } catch (Exception ex) {
            ex.printStackTrace();
            lblStatusGestion.setText("Error al crear Tarea.");
        }
    }

    /**
     * Muestra el diálogo para editar la tarea seleccionada.
     */
    private void accionEditarTarea(ActionEvent event) {
        Tarea tSel = tableTareas.getSelectionModel().getSelectedItem();
        if (tSel == null) {
            lblStatusGestion.setText("Seleccione primero una Tarea.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/mx/edu/uacm/is/slt/ds/dst/vistas/formulario-tarea.fxml"
                    )
            );
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(loader.load());

            EditorTareaController ctrl = loader.getController();
            ctrl.setGestorYOperacion(gestor, operacionSeleccionada);
            ctrl.setTareaEdit(tSel);

            dialog.showAndWait();
            cargarTareas(operacionSeleccionada);
            lblStatusGestion.setText("Tarea editada.");
        } catch (Exception ex) {
            ex.printStackTrace();
            lblStatusGestion.setText("Error al editar Tarea.");
        }
    }

    /**
     * Elimina la tarea seleccionada (pide confirmación).
     */
    private void accionEliminarTarea(ActionEvent event) {
        Tarea tSel = tableTareas.getSelectionModel().getSelectedItem();
        if (tSel == null) {
            lblStatusGestion.setText("Seleccione primero una Tarea.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Tarea");
        alert.setHeaderText("¿Confirma eliminar la Tarea “" + tSel.getTitulo() + "”?");
        alert.setContentText("Esta acción no podrá deshacerse.");

        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.OK) {
                gestor.eliminarTarea(operacionSeleccionada.getId(), tSel.getId());
                cargarTareas(operacionSeleccionada);
                lblStatusGestion.setText("Tarea eliminada.");
            }
        });
    }

    // ------------------------------------------------------------
    // Permite preseleccionar una operación si venimos desde Dashboard
    // ------------------------------------------------------------
    /**
     * Si abrimos esta pantalla directamente pasando una Operación, la preseleccionamos
     * y cargamos sus tareas.
     *
     * @param op Operación a preseleccionar.
     */
    public void setOperacionSeleccionada(Operacion op) {
        this.operacionSeleccionada = op;
        cargarOperaciones();
        if (op != null) {
            tableOperaciones.getSelectionModel().select(op);
            // El listener de selección se encargará de habilitar botones y cargar tareas
        }
    }
}
