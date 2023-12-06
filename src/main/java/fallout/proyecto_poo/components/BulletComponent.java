package fallout.proyecto_poo.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import fallout.proyecto_poo.EntityType;
import fallout.proyecto_poo.FalloutTDApplication;
import javafx.geometry.Point2D;

public class BulletComponent extends Component {

    private final Entity turret;
    private final Entity target;

    public BulletComponent(Entity turret, Entity target) {
        this.turret = turret;
        this.target = target;
    }

    @Override
    public void onUpdate(double tpf) {
        if (target.isActive()) {
            entity.translateTowards(target.getPosition(),tpf*500);
            TurretComponent turretComponent = turret.getComponent(TurretComponent.class);
            var hp = target.getComponent(HealthIntComponent.class);
            FXGL.onCollision(EntityType.PROJECTILE,EntityType.ENEMY,(projectile,enemy)->{
                projectile.removeFromWorld();
                hp.damage(turretComponent.getDamage());
                showExplosion(enemy, turretComponent);
            });
            if(hp.isZero()){
                FXGL.<FalloutTDApplication>getAppCast().onEnemyKilled(target);
                target.removeFromWorld();
            }
        }else {
            entity.removeFromWorld();
        }
    }

    private static void showExplosion(Entity enemy, TurretComponent turretComponent) {
        Point2D offSet = new Point2D(60,90);
        FXGL.spawn("Explosion"
                ,new SpawnData(enemy.getPosition().subtract(offSet))
                        .put("imageRoute", turretComponent.getExplosionRoute())
                );
    }
}
