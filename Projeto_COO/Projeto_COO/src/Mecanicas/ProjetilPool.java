package Mecanicas;
import Mecanicas.Projetil;
import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class ProjetilPool {
    private List<Projetil> projeteis = new ArrayList<>();

    public void disparar(double x, int y, double vx, double vy, double raio) {
        projeteis.add(new Projetil(x, y, vx, vy, raio));
    }

    public void update(long deltaTime) {
        projeteis.removeIf(p -> p.estaForaDaTela());
        for (Projetil p : projeteis) {
            p.move(deltaTime);
        }
    }

    public void desenharTodos(Graphics2D g) {
        for (Projetil p : projeteis) {
            p.draw(g);
        }
    }

    public List<Projetil> getProjeteis() {
        return projeteis;
    }
}
