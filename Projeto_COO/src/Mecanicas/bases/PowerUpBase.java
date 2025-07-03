package Mecanicas.bases;

import Jogo.GameLib;
import java.awt.Color;

import Mecanicas.constantes.Estados;
import static Mecanicas.constantes.Estados.*;

import Mecanicas.interfaces.Colidivel;

public class PowerUpBase {
    
    private EntidadeBase ent_base;   /* Objeto para administrar as propriedades de 'Entidade'  */
    
    private boolean powerUpAtivo; /* Efeito do tiro triplo ativo ou não. */
    private long powerUpTempo;    /* Tempo de duração do efeito de tiro triplo, que reduz ao longo do tempo. */
    private long tempoTotal;         /* Tempo total de duração, utilizado para o desenha da barra de tempo do powerup. */

    public PowerUpBase(double x, double y, double raio) {
        this.ent_base = new EntidadeBase(x, y, raio);
    }

    /* Funções getters de posição, raio e estado.*/
    
    /* posição */
    public double getX(){ return ent_base.getX();}
    public double getY(){ return ent_base.getY();}

    /* raio */
    public double getRaio(){ return ent_base.getRaio();}

    /* estado */
    public Estados getEstado() { return ent_base.getEstado(); }
    public void setEstado(Estados estado) { ent_base.setEstado(estado);}

    /* powerUpAtivo */
    public boolean getPowerUpAtivo() { return this.powerUpAtivo;}
    public void setPowerUpAtivo(boolean powerUpAtivo) { this.powerUpAtivo = powerUpAtivo;}

    /* powerUpTempo */
    public long getPowerUpTempo() { return this.powerUpTempo;}
    public void setPowerUpTempo(long powerUpTempo) { this.powerUpTempo = powerUpTempo;}


    /* ------------------------------------------------------------- Mecânicas do PowerUpBase ------------------------------------------------------------- 
     * 
     * (1) Ativação e desativação;
     * (2) Colisão;
     * (3) Atualização e desenho.
     * 
    */
    
    /* (1) funções de ativação e desativação do efeito de tiro triplo no jogador. */

    /* ativa o efeito do tiro triplo, iniciando o contador do powerup e aplicando no jogador */
    public void ativar(long powerUpTempo){
        powerUpAtivo = true;
        this.powerUpTempo = powerUpTempo;
        this.tempoTotal = powerUpTempo;
    }

    /* desativa o efeito do tiro triplo, aplica ao jogador a mudança, e atribuindo o estado de inativo ao powerup, podendo ser assim removido */
    public void desativar(){
        powerUpAtivo = false;
        ent_base.setEstado(INACTIVATE);
    }

   
    /* (3) funções de execução da lógica de colisão e eventual remoção do powerup.*/
 
    /* calcula se uma entidade entra em colisão com outra. */ 
    public boolean colideCom(Colidivel o){ return ent_base.colideCom(o);}
    
    /* atualiza o estado do powerup para explosão afim de, assim, remover o objeto físico do powerup na lógica do jogo, porém mantendo o objeto ativo 
     * para o cálculo do tempo do efeito e o desenho da barra*/
    public void remover() {
        ent_base.setEstado(EXPLODING); 
    }
 

    /* (4) funções de atualizações do objeto de powerup e desenho (também da barra de tempo) ao longo do tempo de jogo */

    /* atualiza os atributos do powerup ao longo do tempo de jogo em duas condições:
    *  (i) caso esse tenha ultrapassado os limites do jogo, torna-se inativo;
    *  (ii) caso o tempo do efeito tenha passado, torna-se inativo;
    *  (iii) caso nenhuma dessas condições tenha sido alcançadas, o powerup é atualizado conforme sua lógica de movimento no jogo*/    
    public void update(long delta) {
        /* condição (i) */
        if (ent_base.getY() > GameLib.HEIGHT + 10) ent_base.setEstado(INACTIVATE);
        /* condição (ii) */
        if(powerUpAtivo){
            powerUpTempo --;
            if(powerUpTempo <= 0){
                desativar();
            }
        }
        /* condição (iii) */
        if (ent_base.getEstado() == ACTIVE){
            /* movimento vertical descendente */
            ent_base.setY(ent_base.getY() + delta * 0.15);
        }
    }   

    /* desenha a entidade do powerup e barra de tempo, caso o efeito esteja ativo */
    public void drawTiroTriplo() {
        if (ent_base.getEstado() == ACTIVE) {
            GameLib.setColor(Color.ORANGE);
            GameLib.drawDiamond(getX(), getY(), getRaio());
        }
        if(powerUpAtivo){
            float porcent = (float)powerUpTempo/tempoTotal;
            GameLib.setColor(Color.DARK_GRAY);
            GameLib.fillRect(80.0, GameLib.HEIGHT - 40, 100.0, 5);
            GameLib.setColor(Color.WHITE);
            GameLib.fillRect(30 + (100 * porcent) / 2, GameLib.HEIGHT - 40, 100 * porcent, 5);
        }   
    }

    public void drawInvencibilidade(){
        if (ent_base.getEstado() == ACTIVE) {
            GameLib.setColor(Color.WHITE);
            GameLib.drawCircle(ent_base.getX(), ent_base.getY(), ent_base.getRaio());
        }
        if(powerUpAtivo){
            float porcent = (float)powerUpTempo/tempoTotal;
            GameLib.setColor(Color.DARK_GRAY);
            GameLib.fillRect(80.0, GameLib.HEIGHT - 60, 100.0, 5);
            GameLib.setColor(Color.WHITE);
            GameLib.fillRect(30 + (100 * porcent) / 2, GameLib.HEIGHT - 60, 100 * porcent, 5);
        }
    }
}
