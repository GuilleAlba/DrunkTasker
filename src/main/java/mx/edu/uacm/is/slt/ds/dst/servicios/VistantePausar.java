package mx.edu.uacm.is.slt.ds.dst.servicios;

import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;

public class VistantePausar implements Visitante {
    @Override
    public void visitarOpreacion(Operacion operacion) {
        for (Tarea tarea : operacion.getTareas()) {
            visitarTarea(tarea);
        }
        operacion.setEstado("PAUSADA");
    }

    @Override
    public void visitarTarea(Tarea tarea) {
        tarea.setEstado("PAUSADA");
    }
}
