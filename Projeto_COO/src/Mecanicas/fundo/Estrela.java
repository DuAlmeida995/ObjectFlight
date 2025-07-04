package Mecanicas.fundo;

import java.awt.Color;

import Jogo.GameLib;

public class Estrela {  

    private double x, y, vy; /* Posicão no eixo x e y, e velocidade no eixo y */

    public Estrela(double x, double y, double vy) {
        this.x = x;
        this.y = y;
        this.vy= vy;
    }

    
    /* ------------------------------------------------------------- Mecânicas da Estrela ------------------------------------------------------------- 
     *  
     * (1) Atualização e desenho;
     * 
    */


    /* (1) funções de atualizações e desenho da estrela longo do tempo de jogo. */

    /* atualiza a posição da estrela ao longo do tempo - somento no sentido vertical. */
    public void update(long delta) {
        this.y += this.vy * delta;
        if (this.y > 600) this.y = 0; 
    }

    /* desenha a estrela na janela do jogo. */
    public void draw() {
        GameLib.setColor(Color.WHITE);
        GameLib.fillRect((int) x, (int) y, 2, 2);
    }
}