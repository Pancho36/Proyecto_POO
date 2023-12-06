package fallout.proyecto_poo.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurretDataTest {
    @Test
    void testTurretData() {
        TurretData turretData = new TurretData("Turret",
                "image.png", "projectile.png",
                "explosion.png", 10);
        assertEquals("Turret", turretData.name());
        assertEquals("image.png", turretData.imageRoute());
        assertEquals("projectile.png", turretData.projectileImageRoute());
        assertEquals("explosion.png", turretData.explosionImageRoute());
        assertEquals(10, turretData.damage());
    }
    @Test
    void testInvalidImageRoute() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TurretData("Turret", "",
                    "projectile.png", "explosion.png", 10);
        });
    }
}