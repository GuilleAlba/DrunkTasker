package mx.edu.uacm.is.slt.ds.dst.dst.modelos;

public class Tarea implements Componente{

    private String id;
    private String nombre;
    private String descripcion;
    private String estado;
    private String prioridad;

    public Tarea() {
    }

    @Override
    public void ejecutar() {

    }

    @Override
    public void pausar() {

    }

    @Override
    public void reanudar() {

    }

    @Override
    public void detener() {

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
