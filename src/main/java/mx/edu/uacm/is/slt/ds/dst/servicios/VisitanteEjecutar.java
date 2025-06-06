package mx.edu.uacm.is.slt.ds.dst.servicios;

import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;

import java.util.Timer;
import java.util.TimerTask;

public class VisitanteEjecutar implements Visitante {
    @Override
    public void visitarOpreacion(Operacion operacion) {
        operacion.setEstado("EJECUCION");
        for (Tarea tarea : operacion.getTareas()) {
            visitarTarea(tarea);
        }
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                operacion.setEstado("FINALIZADA");
            }
        }, 3000L * operacion.getTareas().size());
    }

    @Override
    public void visitarTarea(Tarea tarea) {
        tarea.setEstado("EJECUCION");
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                tarea.setEstado("FINALIZADA");
            }
        }, 3000);
    }
}
