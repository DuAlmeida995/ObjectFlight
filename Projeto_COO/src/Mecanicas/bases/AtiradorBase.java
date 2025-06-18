package Mecanicas.bases;

import java.util.List;
import Mecanicas.projetil.*;

/* class AtiradorBase
 * Classe que gerencia a funcionalidade de disparo das entidades do jogo.
*/

public class AtiradorBase {

    private long proximoTiro;  /* Tempo entre os disparos */
    private ProjetilPool pool; /* Objeto para administrar os projéteis da entidade */

    public AtiradorBase(){
        this.pool = new ProjetilPool();
    }

    /* Funções getter e setter para 'proximoTiro' e getter para a 'pool' de projéteis. */
    
    /* proximoTiro */
    public long getProximoTiro(){ return this.proximoTiro;}
    public void setProximoTiro(long proximoTiro){ this.proximoTiro = proximoTiro;}

    /* 'pool' de projéteis */
    public List<Projetil> getProjetilPool(){ return pool.getProjeteis();}
    
    /* Função para verificar se a entidade pode atirar. */    
    public boolean podeAtirar(long tempoAtual) {
        return tempoAtual > proximoTiro;
    }

    /* Função que realiza o disparo de um projétil. */
    public void atirar(double x, double y, double vx, double vy, double raio, long tempoAtual, long tempoAdicional) {
        if(podeAtirar(tempoAtual)){
            pool.disparar(x, y, vx, vy, raio);
            proximoTiro = tempoAtual + tempoAdicional;
        }
    }

    public void updateProjeteis(long delta){pool.update(delta);}
    public void drawProjeteis(){pool.drawProjeteis();} 

}
