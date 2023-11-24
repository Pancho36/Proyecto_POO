package com.example.proyecto_poo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class FalloutTDApplication extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Fallout Tower Defense");
        gameSettings.setVersion("0.0");
        gameSettings.setWidth(800); gameSettings.setHeight(600);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
