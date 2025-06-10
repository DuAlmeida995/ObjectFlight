package Mecanicas

public interface Colidivel {
    void getX();
    void getY();
    void getRadius();
    void onCollission(Colidivel outro);
}