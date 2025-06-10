package Mecanicas

public abstract class ProjetilBase extends Entidade {
    public enum ProjectileState { ACTIVE, INACTIVE }

    protected ProjectileState state = ProjectileState.ACTIVE;
    protected double speed;
    protected double directionX;
    protected double directionY;

    public ProjectileBase(double x, double y, double speed, double directionX, double directionY) {
        super(x, y);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    @Override
    public void update(long delta) {
        if (state == ProjectileState.INACTIVE) return;
        x += directionX * speed * delta;
        y += directionY * speed * delta;
        if (isOffScreen()) {
            state = ProjectileState.INACTIVE;
        }
    }

    @Override
    public void onCollision(Colidivel outro) {
        if (state == ProjectileState.ACTIVE) {
            state = ProjectileState.INACTIVE;
            System.out.println("Projétil colidiu e foi desativado");
        }
    }

    protected boolean isOffScreen() {
        return x < 0 || x > 800 || y < 0 || y > 600;
    }

    // Métodos adicionais
    public boolean isActive() {
        return state == ProjectileState.ACTIVE;
    }

    public void reset(double newX, double newY) {
        this.x = newX;
        this.y = newY;
        this.state = ProjectileState.ACTIVE;
    }
}
