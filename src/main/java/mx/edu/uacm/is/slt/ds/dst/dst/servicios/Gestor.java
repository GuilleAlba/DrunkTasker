package mx.edu.uacm.is.slt.ds.dst.dst.servicios;

import mx.edu.uacm.is.slt.ds.dst.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.dst.modelos.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Gestor {

    private List<Operacion> operaciones;

    public Gestor() {
        this.operaciones = new ArrayList<>();
        Operacion operacion1 = new Operacion();
        operacion1.setNombre("Levantamiento de Requisitos");
        operacion1.setEstado("Creada");
        Tarea tarea1 = new Tarea();
        tarea1.setNombre("Entrevista con el cliente");
        tarea1.setEstado("Creada");
        Tarea tarea2 = new Tarea();
        tarea2.setNombre("Documentar requisitos");
        tarea2.setEstado("Creada");
        Operacion operacion2 = new Operacion();
        operacion2.setNombre("Analisis y modelado");
        operacion2.setEstado("Creada");
        Tarea tarea21 = new Tarea();
        tarea21.setNombre("Diagrama de casos de uso");
        tarea21.setEstado("Creada");
        Tarea tarea22 = new Tarea();
        tarea22.setNombre("Plantillas de casos de uso");
        tarea22.setEstado("Creada");
        operacion1.getTareas().add(tarea1);
        operacion1.getTareas().add(tarea2);
        operacion2.getTareas().add(tarea21);
        operacion2.getTareas().add(tarea22);
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<Operacion> operaciones) {
        this.operaciones = operaciones;
    }
}
