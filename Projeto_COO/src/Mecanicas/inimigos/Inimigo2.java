package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.abstratas.EntidadeInimigo;
import Mecanicas.projetil.ProjetilPool;

public class Inimigo2 extends EntidadeInimigo {
        private ProjetilPool pool;
        private int contadorTiro;
        private long proximoTiro;

        public Inimigo2(double x, double y, ProjetilPool pool) {
                super(x, y, 0.0, 1.0, 12.0);
                this.pool = pool;
                this.contadorTiro = 0;
                this.proximoTiro = 0;
        }

        public void move(long delta) {
                if (estado == EXPLODING) {
                        if (now > explosaoFim) {
                                estado = INACTIVATE;
                        }
                        return;
                }

                if (estado == ACTIVE) {
                        // Verifica se saiu da tela
                        if (y > GameLib.HEIGHT + 10) {
                                estado = INACTIVATE;
                                return;
                        }

                        // Move reto para baixo
                        y += vy * delta;
                }
        }

        public void update(long deltaTime) {
                // Se estiver explodindo, aguarda fim e depois inativa
                if (estado == EXPLODING) {
                        if (now > explosaoFim) {
                                estado = INACTIVATE;
                        }
                        return;
                }

                // Movimento normal
                move(deltaTime);

                // Lógica de tiro
                contadorTiro++;
                if (contadorTiro >= 100 && podeAtirar(now)) {
                        atirar(now, pool);
                        contadorTiro = 0;
                }
        }

        public void draw() {
                if (estado == EXPLODING) {
                        // Desenha a explosão
                        double alpha = (now - explosaoComeco) / (double)(explosaoFim - explosaoComeco);
                        GameLib.drawExplosion(x, y, alpha);
                } else {
                        // Desenha o inimigo diamante
                        GameLib.setColor(Color.MAGENTA);
                        GameLib.drawDiamond(x, y, raio);
                }
        }

        public boolean podeAtirar(long now) {
                return now >= proximoTiro;
        }

        public void atirar(long tempoAtual, ProjetilPool pool) {
                if(!podeAtirar(tempoAtual)) return;
                pool.disparar(x, y + raio, 0.0, 0.5, 2.0);
                proximoTiro = tempoAtual + 500;
        }

}
