module com.example.proyecto_poo {
    requires javafx.controls;
    requires javafx.fxml;
            
                                requires com.almasb.fxgl.all;
    
    opens com.example.proyecto_poo to javafx.fxml;
    exports com.example.proyecto_poo;
}