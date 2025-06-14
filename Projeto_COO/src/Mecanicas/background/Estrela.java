package Mecanicas.background;

import java.awt.Color;

import Jogo.GameLib;

public class Estrela {
    private double x, y;
    private double velocidadeY;

    public Estrela(double x, double y, double velocidadeY) {
        this.x = x;
        this.y = y;
        this.velocidadeY = velocidadeY;
    }

    public void update(long delta) {
        y += velocidadeY * delta;
        if (y > 600) y = 0; // reseta verticalmente
    }

    public void draw() {
        GameLib.setColor(Color.WHITE);
        GameLib.fillRect((int) x, (int) y, 2, 2); // estrela pequena
    }
}