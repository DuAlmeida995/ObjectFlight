import Jogo.GameManager;

public class Main {
    public static void main(String[] args) {
        // Inicializa o gerenciador de jogo
        GameManager game = new GameManager();

        game.init();
        // Inicia o loop principal
        game.loop();
    }
}