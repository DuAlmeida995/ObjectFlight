package Mecanicas.bases;

import static Mecanicas.constantes.Estados.EXPLODING;

import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;

/* class EntidadeBase
 * Classe que serve de base para as demais entidades do jogo. 
*/

public class EntidadeBase{
    
    private double x, y, raio; /* Coordenadas x e y, e raio (tamanho) da entidade */
    private Estados estado; /* Estado atual da entidade (INACTIVATE, ACTIVE ou EXPLODING) */

    public EntidadeBase(double x, double y, double raio) {
        this.x = x;
        this.y = y;
        this.raio = raio;
        this.estado = Estados.ACTIVE; 
    }

    /* Funções getters e setters para a posição, raio e o estado da entidade. */
    
    /* posição */
    public double getX() { return x;}
    public double getY() { return y;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}

    /* raio */
    public double getRaio(){ return this.raio;}
    public void setRaio(double raio){ this.raio = raio;}

    /* estado da entidade*/
    public Estados getEstado() { return this.estado;}
    public void setEstado(Estados estado) { this.estado = estado;}

    /* Função que calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel o) {
        double dx = getX() - o.getX();
        double dy = getY() - o.getY();
        double distancia = Math.hypot(dx, dy);
        return distancia < (getRaio() + o.getRaio()) * 0.8;
    }

    /* Função que atualiza os atributos das entidades, entrando em estado de explosão. */
    public void emExplosao(ExplosaoBase exp_base, long tempoExplosao){
        this.estado = EXPLODING;
        exp_base.setExplosaoComeco(System.currentTimeMillis());
        exp_base.setExplosaoFim(exp_base.getexplosaoComeco() + tempoExplosao);
    }

}