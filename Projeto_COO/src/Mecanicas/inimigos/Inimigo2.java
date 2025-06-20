package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.bases.EntidadeInimigoBase;
import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.EntidadeInimigo;

import static Mecanicas.constantes.Estados.*;

public class Inimigo2 implements EntidadeInimigo, Colidivel{

        private int contadorTiro;
        private double anteriorY;
        private double threshold = GameLib.HEIGHT * 0.30;
        EntidadeInimigoBase entIni_base;

        public Inimigo2(double x, double y, double v, double angulo, double raio, double vr, long tempoAtual) {
                entIni_base = new EntidadeInimigoBase(x, y, v, angulo, raio, vr, tempoAtual);
               
        }

        public double getX(){ return entIni_base.getX();}
        public double getY(){ return entIni_base.getY();}
        public double getRaio() { return entIni_base.getRaio();}
        public double getAngulo() { return entIni_base.getAngulo();}
        public long getProximoTiro() { return entIni_base.getProximoTiro();}
        public void setProximoTiro(long proximoTiro) { entIni_base.setProximoTiro(proximoTiro);}

        public Estados getEstado(){ return entIni_base.getEstado();}

        public void update(long deltaTime) {
              
                if (entIni_base.getEstado() == EXPLODING) {
                        if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                                entIni_base.setEstado(INACTIVATE);
                        }       
                        return;
                }

                if(entIni_base.getEstado() == ACTIVE){
                        if(entIni_base.getX() < - 10 || entIni_base.getX() > GameLib.WIDTH + 10){
                                entIni_base.setEstado(INACTIVATE);
                        }
                        anteriorY = entIni_base.getY();
                        entIni_base.setX(entIni_base.getX() + entIni_base.getV()*Math.cos(entIni_base.getAngulo()) * deltaTime);
                        entIni_base.setY(entIni_base.getY() + entIni_base.getV()*Math.sin(entIni_base.getAngulo()) * deltaTime * (-1.0));
                        entIni_base.setAngulo(entIni_base.getAngulo() + entIni_base.getVR() * deltaTime);

                        if(anteriorY < threshold && entIni_base.getY() >= threshold){
                                if(entIni_base.getX() < GameLib.WIDTH / 2) entIni_base.setVR(0.003);
				else entIni_base.setVR(-0.003);
                        }

                        if(entIni_base.getVR() > 0 && Math.abs(entIni_base.getAngulo() - 3 * Math.PI) < 0.05){
                                entIni_base.setVR(0.0);
                                entIni_base.setAngulo(3*Math.PI);
                        }

                        if(entIni_base.getVR() < 0 && Math.abs(entIni_base.getAngulo()) <  0.05){
                                entIni_base.setVR(0.0);
                                entIni_base.setAngulo(0.0);
                        }
                }

                // Lógica de tiro
                contadorTiro++;
                if (contadorTiro >= 100 ) {
                        //entIni_base.atirar(deltaTime);
                        contadorTiro = 0;
                }
        }

        public void draw() {
                if (entIni_base.getEstado() == EXPLODING) {
                        // Desenha a explosão
                        double alpha = (System.currentTimeMillis() - entIni_base.getexplosaoComeco()) / (double)(entIni_base.getexplosaoFim() - entIni_base.getexplosaoComeco());
                        GameLib.drawExplosion(entIni_base.getX(), entIni_base.getY(), alpha);
                } else {
                        // Desenha o inimigo diamante
                        GameLib.setColor(Color.MAGENTA);
                        GameLib.drawDiamond(entIni_base.getX(), entIni_base.getY(), entIni_base.getRaio());
                }
        }

        public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

        public void emColisao(){entIni_base.emColisao();}
        
        //public void atirar(long tempoAtual) { entIni_base.atirar(tempoAtual);}

}
