package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.Entidade;

public class Inimigo1 extends Entidade{
    private double angulo;
    private double rotacaoVelocidade;

    public Inimigo1(double x, double y, double velocidade, double angulo, double rotacaoVelocidade) {
        super((int)x, (int)y, 0.0, velocidade, 9.0);
        this.angulo = angulo;
        this.rotacaoVelocidade = rotacaoVelocidade;
    }

    
    public void move(long delta) {
        x += Math.cos(angulo) * vy * delta;
        y += Math.sin(angulo) * vy * delta * (-1);
        angulo += rotacaoVelocidade * delta;
    }

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

    public void emColisao(Colidivel outro) {
        if (estado == ACTIVE) {
            estado = EXPLODING;
            explosaoComeco = System.currentTimeMillis();
            explosaoFim    = explosaoComeco + 500;
        }
    }
}
