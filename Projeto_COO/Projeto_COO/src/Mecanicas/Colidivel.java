double getX();
double getY();
double getRadius();
void onCollision(Colidivel outro);

/**
 * Aqui está a lógica de colisão circular:
 * mede a distância entre centros e compara com a soma de raios.
 */
default boolean collidesWith(Colidivel o) {
    double dx = getX() - o.getX();
    double dy = getY() - o.getY();
    double distancia = Math.hypot(dx, dy);
    return distancia < (getRadius() + o.getRadius()) * 0.8;
}