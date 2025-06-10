package mx.edu.uacm.is.slt.ds.dst.servicios;

import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.modelos.EnumEstadoTarea;

import java.time.LocalDate;
import java.util.*;

/**
 * Singleton que gestiona Operaciones y Tareas en memoria.
 */
public class Gestor {
    private static Gestor instancia;
    private final Map<UUID, Operacion> operaciones = new LinkedHashMap<>();

    // Constructor privado para evitar instanciación externa
    private Gestor() { }

    /**
     * Retorna la instancia única del Gestor.
     */
    public static synchronized Gestor getInstancia() {
        if (instancia == null) {
            instancia = new Gestor();
        }
        return instancia;
    }

    // ------------------------------------------------------------
    // Métodos para Operaciones
    // ------------------------------------------------------------

    /**
     * Crea una nueva Operación y la agrega al mapa interno.
     *
     * @param nombre      Nombre de la operación.
     * @param descripcion Descripción de la operación.
     * @return La Operación recién creada.
     */
    public Operacion crearOperacion(String nombre, String descripcion) {
        Operacion op = new Operacion(nombre, descripcion);
        operaciones.put(op.getId(), op);
        return op;
    }

    /**
     * Edita una Operación existente (solo nombre y descripción).
     *
     * @param id               ID de la operación a editar.
     * @param nuevoNombre      Nuevo nombre.
     * @param nuevaDescripcion Nueva descripción.
     */
    public void editarOperacion(UUID id, String nuevoNombre, String nuevaDescripcion) {
        Operacion op = operaciones.get(id);
        if (op != null) {
            op.setNombre(nuevoNombre);
            op.setDescripcion(nuevaDescripcion);
        }
    }

    /**
     * Elimina una Operación y todas sus Tareas asociadas.
     *
     * @param id ID de la operación a eliminar.
     */
    public void eliminarOperacion(UUID id) {
        operaciones.remove(id);
    }

    /**
     * Retorna todas las Operaciones en el orden actual (LinkedHashMap conserva inserción).
     *
     * @return Lista de operaciones.
     */
    public List<Operacion> obtenerOperacionesAgrupadasPorEstado() {
        return new ArrayList<>(operaciones.values());
    }

    // ------------------------------------------------------------
    // Métodos para Tareas
    // ------------------------------------------------------------

    /**
     * Crea una nueva Tarea vinculada a la Operación con ID dado.
     *
     * @param operacionId ID de la operación madre.
     * @param titulo      Título de la tarea.
     * @param descripcion Descripción de la tarea.
     * @param fechaLimite Fecha límite.
     * @return La Tarea creada o null si la operación no existe.
     */
    public Tarea crearTarea(UUID operacionId, String titulo, String descripcion, LocalDate fechaLimite) {
        Operacion op = operaciones.get(operacionId);
        if (op == null) {
            return null;
        }
        Tarea t = new Tarea(operacionId, titulo, descripcion, fechaLimite);
        op.agregarTarea(t);
        return t;
    }

    /**
     * Edita una Tarea existente (solo título, descripción, fecha límite, estado,
     * precondiciones, postcondiciones y comportamiento).
     *
     * @param operacionId         ID de la operación madre.
     * @param tareaId             ID de la tarea a editar.
     * @param nuevoTitulo         Nuevo título.
     * @param nuevaDescripcion    Nueva descripción.
     * @param nuevaFechaLimite    Nueva fecha límite.
     * @param nuevoEstado         Nuevo estado (EnumEstadoTarea).
     * @param nuevasPrecondiciones Nuevas precondiciones.
     * @param nuevasPostcondiciones Nuevas postcondiciones.
     * @param nuevoComportamiento Nuevo comportamiento.
     */
    public void editarTarea(
            UUID operacionId,
            UUID tareaId,
            String nuevoTitulo,
            String nuevaDescripcion,
            LocalDate nuevaFechaLimite,
            EnumEstadoTarea nuevoEstado,
            String nuevasPrecondiciones,
            String nuevasPostcondiciones,
            String nuevoComportamiento
    ) {
        Operacion op = operaciones.get(operacionId);
        if (op == null) return;
        for (Tarea t : op.getTareas()) {
            if (t.getId().equals(tareaId)) {
                t.setTitulo(nuevoTitulo);
                t.setDescripcion(nuevaDescripcion);
                t.setFechaLimite(nuevaFechaLimite);
                t.setEstado(nuevoEstado);             // ← aquí es donde cambia el estado
                t.setPrecondiciones(nuevasPrecondiciones);
                t.setPostcondiciones(nuevasPostcondiciones);
                t.setComportamiento(nuevoComportamiento);
                break;
            }
        }
    }

    /**
     * Elimina una Tarea de la Operación correspondiente.
     *
     * @param operacionId ID de la operación madre.
     * @param tareaId     ID de la tarea a eliminar.
     */
    public void eliminarTarea(UUID operacionId, UUID tareaId) {
        Operacion op = operaciones.get(operacionId);
        if (op == null) return;
        op.getTareas().removeIf(t -> t.getId().equals(tareaId));
    }
}
