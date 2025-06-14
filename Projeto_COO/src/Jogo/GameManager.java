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
import Mecanicas.inimigos.*;


public class GameManager {
    private boolean running;
    private long nowTime;

    private Jogador jogador;
    private List<EntidadeInimigo> inimigos;
    private ProjetilPool projetilJogador;
    private ProjetilPool projetilInimigo;
    private BackgroundEstrela fundo;

    private long nextSpawnEnemy1;
    private long nextSpawnEnemy2;

    private static final long ENEMY1_SPAWN_DELAY = 500;   // intervalo em ms
    private static final long ENEMY2_SPAWN_DELAY = 3000;


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
        this.nextSpawnEnemy1 = nowTime + 2000;
        this.nextSpawnEnemy2 = nowTime + 7000;
    }

    public void init() {
        GameLib.initGraphics();
        inimigos.add(new Inimigo1(100, 0, 0.2, Math.PI/2, 0.0));

        inimigos.add(new Inimigo2(200, -20, projetilInimigo));
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

    private void spawnEnemies(long now) {
        // Tipo 1: circulares, caindo a intervalos curtos
        if (now > nextSpawnEnemy1) {
            double x = Math.random() * (GameLib.WIDTH - 18) + 9; // evita borda
            inimigos.add(new Inimigo1(x, -10, 0.2, Math.PI/2, 0.0));
            nextSpawnEnemy1 = now + ENEMY1_SPAWN_DELAY;
        }

        // Tipo 2: diamantes, mais espaçados
        if (now > nextSpawnEnemy2) {
            double x = Math.random() > 0.5
                    ? GameLib.WIDTH * 0.2
                    : GameLib.WIDTH * 0.8;
            inimigos.add(new Inimigo2(x, -10, projetilInimigo));
            nextSpawnEnemy2 = now + ENEMY2_SPAWN_DELAY;
        }
    }

    private void updateAll(long delta) {
        long now = System.currentTimeMillis();
        spawnEnemies(now);
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
