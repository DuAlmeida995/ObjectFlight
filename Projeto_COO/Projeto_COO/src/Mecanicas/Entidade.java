package Mecanicas

public abstract class Entidade implements Movivel, Colidivel {
    protected int x, y;
    protected double vx,vy, radius;
    protected int estado;
    protected long nextShoot;
    protected long explosaoComeco;
    protected long explosaoFim;

    public static final int INACTIVATE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

    public Entidade(int x, int y, double vx, double vy, double radius) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.estado = ATIVO;
    }

    public boolean estaAtivo() { this.estado == ATIVO;}
    public void setExplosaoComeco(long tempo) {
        this.explosaoComeco = tempo;
    }

    public void setExplosaoFim(long tempo) {
        this.explosaoFim = tempo;
    }

    @java.lang.Override
    public void update(long delta);
    public abstract void draw();

    public int getEstado( return estado);

    public void setEstado(int estado) {
        if ( estado >= 0 || estado <= 2) this.estado = estado;
    }

    @Override
    public double getX() { return x;}

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getRadius() { return radius;}

    @Override
    public abstract void move(long delta);

    @Override
    public void onCollision(Colidivel outro) {
        if (estado == ATIVO) {
            estado = EXPLODING;
            setExplosaoComeco(System.currentTimeMillis());
            setExplosaoFim(System.currentTimeMillis() + 500);
        }
    }
}