package Mecanicas.chefes; // aaa

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.bases.DisparadorBase;
import Mecanicas.bases.EntidadeInimigoBase;
import Mecanicas.bases.VidaBase;

import Mecanicas.constantes.Estados;

import Mecanicas.interfaces.*;
import static Mecanicas.constantes.Estados.*;

/* class Chefe1
 * Classe que implementa a entidade de chefe de tipo 1 no jogo. O Chefe 1 realiza disparos enquanto se move de uma borda a outra.
*/

public class Chefe1 implements Chefe, Colidivel{

    private EntidadeInimigoBase entIni_base; /* Objeto que administra as propriedades de 'Entidade Inimigo' */
    private VidaBase vid_base;               /* Objeto para administrar as propriedades de 'Vida'           */

    private boolean descendo;               /* Variável que controla a lógica de movimento */
    
    private int aumentou ;                  /* Variável que controla a lógica de disparo  */
    private long proximoTiroEspecial;       /* Cadência do tiro especial  */

    public Chefe1(double x, double y, double v, double angulo, double raio, double vr, long tempoAtual, int vidaMaxima) {
        entIni_base  = new EntidadeInimigoBase(x, y, v, angulo, raio, vr, tempoAtual);
        vid_base     = new VidaBase(vidaMaxima);
        descendo     = true;
        aumentou     = 0;
        proximoTiroEspecial = 0;
    }
    
    /* Funções getters de posição, raio e estado. */

    /* posição */
    public double getX(){ return entIni_base.getX();}
    public double getY(){ return entIni_base.getY();}

    /* raio */
    public double getRaio() { return entIni_base.getRaio();}

    /* estado */
    public Estados getEstados(){ return entIni_base.getEstado();}

    /* ------------------------------------------------------------- Mecânicas do Chefe1 ------------------------------------------------------------- 
     * 
     * (1) Vida;
     * (2) Colisão;
     * (3) Atirar;
     * (4) Atualização e desenho;
     * 
    */

    
    /* (1) funções básicas de controle da vida do Chefe1. */

    public void reduzir() { vid_base.reduzir();}
    public boolean estaMorto() { return vid_base.estaMorto();}
    public boolean estaInvencivel() { return vid_base.estaInvencivel();}


    /* (2) funções de execução da lógica de colisão e eventual explosão do Chefe1. */

    /* calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

    /* atualiza os atributos do Chefe1 caso este exploda. */
    public void emExplosao(){ entIni_base.emExplosao();}


    /* (3) função que faz com que o Chefe1 atire um projétil ou projéteis (dado a vida atual), inserindo este(s) na 'pool' de projéteis de inimigos do jogo. */

    public void disparar(DisparadorBase projeteisInimgos, long tempoAtual){
        if(tempoAtual > entIni_base.getProximoTiro() && !descendo){
            /* Caso o Chefe tenha reduzido a vida a metade, dispara um tiro especial na posição central e tiros normais nas laterais. */
            if(aumentou == 2){
                if(tempoAtual > proximoTiroEspecial){
                    projeteisInimgos.disparar(entIni_base.getX(), entIni_base.getY() + 35,Math.cos(3*(Math.PI/2)) * 0.25, Math.sin(3*(Math.PI/2)) * 0.25 * (-1.0), 30);
                    proximoTiroEspecial = tempoAtual + 2000;
                }
                double angulo = Math.PI/2 + Math.PI/8 + Math.random() * Math.PI/6 - Math.PI/12;
                double vx1 = Math.cos(angulo) * 0.30;
                double vy1 = Math.sin(angulo) * 0.30;
                double vx2 = Math.cos(angulo/2) * 0.30;
                double vy2 = Math.sin(angulo/2) * 0.30;
                projeteisInimgos.disparar(entIni_base.getX() - 30, entIni_base.getY() + 7, vx1, vy1, 2);
                projeteisInimgos.disparar(entIni_base.getX() + 30, entIni_base.getY() + 7, vx2, vy2, 2);
            }
            /*Caso contrário, o Chefe atira normalmente na posição central. */
            else projeteisInimgos.disparar(entIni_base.getX(), entIni_base.getY() + 35,Math.cos(3*(Math.PI/2)) * 0.45, Math.sin(3*(Math.PI/2)) * 0.45 * (-1.0), 2 );
            entIni_base.setProximoTiro( entIni_base.getProximoTiro() + 200);
        }
    }


    /* (4) funções de atualizações do Chefe1 e desenho (também de seus projéteis) ao longo do tempo de jogo. */
    
