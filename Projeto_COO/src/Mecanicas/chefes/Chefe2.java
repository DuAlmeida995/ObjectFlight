package Mecanicas.chefes;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.bases.DisparadorBase;
import Mecanicas.bases.EntidadeInimigoBase;
import Mecanicas.bases.VidaBase;

import Mecanicas.constantes.Estados;

import Mecanicas.interfaces.*;
import static Mecanicas.constantes.Estados.*;

/* class Chefe2
 * Classe que implementa a entidade de chefe de tipo 2 no jogo. O Chefe2 causa dano interagindo fisicamente com o Jogador, isto é, ele não atira e segue o jogador para lhe causar dano.
*/
public class Chefe2 implements Chefe, Colidivel{

    EntidadeInimigoBase entIni_base; /* Objeto que administra as propriedades de 'Entidade Inimigo' */
    VidaBase vid_base;               /* Objeto que adminsitra as propriedades de 'Vida Base ' */

    private boolean descendo ;       /* Variável que controla a lógica de movimento */
    
    private double posDestinoX;      /* Variável para destinar o movimento de perseguição no eixo X */
    private double posDestinoY;      /* Variável para destinar o movimento de perseguição no eixo Y */

    private long tempoDeEspera;      /* Variável para controlar o tempo de espera da perseguição */
    private boolean esperando;       /* Variável para indicar se o chefe deve mover ou não */
    
    private int quantDeMov;          /* Variável para controlar a quantidade de movimento de perseguição */
    private int contadorDeMov;       /* Variável para contar quantos movimentos de perseguição foram dados */

    public Chefe2(double x, double y, double v, double angulo, double raio, double vr, long tempoAtual, int vidaMaxima) {
        entIni_base   = new EntidadeInimigoBase(x, y, v, angulo, raio, vr, tempoAtual);
        vid_base      = new VidaBase(vidaMaxima);
        descendo      = true;
        posDestinoX   = 0;
        posDestinoY   = 0;
        tempoDeEspera = 0;
        quantDeMov    = 3;
        contadorDeMov = 0;
        esperando     = false;
        entIni_base.setEstado(ACTIVE);
    }
    
    /* Funções getters de posição, raio e estado. */

    /* posição */
    public double getX(){ return entIni_base.getX();}
    public double getY(){ return entIni_base.getY();}

    /* raio */
    public double getRaio() { return entIni_base.getRaio();}

    /* estado */
    public Estados getEstado(){ return entIni_base.getEstado();}

    /* ------------------------------------------------------------- Mecânicas do Chefe2 ------------------------------------------------------------- 
     * 
     * (1) Vida;
     * (2) Colisão;
     * (3) Atualização e desenho;
     * 
    */

    
    /* (1) funções básicas de controle da vida do chefe. */

    public void reduzir() { vid_base.reduzir();}
    public boolean estaMorto() { return vid_base.estaMorto();}
    public boolean estaInvencivel() { return vid_base.estaInvencivel();}

    
    /* (2) funções de execução da lógica de colisão e eventual explosão do chefe. */

    /* calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

    /* atualiza os atributos do chefe caso este exploda. */
    public void emExplosao(){ entIni_base.emExplosao();}


    /* (3) funções de atualizações do Chefe2 e desenho ao longo do tempo de jogo. */

