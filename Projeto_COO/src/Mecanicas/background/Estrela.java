package Mecanicas.background;

import java.awt.Color;

import Jogo.GameLib;

public class Estrela {  

    /* Declara os atributos das estrelas:
     * (double) posição xy e velocidade no eixo y.
     */
    private double x, y, vy;

    /* Construtor da classe Estrela */
    public Estrela(double x, double y, double vy) {
        this.x = x;
        this.y = y;
        this.vy= vy;
    }

    /* Função que atualiza a posição da estrela ao longo do tempo - somento no sentido vertical. */
    public void update(long delta) {
        this.y += this.vy * delta;
        if (this.y > 600) this.y = 0; 
    }

    /* Função que desenha a estrela na janela do jogo. */
    public void draw() {
        GameLib.setColor(Color.WHITE);
        GameLib.fillRect((int) x, (int) y, 2, 2);
    }
}