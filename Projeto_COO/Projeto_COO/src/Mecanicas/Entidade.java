package Mecanicas

public abstract class Entidade implements ObjetoJogo, Movivel {
    protected x, y;
    protected estado;
    protected radius;

    public Entidade(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @java.lang.Override
    public void update(long delta) {
        System.out.println("Entidade atualizada na posição ( " + x + ", " + y + ")");
    }

    public abstract void draw();

    @Override
    public double getX() { return x;}

    @Override
    public double getY() {
        return y;
    }

    @Override
    public abstract double getRadius() { return radius;}

    @Override
    public abstract void setVelocity(double vx, double vy);

    @Override
    public abstract void move(long delta);
}