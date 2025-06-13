package Mecanicas;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameManager {
    private boolean running;
    private long lastTime;

    private Jogador player;
    private List<Entidade> inimigos;
    private ProjetilPool projetilAliado;
    private ProjetilPool projetilInimigo;
    private BackgroundEstrela fundo;

    public static final int INACTIVATE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

    public GameManager() {
        this.running         = true;
        this.lastTime        = System.currentTimeMillis();
        this.player          = new Jogador(GameLib.WIDTH/2, (int)(GameLib.HEIGHT*0.9));
        this.inimigos        = new ArrayList<>();
        this.projetilAliado  = new ProjetilPool();
        this.projetilInimigo = new ProjetilPool();
        this.fundo           = new BackgroundEstrela();
    }

    public void init() {
        GameLib.initGraphics();
        // Exemplo: adicionar inimigos iniciais
        inimigos.add(new Inimigo1(100, -20, 0.2, Math.PI/2, 0.0));
        inimigos.add(new Inimigo2(200, -20, projetilInimigo));
    }

    public void loop() {
        while (running) {
            long now   = System.currentTimeMillis();
            long delta = now - lastTime;
            lastTime   = now;

            handleInput(now);
            updateAll(delta);
            detectCollisions();
            renderAll();
            busyWait(now + 3);
        }
        System.exit(0);
    }

    private void handleInput(long now) {
        double dx = 0, dy = 0;
        if (GameLib.iskeyPressed(GameLib.KEY_LEFT ))  dx = -player.getSpeedX();
        if (GameLib.iskeyPressed(GameLib.KEY_RIGHT))  dx =  player.getSpeedX();
        if (GameLib.iskeyPressed(GameLib.KEY_UP   ))  dy = -player.getSpeedY();
        if (GameLib.iskeyPressed(GameLib.KEY_DOWN ))  dy =  player.getSpeedY();
        player.setVelocity(dx, dy);

        if (GameLib.iskeyPressed(GameLib.KEY_CONTROL) && player.canShoot(now)) {
            player.atirar(now, projetilAliado);
        }
        if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
            running = false;
        }
    }

    private void updateAll(long delta) {
        fundo.update(delta);
        player.update(delta);
        for (Entidade e : inimigos) e.update(delta);
        projetilAliado.update(delta);
        projetilInimigo.update(delta);
        // remover inativos
        Iterator<Entidade> it = inimigos.iterator();
        while (it.hasNext()) {
            Entidade e = it.next();
            if (!e.estaAtivo()) it.remove();
        }
    }

    private void detectCollisions() {
        // Player vs inimigos
        if (player.estaAtivo()) {
            for (Entidade e : inimigos) {
                if (player.collidesWith(e)) {
                    player.onCollision(e);
                    e.onCollision(player);
                }
            }
            // Player vs projéteis inimigos
            for (Projetil p : projetilInimigo.getProjeteis()) {
                if (player.collidesWith(p)) {
                    player.onCollision(p);
                    p.onCollision(player);
                }
            }
        }
        // Projéteis aliados vs inimigos
        for (Projetil p : projetilAliado.getProjeteis()) {
            if (!p.estaAtivo()) continue;
            for (Entidade e : inimigos) {
                if (p.collidesWith(e)) {
                    e.onCollision(p);
                    p.onCollision(e);
                }
            }
        }
    }

    private void renderAll() {
        // fundo estrelas
        fundo.drawBackground(GameLib.getGraphics());
        fundo.drawForeground(GameLib.getGraphics());

        // player
        player.draw(GameLib.getGraphics());

        // inimigos
        for (Entidade e : inimigos) e.draw(GameLib.getGraphics());

        // projeteis
        projetilAliado.drawAll(GameLib.getGraphics());
        projetilInimigo.drawAll(GameLib.getGraphics());

        GameLib.display();
    }

    private void busyWait(long target) {
        while (System.currentTimeMillis() < target) Thread.yield();
    }
}
