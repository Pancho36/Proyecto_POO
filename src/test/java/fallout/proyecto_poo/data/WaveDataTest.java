package fallout.proyecto_poo.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaveDataTest {

    @Test
    void testWaveData() {
        WaveData waveData = new WaveData(1, 10, 5, "enemy.json");
        assertEquals(1, waveData.index());
        assertEquals(10, waveData.numOfEnemies());
        assertEquals(5, waveData.waves());
        assertEquals("enemy.json", waveData.enemyJsonRoute());
    }
    @Test
    void testValidEnemyJsonRoute() {
        assertDoesNotThrow(() -> {
            new WaveData(1, 10, 5, "enemy.json");
        });
    }
    @Test
    void testInvalidEnemyJsonRoute() {
        assertDoesNotThrow(() -> {
            new WaveData(1, 10, 5, "");
        });
    }
}