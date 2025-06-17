package Mecanicas.bases;

import static Mecanicas.constantes.Estados.ACTIVE;

import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;

public class EntidadeBase{
    
    /* Declara atributos básicos das entidades: 
     * (double) coordenadas x y;
     * (int) o estado atual da entidade;
     */
    protected double x, y, raio;
    protected Estados estado;

    /* Construtor da classe Entidade. */
    public EntidadeBase(double x, double y, double raio) {
        this.x = x;
        this.y = y;
        this.raio = raio;
        this.estado = Estados.ACTIVE;
    }

    /* Funções getters e setters para a posição, raio e o estado da entidade.
     */
    
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

    public boolean estaAtivo(){ return this.estado == ACTIVE;}

    public boolean colideCom(Colidivel o) {
        double dx = getX() - o.getX();
        double dy = getY() - o.getY();
        double distancia = Math.hypot(dx, dy);
        return distancia < (getRaio() + o.getRaio()) * 0.8;
    }
}