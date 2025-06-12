package MecanicasAdd commentMore actions

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Projetil {

    private List<Projetil> projeteis = new ArrayList<>();

    public void disparar(int x, int y) {
        projeteis.add(new Projetil(x, y));
    }

    public void update() {
        List<Projetil> paraRemover = new ArrayList<>();

        for (Projetil p : projeteis) {
            p.update();
            if (p.isOffScreen()) {
                paraRemover.add(p);
            }
        }

        projeteis.removeAll(paraRemover);
    }

    public void draw(Graphics2D g2d) {
        for (Projetil p : projeteis) {
            p.draw(g2d);
        }
    }

    public List<Projetil> getProjeteis() {
        return projeteis;
    }