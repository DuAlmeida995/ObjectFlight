package Mecanicas.bases;

import java.util.List;
import Mecanicas.projetil.*;

public class AtiradorBase {
    
    long proximoTiro;
    ProjetilPool pool;

    public AtiradorBase(){
        this.pool = new ProjetilPool();
    }

    public long getProximoTiro(){ return this.proximoTiro;}
    public void setProximoTiro(long proximoTiro){ this.proximoTiro = proximoTiro;}

    public List<Projetil> getProjetilPool(){ return pool.getProjeteis();}
    
    public void updateProjeteis(long delta){pool.update(delta);}
    public void drawProjeteis(){pool.drawProjeteis();} 

    /* Função para verificar se o jogador pode atirar. */    
    public boolean podeAtirar(long currentTime) {
        return currentTime > proximoTiro;
    }

   
    public void atirar(double x, double y, double vx, double vy, double raio, long tempoAtual, long tempoAdicional) {
            if(podeAtirar(tempoAtual)){
                pool.disparar(x, y, vx, vy, raio);
                proximoTiro = tempoAtual + tempoAdicional;
            }
        }
}
