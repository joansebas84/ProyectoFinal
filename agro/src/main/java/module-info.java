module co.edu.poli.agro {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.poli.agro to javafx.fxml;
    opens co.edu.poli.agro.vista to javafx.fxml, javafx.graphics;
    opens co.edu.poli.agro.modelo to javafx.base;
    opens co.edu.poli.agro.servicios to javafx.base;
}