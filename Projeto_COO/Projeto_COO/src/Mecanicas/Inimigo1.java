package Mecanicas

import java.awt.Graphics2D;

    public class Inimigo1 extends Entidade {

        public Enemy1(int x, int y) {
            super(x, y);
            this.velocidadeY = 2;
            this.radius = 9.0;
        }

        @Override
        public void update() {
            mover();
        }

        @Override
        public void draw(Graphics2D g2d) {
            g2d.fillOval(x, y, largura, altura); // inimigo com forma de círculo
        }
    }

    }