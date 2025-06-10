package Mecanicas

public interface Explodivel {
    void startExplosion(long now);
    void isExploding();
    void getExplosionProgress(long now);
}