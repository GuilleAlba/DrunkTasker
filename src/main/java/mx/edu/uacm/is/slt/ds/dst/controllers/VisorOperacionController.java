package mx.edu.uacm.is.slt.ds.dst.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.modelos.EnumEstadoOperacion;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para la ventana “Visor de Operación” (visor-operacion.fxml),
 * que muestra las tareas de una operación seleccionada y permite acciones específicas.
 */
public class VisorOperacionController {

    // ------------------------------------------------------------
    // FXML: Controles para buscar operación y mostrar título
    // ------------------------------------------------------------
    @FXML private TextField tfBuscarOperacion;
    @FXML private Label lblTituloOperacion;

    // ------------------------------------------------------------
    // FXML: Botones para pausar, reanudar, detener operación
    // ------------------------------------------------------------
    @FXML private Button btnPausarOperacion;
    @FXML private Button btnReanudarOperacion;
    @FXML private Button btnDetenerOperacion;

    // ------------------------------------------------------------
    // FXML: Botones para CRUD de tareas y reordenar
    // ------------------------------------------------------------
    @FXML private Button btnNuevaTarea;
    @FXML private Button btnEditarTarea;
    @FXML private Button btnEliminarTarea;
    @FXML private Button btnReordenarTareas;

    // ------------------------------------------------------------
    // FXML: Tabla de tareas y sus columnas
    // ------------------------------------------------------------
    @FXML private TableView<Tarea> tableTareas;
    @FXML private TableColumn<Tarea, String> colTitulo;
    @FXML private TableColumn<Tarea, String> colEstadoTarea;
    @FXML private TableColumn<Tarea, String> colFechaLimite;

    // ------------------------------------------------------------
    // FXML: Paginación de tareas
    // ------------------------------------------------------------
    @FXML private Pagination paginationTareas;

    // ------------------------------------------------------------
    // FXML: Menú "Volver" y "Acerca de"
    // ------------------------------------------------------------
    @FXML private MenuItem menuItemVolver;
    @FXML private MenuItem menuItemAcerca;

