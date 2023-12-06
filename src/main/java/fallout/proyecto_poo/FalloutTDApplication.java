package fallout.proyecto_poo;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import fallout.proyecto_poo.Exceptions.InvalidPositionException;
import fallout.proyecto_poo.Exceptions.JsonNotFoundException;
import fallout.proyecto_poo.data.*;
import fallout.proyecto_poo.ui.HP;
import fallout.proyecto_poo.ui.StartRoundTimer;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FalloutTDApplication extends GameApplication implements Config {

    /* TODO:
     * 3. torreta no se pueda colocar en el camino
     * 4. torreta no se pueda colocar en otra torreta
     * 5. torreta no se pueda colocar en el spawn
     * 6. torreta no se pueda colocar en el final
     * 11. Limitar cantidad de torretas
     * 13. Oro
     * 14. Nivel de dificultad
     */
    private List<WaveData> wavesData;
    private StartRoundTimer startRoundTimer;
    private List<Entity> route;
    private boolean invalidPosition = false;
    private Point2D mousePosition ;
    private  List<Entity> turretDataList;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Fallout Tower Defense");
        gameSettings.setVersion("0.4");
        gameSettings.setWidth(30 * 32);
        gameSettings.setHeight(20 * 32);
        gameSettings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("numOfEnemies",0 );
        vars.put("currentWave", 0);
        vars.put("playerHp", PLAYER_HP);
        vars.put("numOfWaves", 0);
        vars.put("startRoundTimer", TIME_TO_START);
    }

    @Override
    protected void initGame() {
        initMapAndFactory();
        initVarListeners();
        initWaveList();
        startRoundTimer = new StartRoundTimer();
        mousePosition = FXGL.getInput().getMousePositionWorld();
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


        FXGL.run(()-> inc("startRoundTimer",-1),Duration.seconds(1),TIME_TO_START);

        FXGL.getGameTimer().runAtInterval(() -> spawnEnemies(wavesData.get(geti("currentWave"))), Duration.seconds(TIME_TO_START), wavesData.get(geti("currentWave")).waves()
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
    protected void initUI() {
        FXGL.addUINode(new HP(),5,5);
        FXGL.addUINode(startRoundTimer,380,280);
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
        if(geti("startRoundTimer") == 0){
            startRoundTimer.setVisible(false);
        }
    }

    private static void spawnEnemies(WaveData waveData) {
        EnemyData enemyData = getJsonData("scorpion");

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

    public static EnemyData getJsonData(String jsonName) {
        EnemyData enemyData;
        try {
            if(FXGL.getAssetLoader().loadJSON("json/enemy/"+jsonName+".json", EnemyData.class).isPresent()){
                enemyData =  FXGL.getAssetLoader().loadJSON("json/enemy/scorpion.json", EnemyData.class).get();
                return enemyData;
            }else {
                throw new JsonNotFoundException("Json not found");
            }
        } catch (JsonNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void initInput() {
        FXGL.onBtnDown(MouseButton.PRIMARY,()->{
            CheckValidPositon();
            try {
                if(!invalidPosition){
                    FXGL.spawn ("SimpleTurret",
                            new SpawnData(mousePosition.subtract(new Point2D(50,50)))
                                    .put("turretData",
                                            FXGL.getAssetLoader()
                                                    .loadJSON("json/turrets/simpleTurret.json",TurretData.class).get()));

                }else{
                    throw new InvalidPositionException("Invalid Position");
                }
            }catch (InvalidPositionException e){
                System.out.println(e.getMessage());
            }

        });
    }

    private void CheckValidPositon() {
        // TODO: Cannot place turret on route
        mousePosition = FXGL.getInput().getMousePositionWorld();
        turretDataList  = getGameWorld().getEntitiesByType(EntityType.TURRET).stream().toList();
        for (Entity turretData : turretDataList){
            invalidPosition = this.mousePosition.distance(turretData.getPosition()) < 130;
        }
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
