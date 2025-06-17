package Mecanicas.jogador;

import java.awt.Color;
import java.util.List;

import Jogo.GameLib;

import Mecanicas.bases.EntidadeBase;
import Mecanicas.bases.ExplosaoBase;
import Mecanicas.bases.MovimentoBase;
import Mecanicas.bases.AtiradorBase;

import static Mecanicas.constantes.Estados.*;

import Mecanicas.interfaces.Colidivel;
import Mecanicas.projetil.*;

    public class Jogador implements Colidivel{

        MovimentoBase mov_base;
        EntidadeBase ent_base;
        ExplosaoBase exp_base;
        AtiradorBase ati_base;

        /* Construtor da classe Jogador - herdando a de Entidade e instanciando o tempo do tiro
         * como o tempo atual.
         */
        public Jogador(double x, double y) {
            ent_base = new EntidadeBase(x,y,12);
            mov_base = new MovimentoBase(0.25, 0.25);
            exp_base = new ExplosaoBase(0, 0);
            ati_base = new AtiradorBase();
        }

        public double getX() { return ent_base.getX();}
        public double getY() { return ent_base.getY();}
        public void setX(double x) { ent_base.setX(x);}
        public void setY(double y) { ent_base.setY(y);}

        public double getRaio() { return ent_base.getRaio();}
        public void setRaio(double raio) {ent_base.setRaio(raio);}
        
        public double getVX(){ return mov_base.getVX();}
        public double getVY(){ return mov_base.getVY();}
        public void setVX(double vx){ mov_base.setVX(vx);;}
        public void setVY(double vy){ mov_base.setVY(vy);}

        public List<Projetil> getProjetilPool(){ return ati_base.getProjetilPool();}
    
        public boolean estaAtivo(){ return ent_base.estaAtivo();}

        /* Função para desenhar a entidade Jogador na tela */
        public void draw() {

            ati_base.drawProjeteis();

            if (ent_base.getEstado() == EXPLODING) {
                double alpha = (System.currentTimeMillis() - exp_base.getexplosaoComeco()) /
                        (double) (exp_base.getexplosaoFim() - exp_base.getexplosaoComeco());
                GameLib.drawExplosion(ent_base.getX(), ent_base.getY(), alpha);
            } else {
                GameLib.setColor(Color.BLUE);
                GameLib.drawPlayer(ent_base.getX(), ent_base.getY(), ent_base.getRaio());
            }
        }

        /* Função que faz com que o jogador atire um projétil, ativando a função de disparar
         * da classe ProjetilPool.
         */
        public void atirar(long tempoAtual){
            ati_base.atirar(ent_base.getX(), ent_base.getY() - ent_base.getRaio() * 2, 0.0, 
            -0.5, 0, tempoAtual, 100);
        }
        
        public boolean colideCom(Colidivel outro){ return ent_base.colideCom(outro);}

        /* Função que atualiza os atributos do jogador quando este entra em colisão com outra
         * entidade.
         */
        public void emColisao() {
            if (ent_base.getEstado() == ACTIVE) {
                ent_base.setEstado(EXPLODING);
                exp_base.setExplosaoComeco(System.currentTimeMillis());
                exp_base.setExplosaoFim(exp_base.getexplosaoComeco() + 2000);
            }

        }

        /* Função a ser utilizada para atualizar os atributos do jogador em duas condições:
         * (i) caso esse tenha explodido e, passado o tempo da explosão, renasce;
         * (ii) caso, ao calcular o input do usuário, o jogador não saia da tela do jogo.
         */
        public void update(long tempoAtual){
             
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

        public void updateProjeteis(long deltaTime){ ati_base.updateProjeteis(deltaTime);}
    }
