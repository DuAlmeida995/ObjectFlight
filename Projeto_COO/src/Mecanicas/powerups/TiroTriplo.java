package Mecanicas.powerups;

import java.awt.Color;
import Jogo.GameLib;
import Mecanicas.bases.EntidadeBase;
import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;

import static Mecanicas.constantes.Estados.*;

public class TiroTriplo extends EntidadeBase implements Colidivel {
    private Estados estado = ACTIVE;

    public TiroTriplo(double x, double y) {
        super(x, y, 10); // raio 10
    }

    public void update(long delta) {
        if (estado == ACTIVE) setY(getY() + delta * 0.15); // velocidade vertical
        if (getY() > GameLib.HEIGHT + 10) estado = INACTIVATE;
    }

    public void draw() {
        if (estado == ACTIVE) {
            GameLib.setColor(Color.ORANGE);
            GameLib.drawDiamond(getX(), getY(), getRaio());
        }
    }

    public Estados getEstado() { return estado; }

    public void desativar() { estado = INACTIVATE; }

    public boolean isAtivo() { return estado == ACTIVE; }
}