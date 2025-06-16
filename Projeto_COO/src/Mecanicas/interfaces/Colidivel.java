package Mecanicas.interfaces;

public interface Colidivel {
    double getX();
    double getY();

    double getRaio();

    void emColisao(Colidivel outro);

    /**
     * Aqui está a lógica de colisão circular:
     * mede a distância entre centros e compara com a soma de raios.
     */
    default boolean colideCom(Colidivel outro) {
        double dx = getX() - outro.getX();
        double dy = getY() - outro.getY();
        double distancia = Math.hypot(dx, dy);
        return distancia < (getRaio() + outro.getRaio()) * 0.8;
    }
}