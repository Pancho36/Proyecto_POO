package fallout.proyecto_poo;

import com.almasb.fxgl.app.GameSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FalloutTDApplicationTest {
    private FalloutTDApplication app;
    private GameSettings settings;

    @BeforeEach
    void setUp() {
        app = new FalloutTDApplication();
        settings = new GameSettings();
    }
    @Test
    void testPlayerKilled() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("playerHp", 0);
        app.initGameVars(vars);
    }

    @Test
    void testGameOver() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("currentWave", 10);
        vars.put("numOfWaves", 10);
        app.initGameVars(vars);
    }
    @Test
    void testInitSettings() {
        app.initSettings(settings);
        assertEquals("Fallout Tower Defense", settings.getTitle());
        assertEquals("0.4", settings.getVersion());
        assertEquals(30 * 32, settings.getWidth());
        assertEquals(20 * 32, settings.getHeight());
        assertTrue(settings.isMainMenuEnabled());
    }

    @Test
    void testInitGameVars() {
        Map<String, Object> vars = new HashMap<>();
        app.initGameVars(vars);
        assertEquals(0, vars.get("numOfEnemies"));
        assertEquals(0, vars.get("currentWave"));
        assertEquals(FalloutTDApplication.PLAYER_HP, vars.get("playerHp"));
        assertEquals(0, vars.get("numOfWaves"));
        assertEquals(FalloutTDApplication.TIME_TO_START, vars.get("startRoundTimer"));
    }

}