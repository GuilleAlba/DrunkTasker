package mx.edu.uacm.is.slt.ds.dst.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import mx.edu.uacm.is.slt.ds.dst.HelloApplication;
import mx.edu.uacm.is.slt.ds.dst.modelos.Operacion;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static mx.edu.uacm.is.slt.ds.dst.HelloApplication.primaryStage;

public class Gestor {

    private static Gestor instance;
    private ObservableList<Operacion> operaciones;
    private Operacion operacionSleccionada;
    private Tarea tereaSeleccionada;

    private Gestor(){}

    public static Gestor getInstance(){
        if (instance == null) {
            instance = new Gestor();
            instance.operaciones = FXCollections.observableArrayList(new ArrayList<>());
        }
        return instance;
    }

    public ObservableList<Operacion> getOperaciones() {
        return operaciones;
    }

    public void eliminarOperacion(Integer id) {
        operaciones.removeIf(op -> op.getId().equals(id));
    }

    public void agregarOperacion(Operacion nuevaOp) {
        operaciones.add(nuevaOp);
    }

    public void navega (String page, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        Scene scene = new Scene(fxmlLoader.load(), 620, 620);
        primaryStage.setTitle(titulo);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void navegaConOp (String page, String titulo, Integer id) throws IOException {
        Optional<Operacion> ope = operaciones.stream().findAny().filter(operacion -> operacion.getId().equals(id));
        setOperacionSleccionada(ope.get());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        Scene scene = new Scene(fxmlLoader.load(), 620, 620);
        primaryStage.setTitle(titulo);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void navegaConTa (String page, String titulo, Integer id) throws IOException {
        Optional<Tarea> tareaE = operacionSleccionada.getTareas().stream().filter(tarea -> id.equals(tarea.getId())).findFirst();
        setTereaSeleccionada(tareaE.orElseGet(() -> new Tarea(0, "No encontrado", "", "", "", "", false)));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        Scene scene = new Scene(fxmlLoader.load(), 620, 620);
        primaryStage.setTitle(titulo);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Operacion getOperacionSleccionada() {
        return operacionSleccionada;
    }

    public void setOperacionSleccionada(Operacion operacionSleccionada) {
        this.operacionSleccionada = operacionSleccionada;
    }

    public void agregaTarea(Tarea nueva) {
        operacionSleccionada.getTareas().add(nueva);
    }

    public Tarea getTereaSeleccionada() {
        return tereaSeleccionada;
    }

    public void setTereaSeleccionada(Tarea tereaSeleccionada) {
        this.tereaSeleccionada = tereaSeleccionada;
    }
}
