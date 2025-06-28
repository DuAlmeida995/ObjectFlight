package Mecanicas.bases;

import java.util.List;
import Mecanicas.projetil.*;

/* class AtiradorBase
 * Classe que serve de base a funcionalidade de disparo das entidades do jogo.
*/

public class AtiradorBase {

    private long proximoTiro;  /* Cadência dos disparos */
    private ProjetilPool pool; /* Objeto para administrar os projéteis da entidade */

    public AtiradorBase(long proximoTiro){
        this.pool = new ProjetilPool();
        this.proximoTiro = proximoTiro;
    }

    /* Funções getter e setter para 'proximoTiro' e getter para a 'pool' de projéteis. */
    
    /* proximoTiro */
    public long getProximoTiro(){ return this.proximoTiro;}
    public void setProximoTiro(long proximoTiro){ this.proximoTiro = proximoTiro;}

    /* 'pool' de projéteis */
    public List<Projetil> getProjeteis(){ return pool.getProjeteis();}
    
    /* Função para verificar se a entidade pode atirar. */    
    public boolean podeDisparar(long tempoAtual) {
        return tempoAtual > proximoTiro;
    }
    
    /* Função para realizar o disparo de um projétil */
    public void disparar(double x, double y, double vx, double vy, double raio) { pool.disparar(x, y, vx, vy, raio);}

    /* Função que atualiza os atributo dos projéteis da entidade. */
    public void updateProjeteis(long delta){pool.update(delta);}

    /* Função a ser utilizada para desenhar os projéteis do jogador. */
    public void drawProjeteisJogador(){pool.drawProjeteisJogador();} 

    /* Função a ser utilizada para desenhar os projéteis do jogador. */
    public void drawProjeteisInimigo(){pool.drawProjeteisInimigo();}

}
