module mx.edu.uacm.is.slt.ds.dst.dst {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens mx.edu.uacm.is.slt.ds.dst.controladores to javafx.fxml;
    opens mx.edu.uacm.is.slt.ds.dst to javafx.fxml;
    exports mx.edu.uacm.is.slt.ds.dst;
    exports mx.edu.uacm.is.slt.ds.dst.controladores;
    exports mx.edu.uacm.is.slt.ds.dst.modelos;

    opens imagenes to javafx.graphics, javafx.fxml;
}