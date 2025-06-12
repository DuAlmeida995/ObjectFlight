package Mecanicas

import java.awt.Graphics2D;

public class Projetil extends Entidade {
    public Projetil(double x, double y, double vx, double vy, double raio) {
        super(x, y, vx, vy, raio);
    }

    @Override
    public void mover(long deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
    }

    public boolean estaForaDaTela() {
        return (x < 0 || x > GameLib.WIDTH || y < 0 || y > GameLib.HEIGHT);
    }

    public void desenhar(Graphics2D g) {
        GameLib.drawCircle(x, y, raio);
    }
}