package fallout.proyecto_poo;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import fallout.proyecto_poo.data.EnemyData;
import fallout.proyecto_poo.data.TurretData;
import fallout.proyecto_poo.data.Way;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

public class FalloutTDApplication extends GameApplication {

    /* TODO:
     * 1. barra de vida
     * 2. timer de la partida
     * 3. torreta no se pueda colocar en el camino
     * 4. torreta no se pueda colocar en otra torreta
     * 5. torreta no se pueda colocar en el spawn
     * 6. torreta no se pueda colocar en el final
     * 7. Animaciones
     * 8. Instrucciones de juego
     * 9. Sonidos
     */
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Fallout Tower Defense");
        gameSettings.setVersion("0.0");
        gameSettings.setWidth(30*32); gameSettings.setHeight(20*32);
        gameSettings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGame() {

        FXGL.getGameWorld().addEntityFactory(new FalloutFactory());
        FXGL.setLevelFromMap("tmx/fallout.tmx");

        EnemyData enemyData = FXGL.getAssetLoader().loadJSON("json/enemy/enemy.json",EnemyData.class).get();
        var wayEntityInMap =  FXGL.getGameWorld().getSingleton(EntityType.WAY);
        for (int i = 0; i < 2; i++) {
            FXGL.run(()->{
                Polyline wayPoints = wayEntityInMap.getObject("polyline");
                Way individualWay = Way.fromPolyLine(wayPoints, wayEntityInMap.getX(), wayEntityInMap.getY());

                FXGL.spawnWithScale("Enemy", new SpawnData()
                                .put("way", individualWay)
                                .put("enemyData", enemyData)
                        ,Duration.seconds(0.45),
                        Interpolators.ELASTIC.EASE_OUT());
            },Duration.seconds(enemyData.delay()),10);
        }
    }

    @Override
    protected void initInput() {
        FXGL.onBtnDown(MouseButton.PRIMARY,()->{
            Point2D mousePosition = FXGL.getInput().getMousePositionWorld();
            FXGL.spawn ("SimpleTurret",
                    new SpawnData(mousePosition.subtract(new Point2D(40,40)))
                            .put("turretData",
                                    FXGL.getAssetLoader()
                                            .loadJSON("json/turrets/simpleTurret.json",TurretData.class).get()));

        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
