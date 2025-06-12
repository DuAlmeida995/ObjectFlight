package Mecanicas

public abstract class Entidade implements ObjetoJogo, Movivel {
    protected int x, y;
    protected int estado;
    protected double radius;
    protected long nextShoot;
    protected long explosaoComeco;
    protected long explosaoFim;

    public Entidade(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void setExplosaoComeco(long tempo) {
        this.explosaoComeco = tempo;
    }

    public void setExplosaoFim(long tempo) {
        this.explosaoFim = tempo;
    }

    @java.lang.Override
    public void update(long delta) {
        System.out.println("Entidade atualizada na posição ( " + x + ", " + y + ")");
    }

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
    public abstract double getRadius() { return radius;}

    @Override
    public abstract void move(long delta);
}