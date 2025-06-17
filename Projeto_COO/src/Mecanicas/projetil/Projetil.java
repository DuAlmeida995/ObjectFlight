package Mecanicas.projetil;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.bases.EntidadeBase;
import Mecanicas.bases.ExplosaoBase;
import Mecanicas.bases.MovimentoBase;
import Mecanicas.interfaces.Colidivel;

public class Projetil implements Colidivel{

    EntidadeBase ent_base;
    MovimentoBase mov_base;
    ExplosaoBase exp_base;

    public Projetil(double x, double y, double vx, double vy, double raio) {
        ent_base = new EntidadeBase(x,y, raio);
        mov_base = new MovimentoBase(vx, vy);

    }

    public double getX() { return ent_base.getX();}
    public double getY() { return ent_base.getY();}

    public double getRaio() { return ent_base.getRaio();}

    public void move(long delta) {
        ent_base.setX(ent_base.getX() + mov_base.getVX() * delta);
        ent_base.setY(ent_base.getY() + mov_base.getVY() * delta);
    }

    public void update(long delta){
        move(delta);
    }
    public boolean estaForaDaTela() {
        return (ent_base.getX() < 0 || ent_base.getX() > GameLib.WIDTH || 
        ent_base.getY() < 0 || ent_base.getY() > GameLib.HEIGHT);
    }

    public boolean estaAtivo(){ return ent_base.estaAtivo();}
    public boolean colideCom(Colidivel outro) { return ent_base.colideCom(outro);}

    public void draw() {
        	GameLib.setColor(Color.GREEN);
			GameLib.drawLine(ent_base.getX(), ent_base.getY() - 5, ent_base.getX(), ent_base.getY() + 5);
			GameLib.drawLine(ent_base.getX() - 1, ent_base.getY() - 3, ent_base.getX() - 1, ent_base.getY() + 3);
			GameLib.drawLine(ent_base.getX() + 1, ent_base.getY() - 3, ent_base.getX() + 1, ent_base.getY() + 3);
    }

}