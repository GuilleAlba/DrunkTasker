package mx.edu.uacm.is.slt.ds.dst.modelos;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Representa una Tarea ligada a una Operación, con precondiciones, postcondiciones y comportamiento.
 */
public class Tarea {
    private final UUID id;              // Identificador único de la tarea
    private final UUID operacionId;     // ID de la operación a la que pertenece
    private String titulo;              // Título de la tarea
    private String descripcion;         // Descripción detallada de la tarea
    private LocalDate fechaLimite;      // Fecha límite para completar la tarea
    private EnumEstadoTarea estado;     // Estado actual de la tarea

    // Nuevos campos que solicitan mayor detalle
    private String precondiciones;
    private String postcondiciones;
    private String comportamiento;

    /**
     * Constructor. Genera un UUID para la tarea y establece estado inicial PENDIENTE.
     *
     * @param operacionId  ID de la operación madre.
     * @param titulo       Título de la tarea.
     * @param descripcion  Descripción de la tarea.
     * @param fechaLimite  Fecha límite para completar.
     */
    public Tarea(UUID operacionId, String titulo, String descripcion, LocalDate fechaLimite) {
        this.id = UUID.randomUUID();
        this.operacionId = operacionId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.estado = EnumEstadoTarea.PENDIENTE;
        this.precondiciones = "";
        this.postcondiciones = "";
        this.comportamiento = "";
    }

    // ------------------------------------------------------------
    // Getters y setters
    // ------------------------------------------------------------
    public UUID getId() {
        return id;
    }

    public UUID getOperacionId() {
        return operacionId;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }
    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public EnumEstadoTarea getEstado() {
        return estado;
    }
    public void setEstado(EnumEstadoTarea estado) {
        this.estado = estado;
    }

    public String getPrecondiciones() {
        return precondiciones;
    }
    public void setPrecondiciones(String precondiciones) {
        this.precondiciones = precondiciones;
    }

    public String getPostcondiciones() {
        return postcondiciones;
    }
    public void setPostcondiciones(String postcondiciones) {
        this.postcondiciones = postcondiciones;
    }

    public String getComportamiento() {
        return comportamiento;
    }
    public void setComportamiento(String comportamiento) {
        this.comportamiento = comportamiento;
    }
}
