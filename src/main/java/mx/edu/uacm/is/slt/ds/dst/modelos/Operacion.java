package mx.edu.uacm.is.slt.ds.dst.modelos;

import mx.edu.uacm.is.slt.ds.dst.servicios.Visitante;

import java.util.ArrayList;
import java.util.List;

public class Operacion implements Componente{

    private String id;
    private String nombre;
    private String descripcion;
    private String estado;
    private List<Tarea> tareas;

    public Operacion(String id, String nombre, String descripcion, String estado, List<Tarea> tareas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tareas = tareas;
    }

    public Operacion() {
        this.tareas = new ArrayList<>();
    }

    @Override
    public void aceptar(Visitante visitante) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}
