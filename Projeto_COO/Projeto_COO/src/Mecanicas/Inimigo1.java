package Mecanicas;

import java.awt.Graphics2D;
import java.awt.Color;

public class Inimigo1 extends Entidade {
    private double angulo;
    private double rotacaoVelocidade;

    public Inimigo1(double x, double y, double velocidade, double angulo, double rotacaoVelocidade) {
        super((int)x, (int)y, 0.0, velocidade, 9.0);
        this.angulo = angulo;
        this.rotacaoVelocidade = rotacaoVelocidade;
    }

    @Override
    public void update(long delta) {
        if (estado == EXPLODING) {
            if (System.currentTimeMillis() > explosaoFim) {
                estado = INACTIVATE;
            }
            return;
        }

        // Movimento baseado em ângulo e velocidade vy
        x += Math.cos(angulo) * vy * delta;
        y += Math.sin(angulo) * vy * delta * (-1);
        angulo += rotacaoVelocidade * delta;

        // Se desejar, aqui você pode checar saída de tela:
        if (y > GameLib.HEIGHT + radius) {
            estado = INACTIVATE;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (estado == EXPLODING) {
            double progress = (System.currentTimeMillis() - explosaoComeco)
                    / (double)(explosaoFim - explosaoComeco);
            GameLib.drawExplosion(x, y, progress);
        } else {
            g.setColor(Color.CYAN);
            GameLib.drawCircle(x, y, (float)radius);
        }
    }

    @Override
    public void onCollision(Colidivel outro) {
        if (estado == ACTIVATE) {
            estado = EXPLODING;
            explosaoComeco = System.currentTimeMillis();
            explosaoFim    = explosaoComeco + 500;
        }
    }
}
