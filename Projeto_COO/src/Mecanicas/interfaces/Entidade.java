package Mecanicas.interfaces;

public abstract class Entidade implements Movivel, Colidivel {
    
    protected double x, y, vx , vy, raio;
    protected int estado;
    protected long explosaoComeco, explosaoFim;

    public static final int INACTIVATE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

    public Entidade(double x, double y, double vx, double vy, double radius) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.raio = radius;
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

    public int getEstado() { return estado;}

    public void setExplosaoFim(long tempo) { this.explosaoFim = tempo;}
    
    public boolean estaAtivo() {  return this.estado == ACTIVE;}
    
    public void setExplosaoComeco(long tempo) {
        this.explosaoComeco = tempo;
    }

    public abstract void update(long delta);

    public abstract void draw();


    public void setEstado(int estado) {
        if ( estado >= 0 || estado <= 2) this.estado = estado;
    }

  //  public abstract void move(long delta);

    @Override
    public void onCollision(Colidivel outro) {
        if (estado == ACTIVE) {
            estado = EXPLODING;
            setExplosaoComeco(System.currentTimeMillis());
            setExplosaoFim(System.currentTimeMillis() + 500);
        }
    }
}