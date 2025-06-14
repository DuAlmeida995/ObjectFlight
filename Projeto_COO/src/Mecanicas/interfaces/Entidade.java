package Mecanicas.interfaces;

public abstract class Entidade implements Colidivel {
    
    protected double x, y, vx , vy, raio;
    protected int estado;
    protected long explosaoComeco, explosaoFim;

    public static final int INACTIVATE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

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

    public void setX(double x) {this.x = x;}

    public void setY(double y) {this.y = y;}

    public double getX() { return x;}

    public double getY() { return y;}

    public double getSpeedX() { return vx;}
    
    public double getSpeedY() { return vy;}

    public double getRadius() { return raio;}

    public void setExplosaoFim(long tempo) { this.explosaoFim = tempo;}
    
    public void setExplosaoComeco(long tempo) { this.explosaoComeco = tempo;}

    public boolean estaAtivo() {  return this.estado == ACTIVE;}

    public void setEstado(int estado) { this.estado = estado;}

}