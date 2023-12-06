package fallout.proyecto_poo.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.paint.Color;

public class StartRoundTimer extends Icon {
    public StartRoundTimer() {
        super(170,60);
        var text = FXGL.getUIFactoryService().newText("", Color.WHITE, 20.0);
        text.setText("Round starts in");
        text.setTranslateX(10);
        text.setTranslateY(23);

        var time = FXGL.getUIFactoryService().newText("", Color.WHITE, 27.0);
        time.textProperty().bind(FXGL.getip("startRoundTimer").asString());
        time.setTranslateX(73);
        time.setTranslateY(53);

        getChildren().addAll(time,text);
    }
}
