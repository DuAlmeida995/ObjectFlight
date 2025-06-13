package Mecanicas;

import java.awt.Graphics2D;
import java.awt.Color;

public class Inimigo2 extends Entidade implements Atirador {
        private int contadorTiro = 0;
        private long nextShootTime = 0;
        private ProjetilPool pool;

        public Inimigo2(double x, double y, ProjetilPool pool) {
                super((int)x, (int)y, 0.0, 1.0, 12.0);
                this.pool = pool;
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
                mover(deltaTime);

                // Lógica de tiro
                contadorTiro++;
                if (contadorTiro >= 100 && canShoot(now)) {
                        shoot(now, pool);
                        contadorTiro = 0;
                }
        }

        @Override
        public void draw(Graphics2D g2d) {
                if (estado == EXPLODINDO) {
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
                if (estado == ACTIVATE) {
                        estado = EXPLODING;
                        explosaoComeco = now();
                        explosaoFim = explosaoComeco + 500;
                }
        }

        @Override
        public boolean podeAtirar(long now) {
                return now >= nextShootTime;
        }

        @Override
        public void shoot(long now, ProjetilPool pool) {
                if(!podeAtirar(now)) return;
                pool.disparar(x, y + radius, 0.0, 0.5, 2.0);
                nextShootTime = now + 500;
        }

        // utilitário para pegar o tempo atual
        private long now() {
                return System.currentTimeMillis();
        }
}
