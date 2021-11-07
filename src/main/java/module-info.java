module grupo1.hospital.app {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires org.json;
	requires javafx.base;

    opens grupo1.hospital.app to javafx.fxml, javafx.base;
    opens grupo1.hospital.app.classes to javafx.fxml, javafx.base;

    exports grupo1.hospital.app;
}
