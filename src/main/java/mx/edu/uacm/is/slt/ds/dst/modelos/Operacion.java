package mx.edu.uacm.is.slt.ds.dst.modelos;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import mx.edu.uacm.is.slt.ds.dst.servicios.*;

import java.io.IOException;
import java.util.ArrayList;

public class Operacion implements Componente{

    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty descripcion;
    private StringProperty estado;
    private final ObjectProperty ver;
    private final ObjectProperty play;
    private final ObjectProperty pausaB;
    private final ObjectProperty detener;
    private final ObjectProperty reanudar;
    private ObservableList<Tarea> tareas;
    private final Gestor gestor = Gestor.getInstance();

    public Operacion(Integer id) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");;
        this.estado = new SimpleStringProperty("");;
        Button ver = new Button("Ver");
        ver.setOnAction(event -> {
            try {
                gestor.navegaConOp("detalles-operacion.fxml","Detalles de Operacion",getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.ver = new SimpleObjectProperty(ver);
        Button playB = new Button("Play");
        playB.setOnAction(event -> {
            Visitante vis = new VisitanteEjecutar();
            aceptar(vis);
        });
        this.play = new SimpleObjectProperty(playB);
        Button pausaB1 = new Button("Pausa");
        pausaB1.setOnAction(event -> {
            Visitante vis = new VistantePausar();
            aceptar(vis);
        });
        this.pausaB = new SimpleObjectProperty(pausaB1);
        Button stop = new Button("Detener");
        stop.setOnAction(event -> {
            Visitante vis = new VisitanteDetener();
            aceptar(vis);
        });
        this.detener = new SimpleObjectProperty(stop);
        Button reanu = new Button("Reanudar");
        pausaB1.setOnAction(event -> {
            Visitante vis = new VisitanteReanudar();
            aceptar(vis);
        });
        this.reanudar = new SimpleObjectProperty(reanu);
        this.tareas = FXCollections.observableArrayList(new ArrayList<>());
    }

    public Operacion(Integer id, String nombre, String descripcion, String estado) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.estado = new SimpleStringProperty(estado);
        this.tareas = FXCollections.observableArrayList(new ArrayList<>());
        Button ver = new Button("Ver");
        ver.setOnAction(event -> {
            try {
                gestor.navegaConOp("detalles-operacion.fxml","Detalles de Operacion",getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.ver = new SimpleObjectProperty(ver);
        Button playB = new Button("Play");
        playB.setOnAction(event -> {
            Visitante vis = new VisitanteEjecutar();
            aceptar(vis);
        });
        this.play = new SimpleObjectProperty(playB);
        Button pausaB1 = new Button("Pausa");
        pausaB1.setOnAction(event -> {
            Visitante vis = new VistantePausar();
            aceptar(vis);
        });
        this.pausaB = new SimpleObjectProperty(pausaB1);
        Button stop = new Button("Detener");
        stop.setOnAction(event -> {
            Visitante vis = new VisitanteDetener();
            aceptar(vis);
        });
        this.detener = new SimpleObjectProperty(stop);
        Button reanu = new Button("Reanudar");
        pausaB1.setOnAction(event -> {
            Visitante vis = new VisitanteReanudar();
            aceptar(vis);
        });
        this.reanudar = new SimpleObjectProperty(reanu);
    }

    @Override
    public void aceptar(Visitante visitante) {
        visitante.visitarOpreacion(this);
    }

    public Object getVer() {
        return ver.get();
    }

    public ObjectProperty verProperty() {
        return ver;
    }

    public Integer getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public void nuevoEstado(StringProperty estado) {
        this.estado = estado;
    }

    public ObservableList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ObservableList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Object getPlay() {
        return play.get();
    }

    public ObjectProperty playProperty() {
        return play;
    }

    public Object getPausaB() {
        return pausaB.get();
    }

    public ObjectProperty pausaBProperty() {
        return pausaB;
    }

    public Object getDetener() {
        return detener.get();
    }

    public ObjectProperty detenerProperty() {
        return detener;
    }

    public Object getReanudar() {
        return reanudar.get();
    }

    public ObjectProperty reanudarProperty() {
        return reanudar;
    }
}
