package Mecanicas.jogador;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.Entidade;
import Mecanicas.projetil.ProjetilPool;

    public class Jogador extends Entidade{

        private long proximoTiro;

        public Jogador(double x, double y) {
            super(x, y, 0.25, 0.25, 12.0);
            this.proximoTiro = System.currentTimeMillis();
        }

        public void setSpeed(double vx, double vy) {
            this.vx = vx;
            this.vy = vy;
        }

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
    
        public boolean podeAtirar(long currentTime) {
            return currentTime > proximoTiro;
        }

        public void atirar(long tempoAtual, ProjetilPool projetil) {
            if (podeAtirar(tempoAtual)){
                projetil.disparar(x, y - raio * 2, 0.0, -1.0, 0);
                this.proximoTiro = tempoAtual + 100;
            }
        }

        public void explodir(long currentTime) {
            this.explosaoComeco = currentTime;
            this.explosaoFim = currentTime + 500;
            estado = EXPLODING;
        }

        public void emColisao(Colidivel outro) {
            if (estado == ACTIVE) {
                estado = EXPLODING;
                explosaoComeco = System.currentTimeMillis();
                explosaoFim = explosaoComeco + 2000;
            }

        }
        public void update(long tempoAtual){
            
            if(estado == EXPLODING){
                if(tempoAtual > explosaoFim){
                    estado = ACTIVE;
                }
            }

            if(x < 0.0) x = 0;
			if(x >= GameLib.WIDTH) x = GameLib.WIDTH - 1;
			if(y < 25.0) y = 25.0;
			if(y >= GameLib.HEIGHT) y = GameLib.HEIGHT - 1;

        }
    }
