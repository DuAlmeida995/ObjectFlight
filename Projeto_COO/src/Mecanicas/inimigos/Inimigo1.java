package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.Entidade;

public class Inimigo1 extends Entidade{
    private double angulo;
    private double rotacaoVelocidade;
    protected double speed;

    public Inimigo1(double x, double y, double speed, double angulo, double rotacaoVelocidade) {
        super(x, y, 0.0, 0.0, 9.0);    // vx e vy não vão mais armazenar speed
        this.speed             = speed;
        this.angulo            = angulo;
        this.rotacaoVelocidade = rotacaoVelocidade;
    }

    @Override
    public void move(long delta) {
        // calcule o deslocamento "dx" e "dy" a partir do módulo e do ângulo
        double dx = Math.cos(angulo) * speed * delta;
        double dy = Math.sin(angulo) * speed * delta;
        // se o eixo Y do seu sistema cresce para baixo, inverta o sinal:
        // dy = -dy;

        x += dx;
        y += dy;

        angulo += rotacaoVelocidade * delta;
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
        move(delta);

        // Se desejar, aqui você pode checar saída de tela:
        if (y > GameLib.HEIGHT + raio) {
            estado = INACTIVATE;
        }
    }

    public void draw() {
        if (estado == EXPLODING) {
            double progress = (System.currentTimeMillis() - explosaoComeco)
                    / (double)(explosaoFim - explosaoComeco);
            GameLib.drawExplosion(x, y, progress);
        } else {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(x, y, (float)raio);
        }
    }

  
    @Override
    public void onCollision(Colidivel outro) {
        if (estado == ACTIVE) {
            estado = EXPLODING;
            explosaoComeco = System.currentTimeMillis();
            explosaoFim    = explosaoComeco + 500;
        }
    }
}
