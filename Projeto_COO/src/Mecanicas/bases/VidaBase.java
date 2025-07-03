package Mecanicas.bases;

import java.awt.Color;

import Jogo.GameLib;

/* classe VidaBase
 * Classe que serve de base para o controle da vida do jogador e dos chefes. Além disso, evita que o dano sofrido se perpetue no mesmo frame.
*/

public class VidaBase {

    private int vidaMaxima;                    /* Vida máxima, utilizada para resetar a vida e desenhar a barra */   
    private int vidaAtual;                     /* Vida atual, utilizada para verificar se a entidade morreu ou não */ 
    private boolean invencivel = false;        /* Variável booleana para invencibilidade padrão, isto é, ativa para evitar o dano se perpetue no mesmo frame */
    private boolean invencivelPowerUp = false; /* Variável booleana para invencibilidade ativada pelo powerUp, afim do jogador não sofrer dano por um determinado tempo */

    private long tempoInvencivel = 0;          /* Tempo de invecibilidade */
    private long DURACAO_INVENCIVEL = 60;      /* Duração padrão da invencibilidade (1 frame) */

    public VidaBase(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
    }

    /* Funções getters e setters de vidaMaxima e getter da vidaAtual. */

    /* vidaMaxima */
    public int getVidaMaxima(){ return this.vidaMaxima;}
    public void setVidaMaxima(int vidaMaxima){ this.vidaMaxima = vidaMaxima;}

    /* vidaAtual */
    public int getVidaAtual() { return this.vidaAtual;}

    /* ------------------------------------------------------------- Mecânicas do Jogador ------------------------------------------------------------- 
     * 
     * (1) Ativação do powerup;
     * (2) Gerenciameneto da vida;
     * (3) Atualização e desenho.
     * 
    */
    
    /* (1) Função que ativa a invencibilidade provida do powerup no sistema de vida do jogador. */

    public void ativaInvencibilidade(boolean invencibilidade){ 
        this.invencivel = invencibilidade;
        this.invencivelPowerUp = invencibilidade;
    }

    /* (2) Funções de gerenciamento da vida. */
    
    /* verifica se morreu. */
    public boolean estaMorto() {
        return vidaAtual == 0;
    }
    /* verifica se está invencível. */
    public boolean estaInvencivel() {
        return invencivel;
    }

    /* ao sofrer dano (colidindo com o projétil e para o jogador, fisicamento com os inimigos), tem a vida reduzida e ativa a invencibilidade por frame. */
    public void reduzir() {
        invencivel = true;
        tempoInvencivel = DURACAO_INVENCIVEL;
        vidaAtual--;
        if (vidaAtual < 0) vidaAtual = 0; 
    }

    /* exclusivo ao jogador, tem a vida resetada ao morrer. */
    public void resetar() {
        vidaAtual = vidaMaxima;
    }


    /* (4) funções de atualizações da invencibildade e desenha das barras de vida ao longo do tempo de jogo. */
    public void updateInvencibilidade(){
        /* caso a invenciblidade não seja provida do powerup, realiza a lógica de invencibilidade por frame. */
        if(!invencivelPowerUp){
            if(invencivel){
                tempoInvencivel --;
                if(tempoInvencivel <= 0){
                    invencivel = false;
                }
            }
        }
    } 

    /* barra de vida do jogador */
    public void drawVidaJogador() {
        float porcent = (float) vidaAtual / vidaMaxima;
        GameLib.setColor(Color.gray);
        GameLib.fillRect(80.0, GameLib.HEIGHT - 25, 100.0, 20.0);
        GameLib.setColor(Color.RED);
        GameLib.fillRect(30 + (100 * porcent) / 2, GameLib.HEIGHT - 25, 100 * porcent, 20.0);
        
    }

    /* barra de vida do chefe */
    public void drawVidaChefe () {
        float porcent = (float) vidaAtual / vidaMaxima;
        GameLib.setColor(Color.gray);
        GameLib.fillRect(240, 130, 360.0, 20.0);
        GameLib.setColor(Color.YELLOW);
        GameLib.fillRect(60 + (360 * porcent) / 2, 130, 360 * porcent, 20.0);
    }
}
