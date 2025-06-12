package Mecanicas

import java.awt.Graphics2D;

public class Inimigo1 extends Entidade {
    private double angulo;
    private double rotacaoVelocidade;
    private long proximoTiro;
    private long explosaoInicio, explosaoFim;

    public Inimigo1(double x, double y, double v, double angulo, double rv) {
        super(x, y, 0, v, 9.0);
        this.angulo = angulo;
        this.rotacaoVelocidade = rv;
        this.proximoTiro = System.currentTimeMillis() + 1000;
    }

    public void mover(long deltaTime) {
        x += Math.cos(angulo) * vy * deltaTime;
        y += Math.sin(angulo) * vy * deltaTime;
        angulo += rotacaoVelocidade * deltaTime;
    }

        @Override
        public void draw(Graphics2D g2d) {
            g2d.fillOval(x, y, largura, altura); // inimigo com forma de círculo
        }
    }