    // Referencia al singleton Gestor
    private final Gestor gestor = Gestor.getInstancia();
    private Operacion operacionActual;
    private ObservableList<Tarea> tareasData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla de tareas
        colTitulo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo())
        );
        colEstadoTarea.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado().name())
        );
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colFechaLimite.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFechaLimite().format(fmt))
        );

        configurarEventos();
    }

    /**
     * Este método debe llamarse desde DashboardController:
     *   VisorOperacionController ctrl = loader.getController();
     *   ctrl.setOperacion(opSeleccionada);
     *
     * @param op Operación cuya lista de tareas se va a mostrar.
     */
    public void setOperacion(Operacion op) {
        this.operacionActual = op;
        lblTituloOperacion.setText("Tareas de Operación: " + op.getNombre());
        actualizarBotonesOperacion();
        cargarTareas();
    }

    // ------------------------------------------------------------
    // Asociar eventos a botones y menús
    // ------------------------------------------------------------
    private void configurarEventos() {
        btnPausarOperacion.setOnAction(e -> accionPausar());
        btnReanudarOperacion.setOnAction(e -> accionReanudar());
        btnDetenerOperacion.setOnAction(e -> accionDetener());

        btnNuevaTarea.setOnAction(e -> abrirEditorTarea(null));
        btnEditarTarea.setOnAction(e -> {
            Tarea sel = tableTareas.getSelectionModel().getSelectedItem();
            if (sel != null) abrirEditorTarea(sel);
        });
        btnEliminarTarea.setOnAction(e -> {
            Tarea sel = tableTareas.getSelectionModel().getSelectedItem();
            if (sel != null) confirmarEliminarTarea(sel);
        });
        btnReordenarTareas.setOnAction(e -> reordenarSeleccionado());

        // Filtrar tareas en tiempo real al escribir
        tfBuscarOperacion.textProperty().addListener((obs, oldText, newText) -> filtrarTareas(newText));

        // Menú "Volver a Principal"
        menuItemVolver.setOnAction(e -> {
            Stage stage = (Stage) menuItemVolver.getParentPopup().getOwnerWindow();
            stage.close();
        });
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
            }
        });
    }

    // ------------------------------------------------------------
    // Carga las tareas de la operación y configura paginación
    // ------------------------------------------------------------
    private void cargarTareas() {
        List<Tarea> lista = operacionActual.getTareas();
        tareasData.setAll(lista);

        int itemsPorPagina = 10;
        int pageCount = (int) Math.ceil((double) lista.size() / itemsPorPagina);
        paginationTareas.setPageCount(pageCount == 0 ? 1 : pageCount);
        paginationTareas.setCurrentPageIndex(0);

        paginationTareas.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            int page = newIndex.intValue();
            int from = page * itemsPorPagina;
            int to = Math.min(from + itemsPorPagina, lista.size());
            if (from <= to) {
                tableTareas.setItems(FXCollections.observableArrayList(lista.subList(from, to)));
            }
        });

        if (!lista.isEmpty()) {
            int to = Math.min(itemsPorPagina, lista.size());
            tableTareas.setItems(FXCollections.observableArrayList(lista.subList(0, to)));
        } else {
            tableTareas.setItems(FXCollections.observableArrayList());
        }
    }

    // ------------------------------------------------------------
    // Filtra las tareas según el texto ingresado
    // ------------------------------------------------------------
    private void filtrarTareas(String filtro) {
        List<Tarea> todas = operacionActual.getTareas();
        if (filtro == null || filtro.isEmpty()) {
            tareasData.setAll(todas);
        } else {
            String minus = filtro.toLowerCase();
            tareasData.setAll(
                    todas.stream()
                            .filter(t -> t.getTitulo().toLowerCase().contains(minus))
                            .toList()
            );
        }
        recargarPaginacion();
    }

    // ------------------------------------------------------------
    // Reajusta la paginación tras filtrar
    // ------------------------------------------------------------
    private void recargarPaginacion() {
        List<Tarea> lista = tareasData;
        int itemsPorPagina = 10;
        int pageCount = (int) Math.ceil((double) lista.size() / itemsPorPagina);
        paginationTareas.setPageCount(pageCount == 0 ? 1 : pageCount);
        paginationTareas.setCurrentPageIndex(0);

        if (!lista.isEmpty()) {
            int to = Math.min(itemsPorPagina, lista.size());
            tableTareas.setItems(FXCollections.observableArrayList(lista.subList(0, to)));
        } else {
            tableTareas.setItems(FXCollections.observableArrayList());
        }
    }

    // ------------------------------------------------------------
    // Actualiza los botones de la operación según su estado
    // ------------------------------------------------------------
    private void actualizarBotonesOperacion() {
        switch (operacionActual.getEstado()) {
            case ACTIVA:
                btnPausarOperacion.setDisable(false);
                btnReanudarOperacion.setDisable(true);
                btnDetenerOperacion.setDisable(false);
                break;
            case PAUSADA:
            case SUSPENDIDA:
                btnPausarOperacion.setDisable(true);
                btnReanudarOperacion.setDisable(false);
                btnDetenerOperacion.setDisable(true);
                break;
            default:
                btnPausarOperacion.setDisable(true);
                btnReanudarOperacion.setDisable(true);
                btnDetenerOperacion.setDisable(true);
                break;
        }
    }

    // ------------------------------------------------------------
    // Acciones para cambiar estado de la operación
    // ------------------------------------------------------------
    private void accionPausar() {
        operacionActual.setEstado(EnumEstadoOperacion.PAUSADA);
        actualizarBotonesOperacion();
        cargarTareas();
    }

    private void accionReanudar() {
        operacionActual.setEstado(EnumEstadoOperacion.ACTIVA);
        actualizarBotonesOperacion();
        cargarTareas();
    }

    private void accionDetener() {
        operacionActual.setEstado(EnumEstadoOperacion.SUSPENDIDA);
        actualizarBotonesOperacion();
        cargarTareas();
    }

    // ------------------------------------------------------------
    // Elimina la tarea seleccionada (con confirmación)
    // ------------------------------------------------------------
    private void confirmarEliminarTarea(Tarea t) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Deseas eliminar la tarea \"" + t.getTitulo() + "\"?");
        alert.setContentText("Esta acción no se puede deshacer.");
        ButtonType ok = new ButtonType("Eliminar");
        ButtonType cancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(ok, cancel);

        alert.showAndWait().ifPresent(type -> {
            if (type == ok) {
                operacionActual.getTareas().remove(t);
                cargarTareas();
            }
        });
    }

    // ------------------------------------------------------------
    // Reordena la tarea seleccionada para que pase al siguiente lugar
    // ------------------------------------------------------------
    private void reordenarSeleccionado() {
        Tarea sel = tableTareas.getSelectionModel().getSelectedItem();
        if (sel != null) {
            List<Tarea> lista = operacionActual.getTareas();
            int idx = lista.indexOf(sel);
            if (idx < lista.size() - 1) {
                Tarea siguiente = lista.get(idx + 1);
                lista.set(idx + 1, sel);
                lista.set(idx, siguiente);
                cargarTareas();
            }
        }
    }

    // ------------------------------------------------------------
    // Abre el diálogo para crear/editar tarea
    // ------------------------------------------------------------
    private void abrirEditorTarea(Tarea t) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/mx/edu/uacm/is/slt/ds/dst/vistas/formulario-tarea.fxml"
                    )
            );
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(loader.load());

            EditorTareaController ctrl = loader.getController();
            ctrl.setGestorYOperacion(gestor, operacionActual);
            if (t != null) ctrl.setTareaEdit(t);

            dialog.showAndWait();
            cargarTareas();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
