package fallout.proyecto_poo;

import com.almasb.fxgl.core.View;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.TimeComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import fallout.proyecto_poo.components.BulletComponent;
import fallout.proyecto_poo.components.EnemyComponent;
import fallout.proyecto_poo.components.EnemyHpViewComponent;
import fallout.proyecto_poo.components.TurretComponent;
import fallout.proyecto_poo.data.EnemyData;
import fallout.proyecto_poo.data.TurretData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class FalloutFactory implements EntityFactory {

    @Spawns("SimpleTurret")
    public Entity simpleTurret(SpawnData data){
        TurretData turretData = data.get("turretData");
        return FXGL.entityBuilder(data)
                .viewWithBBox(turretData.imageRoute())
                .collidable()
                .with(new TurretComponent(turretData))
                .type(EntityType.TURRET)
                .build();
    }
    @Spawns("Route")
    public Entity route(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityType.ROUTE)
                .collidable()
                .build();
    }
    @Spawns("Bullet")
    public Entity bullet(SpawnData data){
        String imageRoute = data.get("imageRoute");
        ImageView view = FXGL.texture(imageRoute);
        view.setRotate(90);

        Entity tower = data.get("turret");
        Entity target = data.get("target");

        return FXGL.entityBuilder(data)
                .type(EntityType.PROJECTILE)
                .viewWithBBox(view)
                .collidable()
                .with(new BulletComponent(tower,target))
                .build();
    }
    @Spawns("Enemy")
    public Entity newEnemy (SpawnData data) {
        EnemyData enemyData = data.get("enemyData");
        ImageView view = FXGL.texture(enemyData.imageRoute());
        view.setFitHeight(80);
        view.setFitWidth(80);

        return FXGL.entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(view)
                .collidable()
                .with(new HealthIntComponent(enemyData.hp()))
                .with(new EnemyHpViewComponent())
                .with( new AutoRotationComponent())
                .with(new EnemyComponent(data.get("way"),enemyData))
                .build();
    }
    @Spawns("WayPoint")
    public Entity newWaypoint(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.WAY)
                .build();
    }
    @Spawns("Explosion")
    public Entity newExplosion(SpawnData data){
        return entityBuilder(data)
                .view(data.get("imageRoute").toString())
                .with(new ExpireCleanComponent(Duration.seconds(0.1)))
                .build();
    }
}
