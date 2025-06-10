package Mecanicas

public abstract class Entidade implements ObjetoJogo, Movivel, Colidivel {
    protected x, y;

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
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public abstract double getRadius();

    @Override
    public abstract void onCollision(Colidivel outro);

    @Override
    public abstract void setVelocity(double vx, double vy);

    @Override
    public abstract void move(long delta);
}