package fallout.proyecto_poo;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import data.Camino;
import data.EnemigoData;
import data.OleadaData;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;



import static com.almasb.fxgl.dsl.FXGL.*;
import static data.Vars.*;
import static fallout.proyecto_poo.TipoEntidad.CAMINO;

public class FalloutTDApplication extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Fallout Tower Defense");
        gameSettings.setVersion("0.0");
        gameSettings.setWidth(30*32); gameSettings.setHeight(20*32);
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new FalloutFactory());
        FXGL.setLevelFromMap("fallout.tmx");



    }









    private void spawnWave(OleadaData oleada) {
        var wayEntity = getGameWorld().getSingleton(e ->
                e.isType(CAMINO) && e.getString("name").equals(oleada.camino())
        );

        EnemigoData data = getAssetLoader().loadJSON("enemies/" + oleada.enemy(), EnemigoData.class).get();

        Polyline p = wayEntity.getObject("polyline");
        var way = Camino.fromPolyline(p, wayEntity.getX(), wayEntity.getY());

        for (int i = 0; i < oleada.cantidad(); i++) {
            runOnce(() -> {
                spawnWithScale(
                        "Enemy",
                        new SpawnData()
                                .put("way", way)
                                .put("enemyData", data),
                        Duration.seconds(0.45),
                        Interpolators.ELASTIC.EASE_OUT()
                );

            }, Duration.seconds(i * data.intervalo()));
        }

        inc(NUM_ENEMIGOS, oleada.cantidad());
    }


    public void onEnemyReachedEnd(Entity enemy) {
        inc(PLAYER_HP, -1);
        inc(NUM_ENEMIGOS, -1);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
