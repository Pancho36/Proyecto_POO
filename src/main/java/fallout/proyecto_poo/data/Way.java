package fallout.proyecto_poo.data;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

public record Way(List<Point2D> wayPoints) {
    public static Way fromPolyLine(Polyline polyline, double offSetX, double offSetY) {

        var list = new ArrayList<Point2D>();
        for (int i = 0; i < polyline.getPoints().size(); i += 2) {
            var x = polyline.getPoints().get(i) + offSetX - 50;
            var y = polyline.getPoints().get(i + 1) + offSetY - 80;
            list.add(new Point2D(x, y));
        }
        return new Way(list);
    }

}