    /* atualiza os atributos do Chefe2 ao longo do tempo de jogo em três instâncias:
    *  (i) caso esse tenha explodido, torna-se inativo;
    *  (ii) caso contrário, o chefe é atualizado conforme sua lógica de movimento no jogo.
    *  (iii) atualiza a lógica de invencibilidade dos frames para o cálculo de redução de vida do Chefe2 */
    public void update(long delta, double posJogadorX, double posJogadorY) {
        /* instâncias (i) */
        if (entIni_base.getEstado() == EXPLODING) {
            if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                entIni_base.setEstado(INACTIVATE);
            }
            return;
        }
        /* instâncias (ii) */
        if(entIni_base.getEstado() == ACTIVE){
            /* O Chefe entra no cenário do jogo descendo lentamente até certa posição. */
            if(descendo){
                double novoY = entIni_base.getY() + Math.sin(entIni_base.getAngulo()) * entIni_base.getV() * delta * (-1.0);
        
                if(novoY >= GameLib.HEIGHT/3){
                    novoY = GameLib.HEIGHT/3;
                    descendo = false;
                    entIni_base.setAngulo(Math.PI/2);
                }

                entIni_base.setX(entIni_base.getX() + Math.cos(entIni_base.getAngulo()) * entIni_base.getV() * delta); 
                entIni_base.setY(novoY);

                posDestinoX = posJogadorX;
                posDestinoY = posJogadorY;
                
            }
            /* Ao alcançar a posição, ele persegue o Jogador, tentando sempre alcançar a última posição que o Jogador esteve.
             * Inicialmente, o Chefe2 tenta alcançar 3 vezes o jogador, e para por alguns segundos. Ao ter a vida reduzido à 50, 
             * o número de tentativas aumenta para 5.*/
            else{
                /* Aumento das tentativas de movimentos. */
                if(vid_base.getVidaAtual() == vid_base.getVidaMaxima()/2) quantDeMov = 5;
                /* Conforme o Chefe2 toma dano, ele aumenta seu tamanho e sua velocidade. */
                entIni_base.setRaio((vid_base.getVidaMaxima() - vid_base.getVidaAtual()/2)/(vid_base.getVidaMaxima()/100));
                entIni_base.setV(1.1 - (double)(vid_base.getVidaAtual()/(vid_base.getVidaMaxima()*3)));
                /*Tempo de espera entre as sequências de perseguições. */
                if(esperando){
                    if(System.currentTimeMillis() - tempoDeEspera >= 3000){
                        esperando = false;
                        contadorDeMov = 0;
                        posDestinoX = posJogadorX;
                        posDestinoY = posJogadorY;
                    }
                } 
                /* Realiza a perseguição ao Jogador. */
                else if(contadorDeMov < quantDeMov){
                    double dx = posDestinoX - entIni_base.getX();
                    double dy = posDestinoY - entIni_base.getY();
                    double distancia = Math.sqrt(dx * dx + dy * dy);
                    if(distancia > entIni_base.getV()){
                        entIni_base.setX(entIni_base.getX() + entIni_base.getV()*(dx/distancia));
                        entIni_base.setY(entIni_base.getY() + entIni_base.getV()*(dy/distancia));
                    }else{
                        entIni_base.setX(posDestinoX);
                        entIni_base.setY(posDestinoY);
                        contadorDeMov ++;
                        if(contadorDeMov < quantDeMov){
                            posDestinoX = posJogadorX;
                            posDestinoY = posJogadorY;    
                        }else{
                            tempoDeEspera = System.currentTimeMillis();
                            esperando = true;
                        }
                    }
                }
            }  
            /* instâncias (iii) */
            vid_base.updateInvencibilidade();
        }  
    }

    /* desenha a entidade Chefe2. */
    public void draw() {
        if (entIni_base.getEstado() == EXPLODING) {
            double alpha = (System.currentTimeMillis() - entIni_base.getexplosaoComeco()) / 
                        (double) (entIni_base.getexplosaoFim() - entIni_base.getexplosaoComeco());
            GameLib.drawExplosion(entIni_base.getX(), entIni_base.getY(), alpha);

        } else if(entIni_base.getEstado() == ACTIVE) {
            if(descendo == false) vid_base.drawVidaChefe();
            GameLib.setColor(Color.ORANGE);
            /* traços da boca */
            GameLib.drawLine(entIni_base.getX(), entIni_base.getY(), entIni_base.getX() + entIni_base.getRaio() - 8, entIni_base.getY() - 30);
            GameLib.drawLine(entIni_base.getX(), entIni_base.getY(), entIni_base.getX() + entIni_base.getRaio() - 8, entIni_base.getY() + 30);
            /* olho */
            GameLib.drawCircle(entIni_base.getX() - 20 , entIni_base.getY() - 20 , entIni_base.getRaio()/8);
            /* corpo */
            GameLib.drawCircle(entIni_base.getX(), entIni_base.getY(), entIni_base.getRaio());
            GameLib.setColor(Color.BLACK);
            /* quadrado preto para realizar a abertura da boca na circunferência */
            GameLib.fillRect(entIni_base.getX() + entIni_base.getRaio(), entIni_base.getY(), 14, 60);
        }
    }

    /* Implementação vazia da função disparar afim de cumprir com a interface 'Chefe'. O Chefe2 não realiza disparos */
    public void disparar(DisparadorBase projeteisInimgos, long tempoAtual){;}

}

