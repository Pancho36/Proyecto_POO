package fallout.proyecto_poo.ui;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Icon extends Parent {
    public Icon(double x, double y) {
        var bg = new Rectangle(x, y, Color.color(0.5, 0.5, 0.5, 0.75));
        bg.setStroke(Color.color(0, 0, 0, 0.9));
        bg.setStrokeWidth(2.0);
        bg.setArcWidth(10);
        bg.setArcHeight(10);

        getChildren().addAll(bg);
    }
}
