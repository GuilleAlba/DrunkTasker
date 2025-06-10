module mx.edu.uacm.is.slt.ds.dst {
    requires javafx.controls;
    requires javafx.fxml;

    opens mx.edu.uacm.is.slt.ds.dst.controllers to javafx.fxml;
    opens mx.edu.uacm.is.slt.ds.dst.modelos to javafx.base;
    opens mx.edu.uacm.is.slt.ds.dst to javafx.graphics;
}