    /* atualiza os atributos do Chefe1 ao longo do tempo de jogo em três instâncias:
    *  (i) caso esse tenha explodido, torna-se inativo;
    *  (ii) caso contrário, o chefe é atualizado conforme sua lógica de movimento no jogo.
    *  (iii) atualiza a lógica de invencibilidade dos frames para o cálculo de redução de vida do Chefe1 */
    public void update(long delta, double posJogadorX, double posJogadorY) {
        /* instância (i) */
        if (entIni_base.getEstado() == EXPLODING) {
            if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                entIni_base.setEstado(INACTIVATE);
            }
            return;
        }
        /* Verifica se o Chefe teve a vida reduzida à metade de sua vida em algum momento do jogo. */
        if(vid_base.getVidaAtual() == vid_base.getVidaMaxima()/2 && aumentou != 2) aumentou = 1;
        /* Caso o Chefe tenha tido sua vida reduzida à metade, aumenta a sua velocidade. */
        if(aumentou == 1){
            entIni_base.setV(entIni_base.getV() + 0.05);
            aumentou = 2;
        }
        /* instância (ii) */
        if(entIni_base.getEstado() == ACTIVE){
            /* O Chefe entra no cenário do jogo descendo lentamente até certa posição. */
            if(descendo){
                double novoY = entIni_base.getY() + Math.sin(entIni_base.getAngulo()) * entIni_base.getV() * delta * (-1.0);
                if(novoY >= GameLib.HEIGHT/3){
                    novoY = GameLib.HEIGHT/3;
                    descendo = false;
                    entIni_base.setAngulo(0);
                    entIni_base.setProximoTiro(System.currentTimeMillis());
                }
                entIni_base.setX(entIni_base.getX() + Math.cos(entIni_base.getAngulo()) * entIni_base.getV() * delta); 
                entIni_base.setY(novoY);
            }
            /* Ao alcançar a posição, se movimento indo de uma borda a outra da tela. */
            else{
                entIni_base.setX(entIni_base.getX() + Math.cos(entIni_base.getAngulo()) * entIni_base.getV() * delta); 
                if (entIni_base.getX() - entIni_base.getRaio() <= 0){
                    entIni_base.setX(entIni_base.getRaio());
                    entIni_base.setAngulo(0);
                }else if (entIni_base.getX() + entIni_base.getRaio() >= GameLib.WIDTH){
                    entIni_base.setX(GameLib.WIDTH - entIni_base.getRaio());
                    entIni_base.setAngulo(Math.PI);
                }
            }
            /* instância (iii) */
            vid_base.updateInvencibilidade();       
        }
    }

    /* desenha a entidade Chefe1. */
    public void draw() {
        if (entIni_base.getEstado() == EXPLODING) {
            double alpha = (System.currentTimeMillis() - entIni_base.getexplosaoComeco()) / 
                        (double) (entIni_base.getexplosaoFim() - entIni_base.getexplosaoComeco());
            GameLib.drawExplosion(entIni_base.getX(), entIni_base.getY(), alpha);
        } else if(entIni_base.getEstado() == ACTIVE) {
           if(descendo == false) vid_base.drawVidaChefe();
            GameLib.setColor(Color.LIGHT_GRAY);
            /* corpo do nave */
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY()-10, entIni_base.getRaio()*2, 20);    
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY() - 25, entIni_base.getRaio(), 10);
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY() - 35, entIni_base.getRaio() - 20, 10);
            GameLib.fillRect(entIni_base.getX() - 8, entIni_base.getY() - 45, 8, 10);
            GameLib.fillRect(entIni_base.getX() + 8, entIni_base.getY() - 45, 8, 10);
            /* bico da nave */
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY(), 20, 10);
            GameLib.fillRect(entIni_base.getX() - 10,  entIni_base.getY() + 5, 8, 20);
            GameLib.fillRect(entIni_base.getX() + 10, entIni_base.getY() + 5, 8,20);
            GameLib.fillRect(entIni_base.getX() , entIni_base.getY() + 25, 20,20);
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY() + 35, 8, 15);
            GameLib.setColor(Color.DARK_GRAY);
            /* disparadores laterais */
            GameLib.fillRect(entIni_base.getX() - 30, entIni_base.getY() + 7, 8, 15);
            GameLib.fillRect(entIni_base.getX() + 30, entIni_base.getY() + 7, 8, 15);
            GameLib.setColor(Color.GRAY);
            /*asas da nave */
            GameLib.fillRect(entIni_base.getX() - entIni_base.getRaio(), entIni_base.getY()-10, 15, 40);
            GameLib.fillRect(entIni_base.getX() + entIni_base.getRaio(), entIni_base.getY()-10, 15, 40);
        }
    }
}

