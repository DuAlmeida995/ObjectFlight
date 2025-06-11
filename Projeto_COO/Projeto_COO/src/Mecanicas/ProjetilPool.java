package Mecanicas

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class ProjectilePool {

    private List<ProjectileBase> projeteis = new ArrayList<>();

    public void disparar(int x, int y) {
        projeteis.add(new ProjectileBase(x, y));
    }

    public void update() {
        List<ProjectileBase> paraRemover = new ArrayList<>();

        for (ProjectileBase p : projeteis) {
            p.update();
            if (p.isOffScreen()) {
                paraRemover.add(p);
            }
        }

        projeteis.removeAll(paraRemover);
    }

    public void draw(Graphics2D g2d) {
        for (ProjectileBase p : projeteis) {
            p.draw(g2d);
        }
    }

    public List<ProjectileBase> getProjeteis() {
        return projeteis;
    }