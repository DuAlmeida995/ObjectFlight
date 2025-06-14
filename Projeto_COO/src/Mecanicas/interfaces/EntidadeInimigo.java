package Mecanicas.interfaces;

public abstract class EntidadeInimigo extends Entidade {
    public EntidadeInimigo(double x, double y, double vx, double vy, double radius){
        super(x, y, vx, vy, radius);
    }

    public void draw(){};
    public void update(long delta){};
    public void move(long delta){};
}
