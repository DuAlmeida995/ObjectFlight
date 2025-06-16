package Mecanicas.jogador;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.interfaces.Colidivel;
import Mecanicas.abstratas.Entidade;
import Mecanicas.projetil.ProjetilPool;

    public class Jogador extends Entidade{

        /* Declaração do tempo em que o jogador atira. */
        private long proximoTiro;

        /* Construtor da classe Jogador - herdando a de Entidade e instanciando o tempo do tiro
         * como o tempo atual.
         */
        public Jogador(double x, double y) {
            super(x, y, 0.25, 0.25, 12.0);
            this.proximoTiro = System.currentTimeMillis();
        }

        /* Função para desenhar a entidade Jogador na tela */
        public void draw() {
            if (estado == EXPLODING) {
                double alpha = (System.currentTimeMillis() - explosaoComeco) /
                        (double) (explosaoFim - explosaoComeco);
                GameLib.drawExplosion(x, y, alpha);
            } else {
                GameLib.setColor(Color.BLUE);
                GameLib.drawPlayer(x, y, raio);
            }
        }

        /* Função para verificar se o jogador pode atirar. */    
        public boolean podeAtirar(long currentTime) {
            return currentTime > proximoTiro;
        }

        /* Função que faz com que o jogador atire um projétil, ativando a função de disparar
         * da classe ProjetilPool.
         */
        public void atirar(long tempoAtual, ProjetilPool projetil) {
            if (podeAtirar(tempoAtual)){
                projetil.disparar(x, y - raio * 2, 0.0, -1.0, 0);
                this.proximoTiro = tempoAtual + 100;
            }
        }

        /* Função que atualiza os atributos do jogador quando este entra em colisão com outra
         * entidade.
         */
        public void emColisao(Colidivel outro) {
            if (estado == ACTIVE) {
                estado = EXPLODING;
                explosaoComeco = System.currentTimeMillis();
                explosaoFim = explosaoComeco + 2000;
            }

        }

        /* Função a ser utilizada para atualizar os atributos do jogador em duas condições:
         * (i) caso esse tenha explodido e, passado o tempo da explosão, renasce;
         * (ii) caso, ao calcular o input do usuário, o jogador não saia da tela do jogo.
         */
        public void update(long tempoAtual){
            /* condição (i) */
            if(estado == EXPLODING){
                if(tempoAtual > explosaoFim){
                    estado = ACTIVE;
                }
            }
            
            /* condição (ii) */
            if(x < 0.0) x = 0;
			if(x >= GameLib.WIDTH) x = GameLib.WIDTH - 1;
			if(y < 25.0) y = 25.0;
			if(y >= GameLib.HEIGHT) y = GameLib.HEIGHT - 1;
        }
    }
