package fallout.proyecto_poo;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import fallout.proyecto_poo.data.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.geti;

public class FalloutTDApplication extends GameApplication {
    private List<WaveData> wavesData;
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
     * 10. Oleadas (Mezclas enemigos, agregar enemigos)
     * 11. Limitar cantidad de torretas
     * 12. Mejorar torretas (daño, velocidad de disparo, rango) con click derecho
     * 13. Oro
     * 14. Vida del jugador
     */

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Fallout Tower Defense");
        gameSettings.setVersion("0.0");
        gameSettings.setWidth(30 * 32);
        gameSettings.setHeight(20 * 32);
        gameSettings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("numOfEnemies", 0);
        vars.put("currentWave", 0);
        vars.put("playerHp", Config.PLAYER_HP);
        vars.put("numOfWaves", 0);
    }

    @Override
    protected void initGame() {
        initMapAndFactory();
        initVarListeners();
        initWaveList();

        loadCurrentLevel();
    }

    private void initVarListeners() {
        getWorldProperties().<Integer>addListener("numOfEnemies", (old, newValue) -> {
            if (newValue == 0) {
                onWaveEnd();
            }
        });
    }
    public void onEnemyKilled(Entity enemy) {
        inc("numOfEnemies", -1);
    }
    public void onEnemyReachedEnd(Entity enemy) {
        inc("playerHp", -1);
        inc("numOfEnemies", -1);
    }
    private void loadCurrentLevel() {
        FXGL.set("currentWave", 0);
        scheduleNextWave();
    }

    private void scheduleNextWave() {
        FXGL.set("numOfWaves", wavesData.get(geti("currentWave")).waves());
        FXGL.set("numOfEnemies",wavesData.get(geti("currentWave")).numOfEnemies());
        FXGL.getGameTimer().runAtInterval(() -> {
            spawnEnemies(wavesData.get(geti("currentWave")));
        }, Duration.seconds(5), wavesData.get(geti("currentWave")).waves()
        );
    }

    private void onWaveEnd() {
        if (geti("currentWave") < geti("numOfWaves")) {
            scheduleNextWave();
        } else {
            gameOver();
        }
    }

    @Override
    protected void onUpdate(double tpf) {
        // TODO: contar cuantos enemigos quedan pero con una variable unica "EnemiesKilled", cuando llegue a 0 que llame a una funcion que cree enemigos, pero que no sea la inicial
        if(geti("numOfEnemies") == 0){
            inc("currentWave", 1);
            scheduleNextWave();
        }
        if(geti("playerHp") == 0){
            playerKilled();
        }
    }

    private static void spawnEnemies(WaveData waveData) {
        EnemyData enemyData = FXGL.getAssetLoader().loadJSON(waveData.enemyJsonRoute(),EnemyData.class).get();
        var wayEntityInMap =  FXGL.getGameWorld().getSingleton(EntityType.WAY);
        FXGL.run(()->{
            Polyline wayPoints = wayEntityInMap.getObject("polyline");
            Way individualWay = Way.fromPolyLine(wayPoints, wayEntityInMap.getX(), wayEntityInMap.getY());

            FXGL.spawnWithScale("Enemy", new SpawnData()
                            .put("way", individualWay)
                            .put("enemyData", enemyData)
                    ,Duration.seconds(0.45),
                    Interpolators.ELASTIC.EASE_OUT());
        },Duration.seconds(enemyData.delay()),waveData.numOfEnemies());
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
    private void initWaveList() {
        List<String> wavesNames = List.of("wave1", "wave2", "wave3");
        wavesData = wavesNames.stream()
                .map(name -> {
                    return FXGL.getAssetLoader().loadJSON("json/waves/" + name + ".json", WaveData.class).get();
                }).toList();
    }
    private static void initMapAndFactory() {
        FXGL.getGameWorld().addEntityFactory(new FalloutFactory());
        FXGL.setLevelFromMap("tmx/fallout.tmx");
    }
    private void playerKilled() {
        showMessage("Defeat", getGameController()::gotoMainMenu);
    }
    private void gameOver() {
        showMessage("Level Complete", getGameController()::gotoMainMenu);
    }
    public static void main(String[] args) {
        launch(args);
    }

}
