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

    public void disparar(AtiradorBase projeteisInimgos, long tempoAtual){;}

    public void reduzir() { vid_base.reduzir();}
    public boolean estaMorto() { return vid_base.estaMorto();}
    public boolean estaInvencivel() { return vid_base.estaInvencivel();}

    /* Função que atualiza os atributos do inimigo de tipo 1 ao longo do tempo de jogo em duas condições:
    * (i) caso esse tenha explodido, torna-se inativo;
    * (ii) caso esse tenha ultrapassado os limites do jogo.
       Caso nenhuma dessas condições tenha sido alcançadas, o inimigo é atualizado conforme sua lógica de movimento no jogo. */
    public void update(long delta) {

        vid_base.updateInvencibilidade();

        if (entIni_base.getEstado() == EXPLODING) {
            if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                entIni_base.setEstado(INACTIVATE);
            }
            return;
        }
        
        if(entIni_base.getEstado() == ACTIVE){
            if(descendo){
                double novoY = entIni_base.getY() + Math.sin(entIni_base.getAngulo()) * entIni_base.getV() * delta * (-1.0);
            
                if(novoY >= GameLib.HEIGHT/3){
                    novoY = GameLib.HEIGHT/3;
                    descendo = false;
                    entIni_base.setAngulo(0);
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
            vid_base.drawVidaChefe();
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawCircle(entIni_base.getX(), entIni_base.getY(), (float)entIni_base.getRaio());

        }
    }

}

