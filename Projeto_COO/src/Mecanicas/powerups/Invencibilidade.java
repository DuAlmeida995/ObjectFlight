package Mecanicas.powerups;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.bases.EntidadeBase;
import Mecanicas.interfaces.Colidivel;

public class Invencibilidade implements Colidivel {

    private EntidadeBase entidade;
    private double velocidadeY = 0.2;
    private boolean ativo = true;

    public Invencibilidade(double x, double y) {
        this.entidade = new EntidadeBase(x, y, 10.0); // raio = 10.0
    }

    public void update(double delta) {
        if (ativo) {
            entidade.setY(entidade.getY() + velocidadeY * delta);
            if (entidade.getY() > GameLib.HEIGHT) ativo = false;
        }
    }

    public void draw() {
        if (ativo) {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(entidade.getX(), entidade.getY(), entidade.getRaio());
        }
    }
    @Override
    public boolean colideCom(double px, double py, double raioJogador) {
        double dx = entidade.getX() - px;
        double dy = entidade.getY() - py;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return ativo && dist < (entidade.getRaio() + raioJogador) * 0.8;
    }

    public void desativar() { ativo = false; }
    public boolean isAtivo() { return ativo; }

    // Implementação de Colidivel
    public double getX() { return entidade.getX(); }
    public double getY() { return entidade.getY(); }
    public double getRaio() { return entidade.getRaio(); }
}