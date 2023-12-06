package fallout.proyecto_poo.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyDataTest {

    @Test
    void testEnemyData() {
        EnemyData enemyData = new EnemyData(100, 50, 2.0, 10, "image.png");
        assertEquals(100, enemyData.hp());
        assertEquals(50, enemyData.recompense());
        assertEquals(2.0, enemyData.moveSpeed());
        assertEquals(10, enemyData.delay());
        assertEquals("image.png", enemyData.imageRoute());
    }
}