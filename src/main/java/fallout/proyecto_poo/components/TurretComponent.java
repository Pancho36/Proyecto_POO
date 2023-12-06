package fallout.proyecto_poo.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import fallout.proyecto_poo.EntityType;
import fallout.proyecto_poo.data.TurretData;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.newLocalTimer;

public class TurretComponent extends Component {
    private LocalTimer shootDelay;
    private final TurretData data;

    public TurretComponent(TurretData data) {
        this.data = data;
    }
    public int getDamage(){
        return data.damage();
    }
    public String getExplosionRoute(){
        return data.explosionImageRoute();
    }
    @Override
    public void onAdded() {
        shootDelay = newLocalTimer();
        shootDelay.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        if(shootDelay.elapsed(Duration.seconds(0.2))){
            FXGL.getGameWorld()
                    .getClosestEntity(entity,e ->e.isType(EntityType.ENEMY))
                    .ifPresent(closestEnemy -> {
                        shoot(closestEnemy);
                        shootDelay.capture();
                    });
        }
    }
    private void shoot(Entity enemy){
        Point2D offSet = new Point2D(-20,-40);
        Point2D turretPosition = getEntity().getPosition().subtract(offSet);
        String imageProjectileRoute = Objects.requireNonNull(data.projectileImageRoute());

        var bullet = FXGL.spawn("Bullet",
                new SpawnData(turretPosition)
                .put("imageRoute",imageProjectileRoute)
                .put("turret",entity)
                .put("target",enemy)
        );
        bullet.rotateToVector(turretPosition);
    }
}
