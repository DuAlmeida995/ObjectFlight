package Mecanicas.bases;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.powerups.Invencibilidade;

public class VidaBase {
    private int vidaMaxima;
    private int vidaAtual;
    private boolean invencivel = false; 

    private int tempoInvencivel = 0; 
    //private int DURACAO_INVENCIVEL = 60;

    public VidaBase(int vidaMaxima){
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
    }

    public int getVidaAtual(){ return this.vidaAtual; }

    public boolean estaMorto(){
        return vidaAtual == 0;
    }

    public boolean estaInvencivel(){
        return invencivel;
    }

    public void reduzir(){
        invencivel = true;
        tempoInvencivel = System.currentTimeMillis() + 1000;
        vidaAtual --;
        if(vidaAtual < 0) vidaAtual = 0;
    }

    public void resetar(){
        vidaAtual = vidaMaxima;
    }

    public void updateInvencibilidade(){
        if(invencivel){
            tempoInvencivel --;
            if(tempoInvencivel <= 0){
                invencivel = false;
            }
        }
    }

    public void ativarInvencibilidadeTemporaria(int duracao) {
        this.invencivel = true;
        this.tempoInvencivel = System.currentTimeMillis() + duracao;
    }

    public void drawVidaJogador(){
        float porcent = (float) vidaAtual/vidaMaxima;

        GameLib.setColor(Color.gray);
        GameLib.fillRect(80.0, GameLib.HEIGHT - 25, 100.0, 20.0);

        GameLib.setColor(Color.RED);
        GameLib.fillRect(30 + (100 * porcent)/2, GameLib.HEIGHT - 25, 100 * porcent, 20.0);
    }

    public void updateInvencibilidade(){
        if(invencivel && System.currentTimeMillis() > tempoFimInvencibilidade){
            invencivel = false;
        }

    public void drawVidaChefe(){
        float porcent = (float) vidaAtual/vidaMaxima;

        // Gray background bar (Full width, from X=60 to X=420)
        // Center X: (60 + 420) / 2 = 240
        // Width: 420 - 60 = 360
        // Y: 120 (This would be the center Y due to fillRect implementation)
        GameLib.setColor(Color.gray);
        GameLib.fillRect(240, 130, 360.0, 20.0);

        // Yellow health bar (filling, fixed left at 60, shrinking right)
        // Current width: 360 * porcent
        // Current Center X: 60 (fixed left edge) + (current width / 2)
        // Y: 120 (center Y, same as gray bar)
        GameLib.setColor(Color.YELLOW);
        GameLib.fillRect(60 + (360 * porcent)/2, 130, 360 * porcent, 20.0);
    }

}
