package mx.edu.uacm.is.slt.ds.dst.servicios;

import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Gestor {

    private List<Operacion> operaciones;

    public Gestor(List<Operacion> operaciones) {
        this.operaciones = operaciones;
        List<Tarea> listaT= new ArrayList<Tarea>();
        Tarea tarea1 = new Tarea("sds","dsfdsfd","sdff","sdfsdf", "sdds");
        listaT.add(tarea1);
        this.operaciones.add(new Operacion("001","hfjdhf","dssfs","sddsfd",listaT));
    }

    public Gestor() {
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<Operacion> operaciones) {
        this.operaciones = operaciones;
    }
}
