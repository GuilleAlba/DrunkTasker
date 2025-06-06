package mx.edu.uacm.is.slt.ds.dst.modelos;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import mx.edu.uacm.is.slt.ds.dst.servicios.*;

import java.io.IOException;

public class Tarea implements Componente{

    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty descripcion;
    private final StringProperty estado;
    private final StringProperty prioridad;
    private final StringProperty idPrevia;
    private final BooleanProperty pausa;
    private final ObjectProperty ver;
    private final ObjectProperty play;
    private final ObjectProperty pausaB;
    private final ObjectProperty detener;
    private final ObjectProperty reanudar;
    private final Gestor gestor = Gestor.getInstance();

    public Tarea(Integer id, String nombre, String descripcion, String estado, String prioridad, String idPrevia, Boolean pausa) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.estado = new SimpleStringProperty(estado);
        this.prioridad = new SimpleStringProperty(prioridad);
        this.idPrevia = new SimpleStringProperty(idPrevia);
        this.pausa = new SimpleBooleanProperty(pausa);
        Button ver = new Button("Ver");
        ver.setOnAction(event -> {
            try {
                gestor.navegaConTa("detalles-tarea.fxml","Detalles de Tarea",getId());
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
        visitante.visitarTarea(this);
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

    public String getPrioridad() {
        return prioridad.get();
    }

    public StringProperty prioridadProperty() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad.set(prioridad);
    }

    public String getIdPrevia() {
        return idPrevia.get();
    }

    public StringProperty idPreviaProperty() {
        return idPrevia;
    }

    public void setIdPrevia(String idPrevia) {
        this.idPrevia.set(idPrevia);
    }

    public boolean isPausa() {
        return pausa.get();
    }

    public BooleanProperty pausaProperty() {
        return pausa;
    }

    public void setPausa(boolean pausa) {
        this.pausa.set(pausa);
    }

    public Object getVer() {
        return ver.get();
    }

    public ObjectProperty verProperty() {
        return ver;
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
