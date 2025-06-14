package Mecanicas.projetil;

import Jogo.GameLib;

import Mecanicas.interfaces.Entidade;


public class Projetil extends Entidade {

    protected double x, y, vx, vy, raio;

    public Projetil(double x, double y, double vx, double vy, double raio) {
        super(x, y, vx, vy, raio);
    }

    public void move(long delta) {
        x += vx * delta;
        y += vy * delta;
    }

    public void update(long delta){
        move(delta);
    }
    public boolean estaForaDaTela() {
        return (x < 0 || x > GameLib.WIDTH || y < 0 || y > GameLib.HEIGHT);
    }

    public void draw() {
        GameLib.drawCircle(x, y, raio);
    }
}