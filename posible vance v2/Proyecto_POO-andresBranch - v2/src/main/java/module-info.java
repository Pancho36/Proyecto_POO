module fallout.proyecto_poo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    opens assets.levels;

    opens fallout.proyecto_poo to javafx.fxml;
    exports fallout.proyecto_poo;
    exports enemigos;
    opens enemigos to javafx.fxml;
    exports tienda;
    opens tienda to javafx.fxml;
    exports edificios;
    opens edificios to javafx.fxml;
    exports entidades;
    opens entidades to javafx.fxml;
}