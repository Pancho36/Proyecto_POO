package fallout.proyecto_poo.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.component.Component;
import fallout.proyecto_poo.data.EnemyData;
import fallout.proyecto_poo.data.Way;
import javafx.geometry.Point2D;

import java.util.List;

public class EnemyComponent extends Component {
    private List<Point2D> wayPoints ;
    private EnemyData data;
    private Point2D nextWayPoint;

    public EnemyComponent(Way way, EnemyData data) {
        this.wayPoints = way.getWayPoints();
        this.data = data;
    }


    @Override
    public void onAdded() {
        nextWayPoint = wayPoints.remove(0);
        entity.setPosition(nextWayPoint);
    }

    @Override
    public void onUpdate(double tpf) {
        double speed = tpf * 60 * data.moveSpeed();

        Point2D velocity = nextWayPoint
                .subtract(entity.getPosition())
                .normalize()
                .multiply(speed);

        entity.translate(velocity);

        if( nextWayPoint.distance(entity.getPosition()) < speed){
            entity.setPosition(nextWayPoint);
            if(!wayPoints.isEmpty()){
                nextWayPoint = wayPoints.remove(0);
            }else {
                // TODO: Agregar cuando enemigo llegue al final
                entity.removeFromWorld();
            }
        }
    }
}
