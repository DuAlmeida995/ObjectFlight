package Mecanicas
import java.awt.Graphics2D;

    public class Jogador extends Entidade implements Atirador, Colidivel {
        private double vx, vy;
        public Jogador(int x, int y) {
            super();
            this.radius = 12.0;
        }

        protected void setVelocity(double vx, double vy) {
            this.vx = vx;
            this.vy = vy;
        }

        @Override
        public void draw(Graphics2D g2d) {
            g2d.fillRect(x, y, largura, altura);  // simples retângulo como visual
        }

        @Override
        public void atirar(ProjectilePool pool) {
            pool.disparar(x + largura / 2, y);
        }
    }


    }