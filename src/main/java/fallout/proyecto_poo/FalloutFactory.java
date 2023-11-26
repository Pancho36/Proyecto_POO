package fallout.proyecto_poo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;

public class FalloutFactory implements EntityFactory {


    @Spawns("enemyWay")
    public Entity enemyWay(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.WAY)
                .build();

    }

}
