package Mecanicas.interfaces;

public interface Colidivel {
    
    double getX();
    double getY();
    double getRaio();
    boolean colideCom(Colidivel outro);
}