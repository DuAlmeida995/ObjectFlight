package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.bases.DisparadorBase;
import Mecanicas.bases.EntidadeInimigoBase;
import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.*;

import static Mecanicas.constantes.Estados.*;

/* class Inimigo1
 * Classe que implementa a entidade de inimigo tipo 1 no jogo.
*/

public class Inimigo1 implements EntidadeInimigo, Colidivel{
    
    EntidadeInimigoBase entIni_base; /* Objeto que administra as propriedades de 'Entidade Inimigo' */

    public Inimigo1(double x, double y, double v, double angulo, double raio, double vr, long tempoAtual) {
        entIni_base = new EntidadeInimigoBase(x, y, v, angulo, raio, vr, tempoAtual);
    }
    
    /* Funções getters de posição, raio e estado.*/

    /* posição */
    public double getX(){ return entIni_base.getX();}
    public double getY(){ return entIni_base.getY();}
    
    /* raio */
    public double getRaio() { return entIni_base.getRaio();}

    /* estado */
    public Estados getEstado(){ return entIni_base.getEstado();}

    /* ------------------------------------------------------------- Mecânicas do Inimigo1 ------------------------------------------------------------- 
     *  
     * (1) Colisão;
     * (2) Atirar;
     * (3) Atualização e desenho;
     * 
    */

    
    /* (1) funções de execução da lógica de colisão e eventual explosão do Inimigo1. 

    /* calcula se uma entidade entra em colisão com outra. */    
    public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

    /* atualiza os atributos do Inimigo1 caso este exploda. */
    public void emExplosao(){entIni_base.emExplosao();}


    /* (2) função que faz com que o Inimigo1 atire um projétil, inserindo este na 'pool' de projéteis de inimigos do jogo. */

    public void disparar(DisparadorBase projeteisInimigos, long tempoAtual){
        if (tempoAtual > entIni_base.getProximoTiro() && entIni_base.getEstado() == ACTIVE) {
            projeteisInimigos.disparar(entIni_base.getX(), entIni_base.getY(), Math.cos(entIni_base.getAngulo()) * 0.45, Math.sin(entIni_base.getAngulo()) * 0.45 * (-1.0), 2.0);
            entIni_base.setProximoTiro(tempoAtual + 200 + (long)(Math.random() * 500));
        }
    }

    
    /* (3) funções de atualizações do Inimigo1 e desenho ao longo do tempo de jogo. */

    /* atualiza os atributos do Inimigo1 ao longo do tempo de jogo em duas condições:
    * (i) caso esse tenha explodido, torna-se inativo;
    * (ii) caso esse tenha ultrapassado os limites do jogo, torna-se inativo.
    * Caso nenhuma dessas condições tenha sido alcançadas, o inimigo é atualizado conforme sua lógica de movimento no jogo. */
    public void update(long delta) {
        /* condição (i) */
        if (entIni_base.getEstado() == EXPLODING) {
            if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                entIni_base.setEstado(INACTIVATE);
            }
            return;
        }
        /* condição (ii) */
        if (entIni_base.getY() > GameLib.HEIGHT + entIni_base.getRaio()) {
            entIni_base.setEstado(INACTIVATE);
            return;
        }
        /* movimenta somente no eixo vertical, descendo na tela */
        entIni_base.setX(entIni_base.getX() + Math.cos(entIni_base.getAngulo()) * entIni_base.getV() * delta);
        entIni_base.setY(entIni_base.getY() + Math.sin(entIni_base.getAngulo()) * entIni_base.getV() * delta * (-1.0));
        entIni_base.setAngulo(entIni_base.getAngulo() + entIni_base.getVR() * delta);
    }

    /* desenha o Inimigo1. */
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

}

