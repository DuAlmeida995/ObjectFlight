import java.awt.Color;
import java.awt.Graphics2D;

public class Estrela {
    protected double velocidade;;
    protected double[] estrelas;
    protected double count;
    public Estrela (double velocidade, int quantidade ) {
        this.velocidade = velocidade;
        estrelas = new double[quantidade]
    }

    @Override
    public void update() {
        //mover();
        if (y > 600) { // Se saiu da tela, reseta
            y = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, largura, altura);
    }
}