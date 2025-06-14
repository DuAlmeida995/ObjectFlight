package Mecanicas.jogador;

import java.awt.Color;

import Jogo.GameLib;

//import Mecanicas.interfaces.Atirador;
import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.Entidade;
import Mecanicas.projetil.ProjetilPool;

    public class Jogador extends Entidade {
        //private double vx, vy;
        public static final int INACTIVATE = 0;
        public static final int ACTIVE = 1;
        public static final int EXPLODING = 2;
        private long proximoTiro;

        //protected long proximoTiro;
        public Jogador(double x, double y) {
            super(x, y, 0.25, 0.25, 12.0);
            this.proximoTiro = System.currentTimeMillis();
        }

        public void setSpeed(double vx, double vy) {
            this.vx = vx;
            this.vy = vy;
        }

        @Override
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
        
        @Override
        public void move(long delta) {
            
        }

        @Override
        public void update(long delta) {
            if (estado == EXPLODING) {
                // aguarda fim da explosão
                if (System.currentTimeMillis() > explosaoFim) {
                    estado = ACTIVE;
                }
                return;
            }
            // move de acordo com vx/vy
            move(delta);
            // mantém dentro da tela
            this.x = Math.max(0.0, Math.min(this.x, GameLib.WIDTH - raio));
            this.y = Math.max(25.0, Math.min(this.y, GameLib.HEIGHT - raio));
    
        }
        
        public boolean podeAtirar(long currentTime) {
            return currentTime >= proximoTiro;
        }

        public void atirar(long tempoAtual, ProjetilPool projetil) {
            if (!podeAtirar(tempoAtual)) return;
            projetil.disparar(x, y - raio * 2, 0.0, -1.0, 2.0);
            this.proximoTiro = tempoAtual + 100; // Delay entre tiros
        }

        public void explodir(long currentTime) {
            this.explosaoComeco = currentTime;
            this.explosaoFim = currentTime + 500;
            estado = EXPLODING;
        }

        public void onCollision(Colidivel outro) {
            if (estado == ACTIVE) {
                estado = EXPLODING;
                explosaoComeco = System.currentTimeMillis();
                explosaoFim = explosaoComeco + 2000;
            }

            //@Override
            //public void atirar(Projetil projetil) {
            //   projetil.disparar(x + largura / 2, y);
            //}
        }
    }
