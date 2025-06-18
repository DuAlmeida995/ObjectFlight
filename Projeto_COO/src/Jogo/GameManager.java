package Jogo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Mecanicas.constantes.Estados.ACTIVE;
import static Mecanicas.constantes.Estados.INACTIVATE;

import java.awt.Color;
import Mecanicas.background.BackgroundEstrela;
import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.EntidadeInimigo;
import Mecanicas.jogador.*;
import Mecanicas.projetil.*;
import Mecanicas.inimigos.*;


public class GameManager {
    private boolean running;
    private long tempoAtual;

    private Jogador jogador;
    private List<EntidadeInimigo> inimigos;
    private ProjetilPool projetilInimigo;
    private BackgroundEstrela fundo;

    private long nextSpawnEnemy1;
    private long nextSpawnEnemy2;

    private static final long ENEMY1_SPAWN_DELAY = 500;   // intervalo em ms
    private static final long ENEMY2_SPAWN_DELAY = 3000;

    public GameManager() {
        this.running         = true;
        this.tempoAtual         = System.currentTimeMillis();
        this.jogador         = new Jogador(GameLib.WIDTH/2, (int)(GameLib.HEIGHT*0.9));
        this.inimigos        = new ArrayList<>();
        this.projetilInimigo = new ProjetilPool();
        this.fundo           = new BackgroundEstrela();
        this.nextSpawnEnemy1 = tempoAtual + 2000;
        this.nextSpawnEnemy2 = tempoAtual + 7000;
    }

    public void init() {
        GameLib.initGraphics();
    }

    /*                                                  
     * Loop do jogo que realiza quatro principais funções:
     * (1) detecta colisões do jogador com inimigos, do jogador com os projéteis do inimigo;
     * (2) atualiza o estado dos elementos na tela ao passo que o tempo passa;
     * (3) captura e calcula, conforme a entrada do usuário pelo teclado, mudanças no estado
     * do jogador;
     * (4) desenha a cena apartir do momento atual. 
     * (5) espera um intervalo de tempo.                                                   
     */

    public void loop() {
        while (running) {

            long delta = System.currentTimeMillis() - tempoAtual;      
            tempoAtual = System.currentTimeMillis();

            detectCollisions();
            updateAll(delta,tempoAtual);
            handleInput(delta);
            renderAll();
            busyWait(tempoAtual + 3);
        }

        System.exit(0);
    }

    /* (1) Função que detecta possíveis colisões das Entidades colidíveis no jogo. */

    private void detectCollisions() {
        /* Jogador x Inimigos */
        if (jogador.getEstado() == ACTIVE) {
            for (EntidadeInimigo e : inimigos) {
                if (jogador.colideCom((Colidivel)e)) {
                    jogador.emColisao();
                }
            }
            /* Jogador x Projéteis inimigos */
            for (Projetil p : projetilInimigo.getProjeteis()) {
                if (jogador.colideCom(p)) {
                    jogador.emColisao();
                }
            }
        }
        /* Projéteis do jogador x inimigos */
        for (Projetil p : jogador.getProjetilPool()) {
            for (EntidadeInimigo e : inimigos) {
                if(e.getEstado() == ACTIVE){
                    if (e.colideCom(p)) {
                        e.emColisao();
                    }
                }
            }
        }
    }

    /* Função auxiliar utilizada na função uptadeAll() que calcula o momento em que os inimigos
     * devem nascer no jogo.
     */

    private void spawnEnemies(long now) {
        /* Tipo 1: circulares, caindo a intervalos curtos */
        if (now > nextSpawnEnemy1) {
            inimigos.add(new Inimigo1(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.20 + Math.random() * 0.15, (3 * Math.PI) / 2, 9.0, 0.0));
            nextSpawnEnemy1 = now + ENEMY1_SPAWN_DELAY;
        }
        /* Tipo 2: diamantes, mais espaçados */
        if (now > nextSpawnEnemy2) {
            //inimigos.add(new Inimigo2(x, -10, projetilInimigo));
            nextSpawnEnemy2 = now + ENEMY2_SPAWN_DELAY;
        }
    }

    /* (2) Função que realiza a atualização dos estados das Entidades do jogo, como a posição do 
     * elemento na tela.
     */

    private void updateAll(long delta, long nowTime) {
        /* Verifica se inimigos devem nascer. */
        spawnEnemies(nowTime);
        /* Atualiza o fundo. */
        fundo.update(delta);
        /* Atualiza os inimigos. */
        for (EntidadeInimigo e : inimigos) e.update(delta);
        /* Atualiza o jogador (caso saia da tela ou tenha finalizado a sua explosão). */
        jogador.update(nowTime);
        jogador.updateProjeteis(delta);
        /* Atualiza os projéteis disparados. */
        //projetilJogador.update(delta);
        projetilInimigo.update(delta);
        /* Remover inimigos inativos */
        Iterator<EntidadeInimigo> it = inimigos.iterator();
        while (it.hasNext()) {
            EntidadeInimigo e = it.next();
            if (e.getEstado() == INACTIVATE) it.remove();
        }
    }

    /* (3) Função que realiza a captura dos comados inseridos no teclado pelo usuário, atualizando assim
     * a posição do jogador e os tiros na tela e, possivelmente, realizando o fechamento do jogo.
     */

    private void handleInput(long delta) {
        if(jogador.getEstado() == ACTIVE){
            if(GameLib.iskeyPressed(GameLib.KEY_UP)) jogador.setY(jogador.getY() - delta * jogador.getVY());
		    if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) jogador.setY(jogador.getY() + delta * jogador.getVY());
		    if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) jogador.setX(jogador.getX() - delta * jogador.getVX());
		    if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) jogador.setX(jogador.getX() + delta  * jogador.getVX());
            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                jogador.atirar(tempoAtual);
            }
            if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
            running = false;
            }
        }
    }

    /* (4) Função que desenha os elementos na tela conforme o estado atual do jogo. */

    private void renderAll() {
        /* Seta a cor de fundo padrão e o tamanho da janela. */
        GameLib.setColor(Color.BLACK);
        GameLib.fillRect(0, 0, GameLib.WIDTH, GameLib.HEIGHT);
        /* Desenha o fundo do jogo. */
        fundo.drawBackground();
        fundo.drawForeground();
        /* Desenha o jogador. */
        jogador.draw();
        /* Desenha os inimigos. */
        for (EntidadeInimigo e : inimigos) {
            e.draw();
        }
        /* Desenha os projéteis. */
        //projetilJogador.desenharTodos();
        //projetilInimigo.desenharTodos();
        /* Apresenta o frame. */
        GameLib.display();
    }

    private void busyWait(long target) {
        while (System.currentTimeMillis() < target) Thread.yield();
    }
}
