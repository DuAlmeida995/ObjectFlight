package Jogo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.Color;
import Mecanicas.background.BackgroundEstrela;
import Mecanicas.interfaces.Entidade;
import Mecanicas.interfaces.EntidadeInimigo;
import Mecanicas.jogador.*;
import Mecanicas.projetil.*;


public class GameManager {
    private boolean running;
    private long nowTime;

    private Jogador jogador;
    private List<EntidadeInimigo> inimigos;
    private ProjetilPool projetilJogador;
    private ProjetilPool projetilInimigo;
    private BackgroundEstrela fundo;

    public static final int INACTIVATE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

    public GameManager() {
        this.running         = true;
        this.nowTime         = System.currentTimeMillis();
        this.jogador         = new Jogador(GameLib.WIDTH/2, (int)(GameLib.HEIGHT*0.9));
        this.inimigos        = new ArrayList<>();
        this.projetilJogador = new ProjetilPool();
        this.projetilInimigo = new ProjetilPool();
        this.fundo           = new BackgroundEstrela();
    }

    public void init() {
        GameLib.initGraphics();
    }

    public void loop() {
        while (running) {

            long delta = System.currentTimeMillis() - nowTime;      

            nowTime = System.currentTimeMillis();

            handleInput(delta);
            updateAll(delta);
            detectCollisions();
            renderAll();
            busyWait(nowTime + 3);
        }
        System.exit(0);
    }

    private void handleInput(long delta) {

        if(jogador.estaAtivo()){
            if(GameLib.iskeyPressed(GameLib.KEY_UP)) jogador.setY(jogador.getY() - delta * jogador.getSpeedY());
		    if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) jogador.setY(jogador.getY() + delta * jogador.getSpeedY());
		    if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) jogador.setX(jogador.getX() - delta * jogador.getSpeedX());
		    if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) jogador.setX(jogador.getX() + delta  * jogador.getSpeedX());
            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                jogador.atirar(nowTime, projetilJogador);
            }
            if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
            running = false;
            }
        }
    }

    private void updateAll(long delta) {
        
        fundo.update(delta);
        for (EntidadeInimigo e : inimigos) e.update(delta);
        jogador.update();
        projetilJogador.update(delta);
        projetilInimigo.update(delta);

        /* Remover inimigos inativos */

        Iterator<EntidadeInimigo> it = inimigos.iterator();
        while (it.hasNext()) {
            Entidade e = it.next();
            if (!e.estaAtivo()) it.remove();
        }
    }

    private void detectCollisions() {

        /* Jogador x Inimigos */

        if (jogador.estaAtivo()) {
            for (Entidade e : inimigos) {
                if (jogador.collidesWith(e)) {
                    jogador.emColisao(e);
                    e.emColisao(jogador);
                }
            }

            /* Jogador x Projéteis inimigos */

            for (Projetil p : projetilInimigo.getProjeteis()) {
                if (jogador.collidesWith(p)) {
                    jogador.emColisao(p);
                    p.emColisao(jogador);
                }
            }
        }

        /* Projéteis do jogador x inimigos */

        for (Projetil p : projetilJogador.getProjeteis()) {
            if (!p.estaAtivo()) continue;
            for (Entidade e : inimigos) {
                if (p.collidesWith(e)) {
                    e.emColisao(p);
                    p.emColisao(e);
                }
            }
        }
    }

    private void renderAll() {
        GameLib.setColor(Color.BLACK);
        GameLib.fillRect(0, 0, GameLib.WIDTH, GameLib.HEIGHT);

        // desenha fundo
        fundo.drawBackground();
        fundo.drawForeground();

        // desenha jogador
        jogador.draw();

        // desenha inimigos
        for (EntidadeInimigo e : inimigos) {
            e.draw();
        }

        // desenha projéteis
        projetilJogador.desenharTodos();
        projetilInimigo.desenharTodos();

        // apresenta o frame
        GameLib.display();
    }

    private void busyWait(long target) {
        while (System.currentTimeMillis() < target) Thread.yield();
    }
}
