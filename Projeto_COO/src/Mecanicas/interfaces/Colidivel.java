package Mecanicas.interfaces;

/* inferface Colidive 
 * Interface 'Colidivel' para ser utlizada na 'GameManager', nas verificações de colisões. 
*/

public interface Colidivel {
    double getX();
    double getY();
    double getRaio();
    
    boolean colideCom(Colidivel outro);
}