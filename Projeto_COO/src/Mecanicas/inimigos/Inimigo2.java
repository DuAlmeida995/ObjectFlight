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


        /* Funções getters e setters de posição e proximo tiro e um getter para estado, raio, ângulo.*/

        /* posição */
         public double getX(){ return entIni_base.getX();}
        public double getY(){ return entIni_base.getY();}

        /* próximo tiro */
        public long getProximoTiro() { return entIni_base.getProximoTiro();}
        public void setProximoTiro(long proximoTiro) { entIni_base.setProximoTiro(proximoTiro);}
    
        /* raio */
        public double getRaio() { return entIni_base.getRaio();}

        /* ângulo */
        public double getAngulo() { return entIni_base.getAngulo();}

        /* estado */
        public Estados getEstado(){ return entIni_base.getEstado();}

        /* Função que calcula se uma entidade entra em colisão com outra. */
        public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

        /* Função que atualiza o estado do inimigo (ou projétil deste) quando este entra em contato com uma entidade colidível. */
        public void emColisao(){entIni_base.emColisao();}

        /* Função que atualiza os atributos do inimigo de tipo 2 ao longo do tempo de jogo em duas condições:
        * (i) caso esse tenha explodido, torna-se inativo;
        * (ii) caso esse tenha ultrapassado os limites do jogo.
           Caso nenhuma dessas condições tenha sido alcançadas, o inimigo é atualizado conforme sua lógica de movimento no jogo. */
        public void update(long deltaTime) {
              
                if (entIni_base.getEstado() == EXPLODING) {
                        if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                                entIni_base.setEstado(INACTIVATE);
                        }       
                        return;
                }

                if(entIni_base.getX() < - 10 || entIni_base.getX() > GameLib.WIDTH + 10){
                        entIni_base.setEstado(INACTIVATE);
                        return;
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
                

                // Lógica de tiro
                contadorTiro++;
                if (contadorTiro >= 100 ) {
                        //entIni_base.atirar(deltaTime);
                        contadorTiro = 0;
                }
        }

        /* Função para desenhar a entidade inimigo tipo 2. */
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


}
