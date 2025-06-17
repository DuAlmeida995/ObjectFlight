package Mecanicas.projetil;

import java.util.List;
import java.util.ArrayList;

public class ProjetilPool {
    private List<Projetil> projeteis = new ArrayList<>();

    public void disparar(double x, double y, double vx, double vy, double raio) {
        projeteis.add(new Projetil(x, y, vx, vy, raio));
    }

    public void update(long deltaTime) {
        projeteis.removeIf(p -> p.estaForaDaTela());
        for (Projetil p : projeteis) {
            p.move(deltaTime);
        }
    }

    public void drawProjeteis() {
        for (Projetil p : projeteis) {
            p.draw();
        }
    }

    public List<Projetil> getProjeteis() {
        return projeteis;
    }
}
