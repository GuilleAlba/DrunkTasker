package mx.edu.uacm.is.slt.ds.dst.modelos;

import mx.edu.uacm.is.slt.ds.dst.servicios.Visitante;

public class Tarea implements Componente{

    private String id;
    private String nombre;
    private String descripcion;
    private String estado;
    private String prioridad;

    public Tarea(String id, String nombre, String descripcion, String estado, String prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
    }

    public Tarea() {
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

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
