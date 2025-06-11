import java.awt.Color;
import java.awt.Graphics2D;

public class Star extends Entidade {

    public Star(int x, int y) {
        super(x, y);
        this.velocidadeY = 1; // Move devagar
        this.largura = 2;
        this.altura = 2;
    }

    @Override
    public void update() {
        mover();
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