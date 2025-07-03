package Mecanicas.bases;

import java.awt.Color;

import Jogo.GameLib;

public class VidaBase {
    private int vidaMaxima;
    private int vidaAtual;
    private boolean invencivel = false;
    private boolean invencibilidade = false;

    private long tempoInvencivel = 0;
    private long DURACAO_INVENCIVEL = 60;

    public VidaBase(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
    }

    public int getVidaAtual() { return this.vidaAtual;}

    public int getVidaMaxima(){ return this.vidaMaxima;}

    public void setVidaMaxima(int vidaMaxima){ this.vidaMaxima = vidaMaxima;}

    public long getTempoInvencibilidade(){ return this.tempoInvencivel;}

    public void setInvencibilidade(boolean invencibilidade){ 
        this.invencivel = invencibilidade;
        this.invencibilidade = invencibilidade;
    }

    public boolean estaMorto() {
        return vidaAtual == 0;
    }

    public boolean estaInvencivel() {
        return invencivel;
    }

    public void reduzir() {
        invencivel = true;
        tempoInvencivel = DURACAO_INVENCIVEL;
        vidaAtual--;
        if (vidaAtual < 0) vidaAtual = 0; 
    }

    public void resetar() {
        vidaAtual = vidaMaxima;
    }

    public void updateInvencibilidade(){
        if(!invencibilidade){
            if(invencivel){
                tempoInvencivel --;
                if(tempoInvencivel <= 0){
                    invencivel = false;
                }
            }
        }
    } 

    /*
    public void updateInvencibilidade() {
        if (invencivel && System.currentTimeMillis() > tempoInvencivel) {
            invencivel = false;
        }
    }
    */

    public void drawVidaJogador() {
        float porcent = (float) vidaAtual / vidaMaxima;

        GameLib.setColor(Color.gray);
        GameLib.fillRect(80.0, GameLib.HEIGHT - 25, 100.0, 20.0);

        GameLib.setColor(Color.RED);
        GameLib.fillRect(30 + (100 * porcent) / 2, GameLib.HEIGHT - 25, 100 * porcent, 20.0);
        
    }


    public void drawVidaChefe () {
        float porcent = (float) vidaAtual / vidaMaxima;

        GameLib.setColor(Color.gray);
        GameLib.fillRect(240, 130, 360.0, 20.0);

        GameLib.setColor(Color.YELLOW);
        GameLib.fillRect(60 + (360 * porcent) / 2, 130, 360 * porcent, 20.0);
    }


}
