module com.os {
    // requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    opens com.os to javafx.fxml;

    exports com.os;
}
