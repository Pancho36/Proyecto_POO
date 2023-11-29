package fallout.proyecto_poo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;

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

    public static void main(String[] args) {
        launch(args);
    }

}
