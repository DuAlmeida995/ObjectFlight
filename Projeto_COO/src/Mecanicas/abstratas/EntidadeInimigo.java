package Mecanicas.abstratas;

import Mecanicas.interfaces.Colidivel;

public abstract class EntidadeInimigo extends Entidade {

    public EntidadeInimigo(double x, double y, double vx, double vy, double rio){
        super(x, y, vx, vy, rio);
    }

    public void draw(){};
    public void update(long delta){};
    public void move(long delta){};

    public void emColisao(Colidivel outro) {
        if (estado == ACTIVE) {
                estado = EXPLODING;
                explosaoComeco = now;
                explosaoFim = explosaoComeco + 500;
        }
    }
}
