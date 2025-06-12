package Mecanicas
import java.awt.Graphics2D;

    public class Jogador extends Entidade implements Atirador, Colidivel, Explodivel {
        private double vx, vy
        protected long nextShoot;
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
        public void atirar(Projetil projetil) {
            projetil.disparar(x + largura / 2, y);
        }
    }


    }