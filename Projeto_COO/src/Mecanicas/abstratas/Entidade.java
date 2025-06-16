package Mecanicas.abstratas;

import Mecanicas.interfaces.Colidivel;

public abstract class Entidade implements Colidivel {
    
    /* Declara atributos básicos das entidades: 
     * (double) coordenadas x y, velocidades nos eixos xy, raio da entidade;
     * (int) o estado atual da entidade;
     * (long) o tempo em que começa e termina a explosão.
     */
    protected double x, y, vx , vy, raio;
    protected int estado;
    protected long explosaoComeco, explosaoFim;

    /* Declaração de constantes que corresponde aos possíveis estados da entidade.*/
    protected static final int INACTIVATE = 0;
    protected static final int ACTIVE = 1;
    protected static final int EXPLODING = 2;

    /* Declaração de uma constante do tempo atual utilizada nas funções. */
    protected static final long now = System.currentTimeMillis();


    /* Construtor da classe Entidade. */
    public Entidade(double x, double y, double vx, double vy, double raio) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.raio = raio;
        this.estado = ACTIVE;
        this.explosaoComeco = 0;
        this.explosaoFim = 0;
    }

    /* Funções getters e setters para a posição, velocidade e raio, getter para os tempos
     * da explosão e setter para o estado da entidade.
     */
    
    /* posição */
    public double getX() { return x;}
    public double getY() { return y;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}

    /* velocidade */
    public double getVX() { return vx;}
    public double getVY() { return vy;}
    public void setVX(double vx) { this.vx = vx;}
    public void setVY(double vy) { this.vy = vy;}
    
    /* raio */
    public double getRaio() { return raio;}
    public void setRaio(double raio) { this.raio = raio;}

    /* tempo da explosão */
    public void setExplosaoFim(long tempo) { this.explosaoFim = tempo;}
    public void setExplosaoComeco(long tempo) { this.explosaoComeco = tempo;}

    /* estado da entidade*/
    public void setEstado(int estado) { this.estado = estado;}
    
    /* Função para verificar se a entidade está ativa. */
    public boolean estaAtivo() {  return this.estado == ACTIVE;}
}