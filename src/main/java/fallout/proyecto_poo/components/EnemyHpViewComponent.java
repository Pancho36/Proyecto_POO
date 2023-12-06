package fallout.proyecto_poo.components;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.paint.Color;

public class EnemyHpViewComponent extends ChildViewComponent {
    private HealthIntComponent hp;
    private final ProgressBar hpBar;

    public EnemyHpViewComponent() {
        super(0, 64, false);

        hpBar = new ProgressBar(false);
        hpBar.setWidth(60);
        hpBar.setHeight(10);
        hpBar.setFill(Color.DARKRED);
        hpBar.setLabelVisible(false);

        getViewRoot().getChildren().add(hpBar);
    }

    @Override
    public void onAdded() {
        super.onAdded();

        hpBar.maxValueProperty().bind(hp.maxValueProperty());
        hpBar.currentValueProperty().bind(hp.valueProperty());
    }
}
