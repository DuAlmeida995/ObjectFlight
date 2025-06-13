package Mecanicas;

import java.awt.Color;
import java.awt.Graphics2D;

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

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect((int) x, (int) y, 2, 2); // estrela pequena
    }
}