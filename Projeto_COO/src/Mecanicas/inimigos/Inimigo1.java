package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.bases.EntidadeInimigoBase;
import Mecanicas.interfaces.Colidivel;
import Mecanicas.projetil.ProjetilPool;

import static Mecanicas.constantes.Estados.*;

public class Inimigo1{
    
    EntidadeInimigoBase entIni_base;
    ProjetilPool pool;

    public Inimigo1(double x, double y, double v, double angulo, double raio, double vr, ProjetilPool pool) {
        entIni_base = new EntidadeInimigoBase(x, y, v, angulo, raio, vr);
        this.pool = pool;

    }


    public void move(long delta) {
        entIni_base.setX(entIni_base.getX() + Math.cos(entIni_base.getAngulo()) * entIni_base.getV() * delta);
        entIni_base.setY(entIni_base.getY() + Math.sin(entIni_base.getAngulo()) * entIni_base.getV() * delta * (-1));
        // calcule o deslocamento "dx" e "dy" a partir do módulo e do ângulo
        double dx = Math.cos(entIni_base.getAngulo()) * entIni_base.getV() * delta;
        double dy = Math.sin(entIni_base.getAngulo()) * entIni_base.getY() * delta;
        // se o eixo Y do seu sistema cresce para baixo, inverta o sinal:
        // dy = -dy;
        entIni_base.setX(entIni_base.getX() + dx);
        entIni_base.setY(entIni_base.getY() + dy);
        entIni_base.setAngulo(entIni_base.getAngulo() + entIni_base.getVR() * delta);
    }

    public void update(long delta) {
        if (entIni_base.getEstado() == EXPLODING) {
            if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                entIni_base.setEstado(INACTIVATE);
            }
            return;
        }

        // Movimento baseado em ângulo e velocidade vy
        move(delta);

        // Se desejar, aqui você pode checar saída de tela:
        if (entIni_base.getY() > GameLib.HEIGHT + entIni_base.getRaio()) {
            entIni_base.setEstado(INACTIVATE);
        }
    }

    public void draw() {
        if (entIni_base.getEstado() == EXPLODING) {
            double progress = (System.currentTimeMillis() - entIni_base.getexplosaoComeco())
                    / (double)(entIni_base.getexplosaoFim() - entIni_base.getexplosaoComeco());
            GameLib.drawExplosion(entIni_base.getX(), entIni_base.getY(), progress);
        } else {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(entIni_base.getX(), entIni_base.getY(), (float)entIni_base.getRaio());
        }
    }

    public boolean estaAtivo(){ return entIni_base.estaAtivo();}

    public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

    public void emColisao(Colidivel outro){ entIni_base.colideCom(outro);}



}

