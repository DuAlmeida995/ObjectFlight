package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.bases.EntidadeInimigoBase;
import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.*;

import static Mecanicas.constantes.Estados.*;

public class Inimigo1 implements EntidadeInimigo, Colidivel{
    
    EntidadeInimigoBase entIni_base;

    public Inimigo1(double x, double y, double v, double angulo, double raio, double vr, long tempoAtual) {
        entIni_base = new EntidadeInimigoBase(x, y, v, angulo, raio, vr, tempoAtual);
    }
    
    public double getX(){ return entIni_base.getX();}
    public double getY(){ return entIni_base.getY();}
    public double getRaio() { return entIni_base.getRaio();}
    public double getAngulo() { return entIni_base.getAngulo();}
    public long getProximoTiro() { return entIni_base.getProximoTiro();}
    public void setProximoTiro(long proximoTiro) { entIni_base.setProximoTiro(proximoTiro);}

    public Estados getEstado(){ return entIni_base.getEstado();}

    public void update(long delta) {
        if (entIni_base.getEstado() == EXPLODING) {
            if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                entIni_base.setEstado(INACTIVATE);
            }
            return;
        }
        
        entIni_base.setX(entIni_base.getX() + Math.cos(entIni_base.getAngulo()) * entIni_base.getV() * delta);
        entIni_base.setY(entIni_base.getY() + Math.sin(entIni_base.getAngulo()) * entIni_base.getV() * delta * (-1.0));
        entIni_base.setAngulo(entIni_base.getAngulo() + entIni_base.getVR() * delta);

        if (entIni_base.getY() > GameLib.HEIGHT + entIni_base.getRaio()) {
            entIni_base.setEstado(INACTIVATE);
        }
    }

    public void draw() {

        if (entIni_base.getEstado() == EXPLODING) {
            double alpha = (System.currentTimeMillis() - entIni_base.getexplosaoComeco()) / 
                        (double) (entIni_base.getexplosaoFim() - entIni_base.getexplosaoComeco());
            GameLib.drawExplosion(entIni_base.getX(), entIni_base.getY(), alpha);
        } else {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(entIni_base.getX(), entIni_base.getY(), (float)entIni_base.getRaio());
        }
    }

    public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

    public void emColisao(){entIni_base.emColisao();}



}

