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
import mx.edu.uacm.is.slt.ds.dst.modelos.EnumEstadoTarea;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para la ventana "Dashboard" de DrunkTasker.
 * Muestra KPIs (total de operaciones, tareas pendientes/completadas hoy) y lista operaciones recientes.
 */
public class DashboardController {

    @FXML private Label lblTotalOperaciones;
    @FXML private Label lblTareasPendientes;
    @FXML private Label lblTareasCompletadasHoy;
    @FXML private Button btnIrGestion;
    @FXML private Button btnNuevaOperacion;
    @FXML private TableView<Operacion> tableOperaciones;
    @FXML private TableColumn<Operacion, String> colNombre;
    @FXML private TableColumn<Operacion, String> colEstado;
    @FXML private TableColumn<Operacion, String> colFechaCreacion;
    @FXML private TableColumn<Operacion, Void> colAcciones;
    @FXML private MenuItem menuItemSalir;
    @FXML private MenuItem menuItemAcerca;
    @FXML private Label lblStatus;

    // Usamos siempre la misma instancia de Gestor (singleton)
    private final Gestor gestor = Gestor.getInstancia();
    private ObservableList<Operacion> operacionesData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();            // Configura columnas de la tabla
        actualizarKPIs();             // Carga valores de KPIs
        cargarOperacionesRecientes(); // Llena la tabla con las operaciones actuales
        configurarEventos();          // Asocia eventos a botones y menús
    }

    /**
     * Configura la TableView de operaciones: las columnas y la columna de acciones con botón "Ver".
     */
    private void configurarTabla() {
        // Columna "Nombre"
        colNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );

        // Columna "Estado"
        colEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado().name())
        );

        // Columna "Fecha creación": formateada a "dd/MM/yyyy HH:mm"
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        colFechaCreacion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaCreacion().format(fmt))
        );

        // Columna "Acciones": botón "Ver" que abre gestión de esa operación
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnVer = new Button("Ver");

            {
                btnVer.setOnAction(evt -> {
                    Operacion op = getTableView().getItems().get(getIndex());
                    if (op != null) {
                        abrirGestion(op);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnVer);
                }
            }
        });
    }

    /**
     * Calcula y actualiza los KPIs en el panel superior del Dashboard:
     * - Total de Operaciones
     * - Tareas Pendientes
     * - Tareas Completadas Hoy (en este ejemplo, se deja en 0).
     */


    private void actualizarKPIs() {
        List<Operacion> todas = gestor.obtenerOperacionesAgrupadasPorEstado();
        lblTotalOperaciones.setText(String.valueOf(todas.size()));

        int pendientes = 0;
        int completadasHoy = 0;
        for (Operacion op : todas) {
            for (Tarea t : op.getTareas()) {
                System.out.println("→ Tarea “" + t.getTitulo() + "” tiene estado: " + t.getEstado());
                if (t.getEstado() == EnumEstadoTarea.PENDIENTE) {
                    pendientes++;
                } else if (t.getEstado() == EnumEstadoTarea.COMPLETADA) {
                    completadasHoy++;
                }
            }
        }
        lblTareasPendientes.setText(String.valueOf(pendientes));
        lblTareasCompletadasHoy.setText(String.valueOf(completadasHoy));
    }


    /**
     * Carga las operaciones actuales en la TableView.
     */
    private void cargarOperacionesRecientes() {
        List<Operacion> recientes = gestor.obtenerOperacionesAgrupadasPorEstado();
        operacionesData.setAll(recientes);
        tableOperaciones.setItems(operacionesData);
    }

    /**
     * Asocia los eventos a botones y menús:
     * - btnIrGestion: abre vista de gestión
     * - btnNuevaOperacion: muestra formulario para crear nueva operación
     * - menuItemSalir: sale de la aplicación
     * - menuItemAcerca: abre ventana "Acerca de"
     */
    private void configurarEventos() {
        // Botón "Ir a Gestión"
        btnIrGestion.setOnAction(this::accionIrGestion);

        // Botón "+ Operación": abre el diálogo para crear una nueva operación
        btnNuevaOperacion.setOnAction(e -> abrirFormularioOperacion());

        // Menús de "Salir" y "Acerca de"
        menuItemSalir.setOnAction(e -> System.exit(0));
        menuItemAcerca.setOnAction(e -> abrirAcercaDe());
    }

    /**
     * Manejador para el botón "Ir a Gestión" (desde Dashboard). Abre management.fxml
     */
    private void accionIrGestion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/mx/edu/uacm/is/slt/ds/dst/vistas/management.fxml"
                    )
            );
            Scene escena = new Scene(loader.load());
            Stage ventana = new Stage();
            ventana.setTitle("DrunkTasker – Gestión de Operaciones y Tareas");
            ventana.setScene(escena);
            ventana.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblStatus.setText("Error al abrir Gestión");
        }
    }

    /**
     * Abre el formulario para crear una nueva operación (formulario-operacion.fxml).
     * Al cerrarlo, recarga la tabla y los KPIs.
     */
    private void abrirFormularioOperacion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/mx/edu/uacm/is/slt/ds/dst/vistas/formulario-operacion.fxml"
                    )
            );
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(loader.load());

            // Configuramos el controlador del diálogo para que use el mismo Gestor
            EditorOperacionController ctrl = loader.getController();
            ctrl.setGestor(gestor);

            // Mostramos el diálogo y, al cerrarlo, volvemos a cargar KPIs y tabla
            dialog.showAndWait();
            actualizarKPIs();
            cargarOperacionesRecientes();
            lblStatus.setText("Operación creada con éxito.");
        } catch (Exception ex) {
            ex.printStackTrace();
            lblStatus.setText("Error al crear Operación");
        }
    }

    /**
     * Abre la vista de gestión de una operación concreta (management.fxml),
     * pasando la operación seleccionada al ManagementController.
     *
     * @param op Operación a gestionar.
     */
    private void abrirGestion(Operacion op) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/mx/edu/uacm/is/slt/ds/dst/vistas/management.fxml")
            );
            Scene escena = new Scene(loader.load());
            Stage ventana = new Stage();
            ventana.setTitle("Gestión de Operación: " + op.getNombre());
            ventana.setScene(escena);

            // Pasamos la Operación al controller de Gestión:
            ManagementController ctrl = loader.getController();
            ctrl.setOperacionSeleccionada(op);

            // En vez de show(), usamos showAndWait(), para que espere a que cierres Gestión:
            ventana.showAndWait();

            // Una vez que el usuario haya cerrado la ventana de Gestión, volvemos a recargar:
            actualizarKPIs();
            cargarOperacionesRecientes();
        } catch (Exception ex) {
            ex.printStackTrace();
            lblStatus.setText("Error al abrir Gestión");
        }
    }


    /**
     * Abre la ventana "Acerca de DrunkTasker".
     */
    private void abrirAcercaDe() {
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
            lblStatus.setText("Error al abrir Acerca de.");
        }
    }
}
