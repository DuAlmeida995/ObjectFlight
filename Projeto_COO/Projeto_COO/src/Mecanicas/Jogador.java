package Mecanicas
import java.awt.Graphics2D;

    public class Jogador extends Entidade implements Atirador, Colidivel {
        private double vx, vy
        //protected long proximoTiro;
        public Jogador(int x, int y) {
            super(x, y, 0.25, 0.25, 12.0);
            this.proximoTiro = System.currentTimeMillis();
        }

        protected void setVelocity(double vx, double vy) {
            this.vx = vx;
            this.vy = vy;
        }

        @Override
        public void draw(Graphics2D g2d) {
            if (estado == EXPLODINDO) {
                double alpha = (System.currentTimeMillis() - explosaoComeco) /
                        (double)(explosaoFim - explosaoComeco);
                GameLib.drawExplosion(x, y, alpha);
            } else {
                g2d.setColor(Color.BLUE);
                GameLib.drawPlayer(x, y, radius);
            }
        }

        @Override
        public void update(long delta) {
            if (estado == EXPLODINDO) {
                // aguarda fim da explosão
                if (System.currentTimeMillis() > explosaoFim) {
                    estado = ATIVO;
                }
                return;
            }
            // move de acordo com vx/vy
            this.x += vx * delta;
            this.y += vy * delta;
            // mantém dentro da tela
            this.x = Math.max(0, Math.min(this.x, GameLib.WIDTH - radius));
            this.y = Math.max(25, Math.min(this.y, GameLib.HEIGHT - radius));
        }

        public boolean podeAtirar(long currentTime) {
            return currentTime >= proximoTiro;
        }

        public void atirar(long currentTime, ProjetilPool projetil) {
            if(!podeAtirar(currentTime)) return;
            projetil.disparar(x, y - radius * 2, 0.0, -1.0, 2.0);
            this.proximoTiro = currentTime + 100; // Delay entre tiros
        }

        public void explodir(long currentTime) {
            tempoExplosaoInicio = currentTime;
            tempoExplosaoFim = currentTime + 500;
            estado = EXPLODINDO;
        }

        public void onCollision(Colidivel outro) {
            if (estado == ATIVO) {
                estado = EXPLODINDO;
                explosaoComeco = System.currentTimeMillis();
                explosaoFim = explosaoComeco + 2000;
            }


        //@Override
        //public void atirar(Projetil projetil) {
         //   projetil.disparar(x + largura / 2, y);
        //}
    }
