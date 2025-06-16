package Mecanicas.projetil;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.interfaces.Colidivel;
import Mecanicas.abstratas.Entidade;


public class Projetil extends Entidade {

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
        	GameLib.setColor(Color.GREEN);
			GameLib.drawLine(this.x, this.y - 5, this.x, this.y + 5);
			GameLib.drawLine(this.x - 1, this.y - 3, this.x - 1, this.y + 3);
			GameLib.drawLine(this.x + 1, this.y - 3, this.x + 1, this.y + 3);
    }

    public void emColisao(Colidivel outro) {
        if (estado == ACTIVE) {
            estado = EXPLODING;
            explosaoComeco = System.currentTimeMillis();
            explosaoFim = explosaoComeco + 2000;
        }
    }

}