package Mecanicas;

import java.awt.Graphics2D;


public class Projetil extends Entidade {
    public Projetil(int x, int y, double vx, double vy, double raio) {
        super(x, y, vx, vy, raio);
    }

    @Override
    public void move(long delta) {
        x += vx * delta;
        y += vy * delta;
    }
    @Override
    public void update(long delta){
        move(delta);
    }
    public boolean estaForaDaTela() {
        return (x < 0 || x > GameLib.WIDTH || y < 0 || y > GameLib.HEIGHT);
    }

    public void draw(Graphics2D g) {
        GameLib.drawCircle(x, y, raio);
    }
}