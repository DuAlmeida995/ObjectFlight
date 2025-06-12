public class ProjetilPool {
    private List<Projetil> projeteis = new ArrayList<>();

    public void disparar(double x, double y, double vx, double vy, double raio) {
        projeteis.add(new Projetil(x, y, vx, vy, raio));
    }

    public void atualizar(long deltaTime) {
        projeteis.removeIf(p -> p.estaForaDaTela());
        for (Projetil p : projeteis) {
            p.mover(deltaTime);
        }
    }

    public void desenharTodos(Graphics2D g) {
        for (Projetil p : projeteis) {
            p.desenhar(g);
        }
    }

    public List<Projetil> getProjeteis() {
        return projeteis;
    }
}
