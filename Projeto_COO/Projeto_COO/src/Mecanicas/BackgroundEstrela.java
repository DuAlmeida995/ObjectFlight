import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EstrelaFundo {
    private List<Estrela> camada1;
    private List<Estrela> camada2;

    public EstrelaFundo() {
        camada1 = gerarEstrelas(20, 0.070); // primeiro plano
        camada2 = gerarEstrelas(50, 0.045); // segundo plano
    }

    private List<Estrela> gerarEstrelas(int quantidade, double velocidadeY) {
        List<Estrela> estrelas = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < quantidade; i++) {
            double x = rand.nextInt(800); // largura da tela
            double y = rand.nextInt(600); // altura da tela
            estrelas.add(new Estrela(x, y, velocidadeY));
        }

        return estrelas;
    }

    public void update(long delta) {
        for (Estrela e : camada1) e.update(delta);
        for (Estrela e : camada2) e.update(delta);
    }

    public void draw(Graphics2D g2d) {
        for (Estrela e : camada2) e.draw(g2d); // segundo plano primeiro
        for (Estrela e : camada1) e.draw(g2d); // primeiro plano depois
    }
}