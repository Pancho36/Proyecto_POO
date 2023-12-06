package fallout.proyecto_poo.data;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

public class Way {
    private List<Point2D> wayPoints ;

    public Way(List<Point2D> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public List<Point2D> getWayPoints() {
        return wayPoints;
    }
    public static Way fromPolyLine(Polyline polyline,double offSetX,double offSetY){

        var list = new ArrayList<Point2D>();
        for (int i = 0; i < polyline.getPoints().size(); i+= 2) {
            var x = polyline.getPoints().get(i)+offSetX-50;
            var y = polyline.getPoints().get(i+1)+offSetY-80;
            list.add(new Point2D(x,y));
        }
        return new Way(list);
    }

}
