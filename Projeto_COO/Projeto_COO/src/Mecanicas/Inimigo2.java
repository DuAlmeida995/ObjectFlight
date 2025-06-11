package Mecanicas

        public class Inimigo2 extends Entidade implements Atirador {
                private int contadorTiro = 0;

                public Enemy2(int x, int y) {
                        super(x, y);
                        this.velocidadeY = 1; // Mais lento que Enemy1
                        this.radius = 12.0
                }

                @Override
                public void update() {
                        mover();
                        contadorTiro++;
                        if (contadorTiro >= 100) { // Atira a cada 100 frames
                                contadorTiro = 0;
                                // Aqui você chamaria o método atirar com o pool apropriado
                        }
                }

                @Override
                public void draw(Graphics2D g2d) {
                        g2d.drawRect(x, y, largura, altura); // inimigo com forma de quadrado
                }

                @Override
                public void atirar(ProjectilePool pool) {
                        pool.disparar(x + largura / 2, y + altura);
                }
        }
        }