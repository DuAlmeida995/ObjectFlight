package Mecanicas.chefes;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.bases.AtiradorBase;
import Mecanicas.bases.EntidadeInimigoBase;
import Mecanicas.bases.VidaBase;
import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.*;

import static Mecanicas.constantes.Estados.*;

/* class Inimigo1
 * Classe que implementa a entidade de inimigo tipo 1 no jogo.
*/
public class Chefe1 implements EntidadeInimigo, Colidivel{
    private boolean descendo = true;
    private int aumentou = 0;
    private long tiroEspecial = 0;

    EntidadeInimigoBase entIni_base; /* Objeto que administra as propriedades de 'Entidade Inimigo' */
    VidaBase vid_base;

    public Chefe1(Estados estados, double x, double y, double v, double angulo, double raio, double vr, long tempoAtual, int vidaMaxima) {
        entIni_base = new EntidadeInimigoBase(x, y, v, angulo, raio, vr, tempoAtual);
        vid_base = new VidaBase(vidaMaxima);
        entIni_base.setEstado(estados);
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
    public void setEstado(Estados estados) { entIni_base.setEstado(estados);}
    /* Função que calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

    /* Função que atualiza o estado do inimigo (ou projétil deste) quando este entra em contato com uma entidade colidível. */
    public void emColisao(){ entIni_base.emColisao();}

    public void disparar(AtiradorBase projeteisInimgos, long tempoAtual){
        if(tempoAtual > entIni_base.getProximoTiro() && !descendo){
            if(aumentou == 2){
                if(tempoAtual > tiroEspecial){
                    projeteisInimgos.disparar(entIni_base.getX(), entIni_base.getY() + 35,Math.cos(3*(Math.PI/2)) * 0.25, Math.sin(3*(Math.PI/2)) * 0.25 * (-1.0), 30);
                    tiroEspecial = tempoAtual + 2000;
                }
                double angulo = Math.PI/2 + Math.PI/8 + Math.random() * Math.PI/6 - Math.PI/12;
                double vx1 = Math.cos(angulo) * 0.30;
                double vy1 = Math.sin(angulo) * 0.30;
                double vx2 = Math.cos(angulo/2) * 0.30;
                double vy2 = Math.sin(angulo/2) * 0.30;
                projeteisInimgos.disparar(entIni_base.getX() - 30, entIni_base.getY() + 7, vx1, vy1, 2);
                projeteisInimgos.disparar(entIni_base.getX() + 30, entIni_base.getY() + 7, vx2, vy2, 2);
                    
            }else projeteisInimgos.disparar(entIni_base.getX(), entIni_base.getY() + 35,Math.cos(3*(Math.PI/2)) * 0.45, Math.sin(3*(Math.PI/2)) * 0.45 * (-1.0), 2 );
           
            entIni_base.setProximoTiro( entIni_base.getProximoTiro() + 200);

        }
    }

    public void reduzir() { vid_base.reduzir();}
    public boolean estaMorto() { return vid_base.estaMorto();}
    public boolean estaInvencivel() { return vid_base.estaInvencivel();}

    /* Função que atualiza os atributos do inimigo de tipo 1 ao longo do tempo de jogo em duas condições:
    * (i) caso esse tenha explodido, torna-se inativo;
    * (ii) caso esse tenha ultrapassado os limites do jogo.
       Caso nenhuma dessas condições tenha sido alcançadas, o inimigo é atualizado conforme sua lógica de movimento no jogo. */
    public void update(long delta, double posJogadorX, double posJogadorY) {

        vid_base.updateInvencibilidade();
    
        if (entIni_base.getEstado() == EXPLODING) {
            if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                entIni_base.setEstado(INACTIVATE);
            }
            return;
        }
        
        if(vid_base.getVidaAtual() == 50 && aumentou != 2) aumentou = 1;
        if(aumentou == 1){
            entIni_base.setV(entIni_base.getV() + 0.05);
            aumentou = 2;
        }

        if(entIni_base.getEstado() == ACTIVE){
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
            
            }else{
                entIni_base.setX(entIni_base.getX() + Math.cos(entIni_base.getAngulo()) * entIni_base.getV() * delta); 
                if (entIni_base.getX() - entIni_base.getRaio() <= 0){
                    entIni_base.setX(entIni_base.getRaio());
                    entIni_base.setAngulo(0);
                }else if (entIni_base.getX() + entIni_base.getRaio() >= GameLib.WIDTH){
                    entIni_base.setX(GameLib.WIDTH - entIni_base.getRaio());
                    entIni_base.setAngulo(Math.PI);
                }
            }
        }              
    }

    /* Função para desenhar a entidade inimigo tipo 1. */
    public void draw() {
        if (entIni_base.getEstado() == EXPLODING) {
            double alpha = (System.currentTimeMillis() - entIni_base.getexplosaoComeco()) / 
                        (double) (entIni_base.getexplosaoFim() - entIni_base.getexplosaoComeco());
            GameLib.drawExplosion(entIni_base.getX(), entIni_base.getY(), alpha);
        } else if(entIni_base.getEstado() == ACTIVE) {
           if(descendo == false) vid_base.drawVidaChefe();
            GameLib.setColor(Color.LIGHT_GRAY);
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY()-10, entIni_base.getRaio()*2, 20);    
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY() - 25, entIni_base.getRaio(), 10);
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY() - 35, entIni_base.getRaio() - 20, 10);
            GameLib.fillRect(entIni_base.getX() - 8, entIni_base.getY() - 45, 8, 10);
            GameLib.fillRect(entIni_base.getX() + 8, entIni_base.getY() - 45, 8, 10);
        
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY(), 20, 10);
            GameLib.fillRect(entIni_base.getX() - 10,  entIni_base.getY() + 5, 8, 20);
            GameLib.fillRect(entIni_base.getX() + 10, entIni_base.getY() + 5, 8,20);
            GameLib.fillRect(entIni_base.getX() , entIni_base.getY() + 25, 20,20);
            GameLib.fillRect(entIni_base.getX(), entIni_base.getY() + 35, 8, 15);
        
            GameLib.setColor(Color.DARK_GRAY);

            GameLib.fillRect(entIni_base.getX() - 30, entIni_base.getY() + 7, 8, 15);
            GameLib.fillRect(entIni_base.getX() + 30, entIni_base.getY() + 7, 8, 15);
        
            GameLib.setColor(Color.GRAY);

            GameLib.fillRect(entIni_base.getX() - entIni_base.getRaio(), entIni_base.getY()-10, 15, 40);
            GameLib.fillRect(entIni_base.getX() + entIni_base.getRaio(), entIni_base.getY()-10, 15, 40);


        }
    }

}

