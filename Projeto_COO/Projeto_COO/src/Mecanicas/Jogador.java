package Mecanicas
import java.awt.Graphics2D;

    public class Jogador extends Entidade implements Atirador {
        private double vx, vy
        protected long proximoTiro;
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
            g2d.fillRect(x, y, largura, altura);  // simples retângulo como visual
        }

        public boolean podeAtirar(long currentTime) {
            return currentTime >= proximoTiro;
        }

        public void atirar(long currentTime) {
            this.proximoTiro = currentTime + 100; // Delay entre tiros
        }

        public void explodir(long currentTime) {
            tempoExplosaoInicio = currentTime;
            tempoExplosaoFim = currentTime + 500;
            estado = EXPLODINDO;
        }

        @Override
        //public void atirar(Projetil projetil) {
         //   projetil.disparar(x + largura / 2, y);
        //}
    }
