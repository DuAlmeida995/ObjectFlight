package Mecanicas.bases;

import java.util.List;

import Mecanicas.projetil.*;

/* classe DisparadorBase 
 * Classe que serve de base para funcionalidade de disparo das entidades do jogo.
*/

public class DisparadorBase {

    private long proximoTiro;  /* Cadência dos disparos */
    private ProjetilPool pool; /* Objeto para administrar os projéteis da entidade */

    public DisparadorBase(long proximoTiro){
        this.pool = new ProjetilPool();
        this.proximoTiro = proximoTiro;
    }

    /* Funções getter e setter para 'proximoTiro' e getter para a 'pool' de projéteis. */
    
    /* proximoTiro */
    public long getProximoTiro(){ return this.proximoTiro;}
    public void setProximoTiro(long proximoTiro){ this.proximoTiro = proximoTiro;}

    /* 'pool' de projéteis */
    public List<Projetil> getProjeteis(){ return pool.getProjeteis();}
    
    /* ------------------------------------------------------------- Mecânicas da EntidadeBase ------------------------------------------------------------- 
     * 
     * (1) Disparo;
     * (2) Atualização e desenho.
     *  
    */


    /* (1) funções para gerenciar o disparo das entidades. */
    
    /* verifica se a entidade pode atirar. */    
    public boolean podeDisparar(long tempoAtual) {
        return tempoAtual > proximoTiro;
    }
    
    /* realizar o disparo de um projétil (adiciona um projétil na 'pool'). */
    public void disparar(double x, double y, double vx, double vy, double raio) { 
        pool.disparar(x, y, vx, vy, raio);
    }


    /* (2) funções de atualizações e desenho dos projéteis ao longo do tempo de jogo. */

    /* atualiza os atributos dos projéteis da 'pool'. */
    public void updateProjeteis(long delta){
        pool.update(delta);
    }

    /* desenha os projéteis do jogador. */
    public void drawProjeteisJogador(){
        pool.drawProjeteisJogador();
    } 

    /* desenha os projéteis do inimigo. */
    public void drawProjeteisInimigo(){
        pool.drawProjeteisInimigo();
    }
}
