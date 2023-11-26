module fallout.proyecto_poo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    opens assets.levels;

    opens fallout.proyecto_poo to javafx.fxml;
    exports fallout.proyecto_poo;
}