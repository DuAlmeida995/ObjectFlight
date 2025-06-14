package Mecanicas.background;

import java.awt.Color;
import java.util.Random;
import Jogo.GameLib;

public class BackgroundEstrela {
    private final double[] bg1X, bg1Y, bg2X, bg2Y;
    private final double bg1Speed = 0.070, bg2Speed = 0.045;
    private double bg1Count = 0, bg2Count = 0;

    public BackgroundEstrela() {
        int qtd1 = 20, qtd2 = 50;
        bg1X = new double[qtd1]; bg1Y = new double[qtd1];
        bg2X = new double[qtd2]; bg2Y = new double[qtd2];
        Random r = new Random();
        for (int i = 0; i < qtd1; i++) {
            bg1X[i] = r.nextDouble() * GameLib.WIDTH;
            bg1Y[i] = r.nextDouble() * GameLib.HEIGHT;
        }
        for (int i = 0; i < qtd2; i++) {
            bg2X[i] = r.nextDouble() * GameLib.WIDTH;
            bg2Y[i] = r.nextDouble() * GameLib.HEIGHT;
        }
    }

    public void update(long delta) {
        bg2Count += bg2Speed * delta;
        bg1Count += bg1Speed * delta;
    }

    public void drawBackground() {
        GameLib.setColor(Color.DARK_GRAY);
        for (int i = 0; i < bg2X.length; i++) {
            double y = (bg2Y[i] + bg2Count) % GameLib.HEIGHT;
            GameLib.fillRect(bg2X[i], y, 2, 2);
        }
    }

    public void drawForeground() {
        GameLib.setColor(Color.GRAY);
        for (int i = 0; i < bg1X.length; i++) {
            double y = (bg1Y[i] + bg1Count) % GameLib.HEIGHT;
            GameLib.fillRect(bg1X[i], y, 3, 3);
        }
    }
}
