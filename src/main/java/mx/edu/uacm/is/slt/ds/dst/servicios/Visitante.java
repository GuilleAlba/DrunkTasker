package mx.edu.uacm.is.slt.ds.dst.servicios;

import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;

public interface Visitante {
    void visitarOpreacion(Operacion operacion);
    void visitarTarea(Tarea Tarea);
}
