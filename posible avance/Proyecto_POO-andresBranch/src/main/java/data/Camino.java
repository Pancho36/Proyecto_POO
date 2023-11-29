package data;


import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;


public class Camino {

    private List<Point2D> waypoints;

    public Camino(List<Point2D> waypoints) {
        this.waypoints = waypoints;
    }

    public List<Point2D> getWaypoints() {
        return new ArrayList<>(waypoints);
    }

    public static Camino fromPolyline(Polyline polyline, double offsetX, double offsetY) {
        var list = new ArrayList<Point2D>();

        for (int i = 0; i < polyline.getPoints().size(); i += 2) {
            var x = polyline.getPoints().get(i) + offsetX;
            var y = polyline.getPoints().get(i+1) + offsetY;

            list.add(new Point2D(x, y));
        }

        return new Camino(list);
    }
}
