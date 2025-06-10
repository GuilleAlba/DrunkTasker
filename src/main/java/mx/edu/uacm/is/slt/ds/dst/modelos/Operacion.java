package mx.edu.uacm.is.slt.ds.dst.modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa una Operación, que agrupa varias Tareas.
 */
public class Operacion {
    private final UUID id;                   // Identificador único de la operación
    private String nombre;                   // Nombre descriptivo de la operación
    private String descripcion;              // Descripción opcional de la operación
    private final LocalDateTime fechaCreacion; // Fecha y hora de creación de la operación
    private EnumEstadoOperacion estado;      // Estado actual de la operación
    private final List<Tarea> tareas = new ArrayList<>(); // Listado de tareas asociadas

    /**
     * Constructor por defecto. Genera un UUID y establece fecha de creación a ahora.
     * El estado inicial será NO_INICIADA.
     *
     * @param nombre      Nombre de la operación.
     * @param descripcion Descripción de la operación.
     */
    public Operacion(String nombre, String descripcion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EnumEstadoOperacion.NO_INICIADA;
    }

    // ------------------------------------------------------------
    // Getters y setters
    // ------------------------------------------------------------
    public UUID getId() {
        return id;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public EnumEstadoOperacion getEstado() {
        return estado;
    }
    public void setEstado(EnumEstadoOperacion estado) {
        this.estado = estado;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    // ------------------------------------------------------------
    // Métodos para manejar la lista de tareas
    // ------------------------------------------------------------

    /**
     * Agrega una tarea a esta Operación. Solo se agrega si el ID de la tarea coincide
     * con el ID de esta operación (prevención extra).
     *
     * @param t Tarea a agregar.
     */
    public void agregarTarea(Tarea t) {
        if (t != null && t.getOperacionId().equals(this.id)) {
            tareas.add(t);
        }
    }

    /**
     * Elimina una tarea de esta operación.
     *
     * @param t Tarea a eliminar.
     */
    public void eliminarTarea(Tarea t) {
        tareas.remove(t);
    }
}
