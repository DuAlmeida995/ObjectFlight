package Mecanicas.jogador;

import java.awt.Color;
import java.util.List;


import Jogo.GameLib;

import Mecanicas.bases.EntidadeBase;
import Mecanicas.bases.ExplosaoBase;
import Mecanicas.bases.MovimentoBase;
import Mecanicas.bases.VidaBase;
import Mecanicas.constantes.Estados;
import Mecanicas.bases.AtiradorBase;

import static Mecanicas.constantes.Estados.*;

import Mecanicas.interfaces.Colidivel;
import Mecanicas.projetil.*;

/* class Jogador
 * Classe que implementa a entidade de jogador no jogo.
*/

public class Jogador implements Colidivel{

    AtiradorBase ati_base;  /* Objeto para administrar as propriedades de 'Atirador' */
    EntidadeBase ent_base;  /* Objeto para administrar as propriedades de 'Entidade' */
    ExplosaoBase exp_base;  /* Objeto para administrar as propriedades de 'Explosao' */
    MovimentoBase mov_base; /* Objeto para administrar as propriedades de 'Movimento' */
    VidaBase vid_base;      /* Objeto para administrar as propriedades de 'Vida' */

    public Jogador(double x, double y, long tempoAtual, int vidaMaxima) {
        ent_base = new EntidadeBase(x,y,12); /* Raio -> 12 */ 
        mov_base = new MovimentoBase(0.25, 0.25); /* Velocidade no eixo X e eixo Y -> 0.25 */
        exp_base = new ExplosaoBase(0, 0);
        ati_base = new AtiradorBase(tempoAtual + 100); /* Cadência dos disparos dos projéteis */
        vid_base = new VidaBase(vidaMaxima);
    }

    /* Funções getters e setters de posição, raio, velocidade no eixo X e eixo Y, estado e um getter para lista de projéteis.*/
    
    /* posição */
    public double getX() { return ent_base.getX();}
    public double getY() { return ent_base.getY();}
    public void setX(double x) { ent_base.setX(x);}
    public void setY(double y) { ent_base.setY(y);}

    /* raio */
    public double getRaio() { return ent_base.getRaio();}
    public void setRaio(double raio) {ent_base.setRaio(raio);}
        
    /* velocidade no eixo X e eixo Y */
    public double getVX(){ return mov_base.getVX();}
    public double getVY(){ return mov_base.getVY();}
    public void setVX(double vx){ mov_base.setVX(vx);;}
    public void setVY(double vy){ mov_base.setVY(vy);}

    /* estado */
    public Estados getEstado(){ return ent_base.getEstado();}
    public void setEstado(Estados estados){ ent_base.setEstado(estados);}

    /* 'pool' de projéteis */
    public List<Projetil> getProjetilPool(){ return ati_base.getProjeteis();}

    
    /* Função que faz com que o jogador atire um projétil, ativando a função de atirar
    * da classe ProjetilPool. */
    public void atirar(long tempoAtual){
        if(ati_base.podeAtirar(tempoAtual)){
            ati_base.disparar(ent_base.getX(), ent_base.getY() - ent_base.getRaio() * 2, 0.0, 
            -1.0, 0);
            ati_base.setProximoTiro(tempoAtual + 100);
        }
    }
        
    /* Função que calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel outro){ return ent_base.colideCom(outro);}

    /* Função que atualiza o estado do inimigo (ou projétil deste) quando este entra em contato com uma entidade colidível. */
    public void emColisao() {
        ent_base.setEstado(EXPLODING);
        exp_base.setExplosaoComeco(System.currentTimeMillis());
        exp_base.setExplosaoFim(exp_base.getexplosaoComeco() + 2000);
    }

    public boolean estaMorto() { return vid_base.estaMorto();}
    public boolean estaInvencivel() { return vid_base.estaInvencivel();}
    public void reduzir() { vid_base.reduzir();}
    public void resetar() { vid_base.resetar();}
    
    
    /* Função que atualiza os atributos do jogador ao longo do tempo de jogo em duas condições:
    * (i) caso esse tenha explodido e, passado o tempo da explosão, renasce;
    * (ii) caso, ao calcular o input do usuário, o jogador não saia da tela do jogo. */
    public void update(long tempoAtual){
         
        vid_base.updateInvencibilidade();

        /* condição (i) */
        if(ent_base.getEstado() == EXPLODING){
            if(tempoAtual > exp_base.getexplosaoFim()){
                ent_base.setEstado(ACTIVE);
            }
        }
            
        /* condição (ii) */
        if(ent_base.getX() < 0.0) ent_base.setX(0);
		if(ent_base.getX() >= GameLib.WIDTH) ent_base.setX(GameLib.WIDTH - 1);
		if(ent_base.getY() < 25.0) ent_base.setY(25.0);
		if(ent_base.getY() >= GameLib.HEIGHT) ent_base.setY(GameLib.HEIGHT - 1);
    }

    /* Função que atualiza os atributos dos projéteis do jogador. */
    public void updateProjeteis(long deltaTime){ ati_base.updateProjeteis(deltaTime);}

     /* Função para desenhar a entidade Jogador e seus projéteis na tela */
    public void draw() {
      
        if (ent_base.getEstado() == EXPLODING) {
            double alpha = (System.currentTimeMillis() - exp_base.getexplosaoComeco()) / 
                        (double) (exp_base.getexplosaoFim() - exp_base.getexplosaoComeco());
            GameLib.drawExplosion(ent_base.getX(), ent_base.getY(), alpha);
        } else {
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(ent_base.getX(), ent_base.getY(), ent_base.getRaio());
        }
    
        ati_base.drawProjeteisJogador();
        vid_base.drawVidaJogador();
    }


}
