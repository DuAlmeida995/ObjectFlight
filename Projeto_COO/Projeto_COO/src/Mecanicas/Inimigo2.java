package Mecanicas

import java.awt.Graphics2D;

public class Inimigo2 extends Entidade implements Atirador {
        private int contadorTiro = 0;
        private long nextShootTime = 0;
        private ProjetilPool pool;

        public Inimigo2(double x, double y, ProjetilPool pool) {
                super(x, y, 0.0, 1.0, 12.0);
                this.pool = pool;
        }

        @Override
        public void update(long deltaTime) {
                mover(deltaTime);
                contadorTiro++;
                long now = System.currentTimeMillis();
                if (contadorTiro >= 100 && canShoot(now)) {
                        shoot(now);
                        contadorTiro = 0;
                }
        }

        @Override
        public void draw(Graphics2D g2d) {
                int size = (int)(raio * 2);
                g2d.drawRect((int)(x - raio), (int)(y - raio), size, size);
        }
        @Override
        public boolean canShoot(long now) {
                return now >= nextShootTime;
        }
        @Override
        public void shoot(long now) {
                pool.disparar(x, y + raio, 0.0, 0.5);
                nextShootTime = now + 500;
        }
}