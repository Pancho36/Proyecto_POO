package fallout.proyecto_poo.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.paint.Color;

public class HP extends Icon {
    public HP() {
        super(100,60);
        var hpTexture = FXGL.texture("ui/heart.png").multiplyColor(Color.RED);
        hpTexture.setTranslateX(12);
        hpTexture.setTranslateY(15);
        hpTexture.setFitHeight(30);
        hpTexture.setFitWidth(30);

        var text = FXGL.getUIFactoryService().newText("", Color.WHITE, 32.0);
        text.textProperty().bind(FXGL.getip("playerHp").asString());
        text.setTranslateX(44);
        text.setTranslateY(43);

        getChildren().addAll(hpTexture, text);
    }
}
