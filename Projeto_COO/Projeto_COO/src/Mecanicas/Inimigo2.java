package Mecanicas;

import java.awt.Graphics2D;
import java.awt.Color;


public class Inimigo2 extends Entidade implements Atirador, Movivel {
        private ProjetilPool pool;
        private int contadorTiro;
        private long proximoTiro;

        public static final int INACTIVATE = 0;
        public static final int ACTIVE = 1;
        public static final int EXPLODING = 2;

        public Inimigo2(double x, double y, ProjetilPool pool) {
                super((int)x, (int)y, 0.0, 1.0, 12.0);
                this.pool = pool;
                this.contadorTiro = 0;
                this.proximoTiro = 0;
        }

        @Override
        public void move(long delta) {
                if (estado == EXPLODING) {
                        if (System.currentTimeMillis() > explosaoFim) {
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

        @Override
        public void update(long deltaTime) {
                long now = System.currentTimeMillis();

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

        @Override
        public void draw(Graphics2D g2d) {
                if (estado == EXPLODING) {
                        // Desenha a explosão
                        double alpha = (now() - explosaoComeco) / (double)(explosaoFim - explosaoComeco);
                        GameLib.drawExplosion(x, y, alpha);
                } else {
                        // Desenha o inimigo quadrado
                        g2d.setColor(Color.MAGENTA);
                        int size = (int)(radius * 2);
                        g2d.drawRect((int)(x - radius), (int)(y - radius), size, size);
                }
        }

        @Override
        public void onCollision(Colidivel outro) {
                if (estado == ACTIVE) {
                        estado = EXPLODING;
                        explosaoComeco = now();
                        explosaoFim = explosaoComeco + 500;
                }
        }

        @Override
        public boolean podeAtirar(long now) {
                return now >= proximoTiro;
        }

        @Override
        public void atirar(long tempoAtual, ProjetilPool pool) {
                if(!podeAtirar(tempoAtual)) return;
                pool.disparar(x, y + radius, 0.0, 0.5, 2.0);
                proximoTiro = tempoAtual + 500;
        }

        // utilitário para pegar o tempo atual
        private long now() {
                return System.currentTimeMillis();
        }
}
