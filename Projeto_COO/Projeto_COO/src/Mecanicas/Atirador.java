package Mecanicas

public interface Atirador {
    void canShoot(long now);
    void shoot();
    void getShootNextTime();
}