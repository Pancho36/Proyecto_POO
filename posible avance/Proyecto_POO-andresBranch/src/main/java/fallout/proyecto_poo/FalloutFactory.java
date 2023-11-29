package fallout.proyecto_poo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.TimeComponent;
import enemigos.Enemigo;
import data.EnemigoData;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static fallout.proyecto_poo.TipoEntidad.ENEMIGO;

public class FalloutFactory implements EntityFactory {


    @Spawns("enemyWay")
    public Entity enemyWay(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(TipoEntidad.CAMINO)
                .build();

    }

    @Spawns("Enemy")
    public Entity spawnEnemy(SpawnData data) {
        EnemigoData enemyData = data.get("EnemigoData");

        return entityBuilder(data)
                .type(ENEMIGO)
                .viewWithBBox("enemies/" + enemyData.nombreImagen())
                .collidable()
                .with(new TimeComponent())
                .with(new EffectComponent())
                .with(new HealthIntComponent(enemyData.vida()))
                .with(new Enemigo(data.get("way"), enemyData) {
                })
                .with(new AutoRotationComponent())
                //.with(new EnemyHealthViewComponent())
                .build();
    }





}
