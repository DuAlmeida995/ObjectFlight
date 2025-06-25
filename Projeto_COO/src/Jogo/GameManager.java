package Jogo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Mecanicas.constantes.Estados.ACTIVE;
import static Mecanicas.constantes.Estados.INACTIVATE;

import java.awt.Color;
import Mecanicas.background.BackgroundEstrela;
import Mecanicas.bases.AtiradorBase;
import Mecanicas.chefes.*;
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
    //private Chefe1 chefe1;
    private Chefe2 chefe2;
    private AtiradorBase projeteisInimigos;

    private BackgroundEstrela fundo;

    private final int QUANT_VIDA_JOGADOR = 5;

    private long nextSpawnEnemy1;
    private long nextSpawnEnemy2;
    private long spawnChefe; 
    private boolean spawnouChefe = false;

    private int contadorInimigo2 = 0;
    private double inimigo2SpawnX = GameLib.WIDTH * 0.20; 

    private static final long ENEMY1_SPAWN_DELAY = 500;   
    private static final long ENEMY2_SPAWN_DELAY = 3000;

    public GameManager() {
        this.running           = true;
        this.tempoAtual        = System.currentTimeMillis();
        this.jogador           = new Jogador(GameLib.WIDTH/2, (int)(GameLib.HEIGHT*0.9), tempoAtual, QUANT_VIDA_JOGADOR);
        this.inimigos          = new ArrayList<>();
        //this.chefe1            = new Chefe1(INACTIVATE, GameLib.WIDTH/2, -10.0, 0.10 + Math.random() * 0.15, (3 * Math.PI) / 2, 50.0, 0.0, tempoAtual, 100);
        this.chefe2            = new Chefe2(INACTIVATE, GameLib.WIDTH/2, -10.0, 0.10 + Math.random() * 0.15, (3 * Math.PI) / 2, 50.0, 0.0, tempoAtual, 100);
        this.projeteisInimigos = new AtiradorBase(tempoAtual + 500);
        this.fundo             = new BackgroundEstrela();
        this.nextSpawnEnemy1   = tempoAtual + 2000;
        this.nextSpawnEnemy2   = tempoAtual + 7000;
        this.spawnChefe        = tempoAtual + 10000;
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
        if (jogador.getEstado() == ACTIVE && !jogador.estaInvencivel()) {
            /* Jogador x Inimigos */
            for (EntidadeInimigo e : inimigos) {
                if (jogador.colideCom((Colidivel)e)) {
                    jogador.reduzir();
                    if(jogador.estaMorto()){
                        jogador.emColisao();
                        jogador.resetar();
                    }
                }  
            }
            /* Jogador x Projéteis inimigos */
            for (Projetil p : projeteisInimigos.getProjeteis()) {
                if (jogador.colideCom(p)) {
                    jogador.reduzir();
                    if(jogador.estaMorto()){
                        jogador.emColisao();
                        jogador.resetar();
                    }
                }
            }
            if(spawnouChefe){
                if(jogador.colideCom(chefe2)){
                    jogador.reduzir();
                    if(jogador.estaMorto()){
                        jogador.emColisao();
                        jogador.resetar();
                    }
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
            if(chefe2.getEstado() == ACTIVE && !chefe2.estaInvencivel()){
                if(chefe2.colideCom(p)){
                    chefe2.reduzir();
                    if(chefe2.estaMorto()){
                        chefe2.emColisao();
                    }
                }
            }
        }
    }

    /* Função auxiliar utilizada na função uptadeAll() que calcula o momento em que os inimigos
     * devem nascer no jogo.
     */

    private void spawnChefe(long tempoAtual){
        if(tempoAtual > spawnChefe && !spawnouChefe){
            chefe2.setEstado(ACTIVE);
            spawnouChefe = true;
        }

    }

    private void spawnEnemies(long tempoAtual) {
        /* Tipo 1: circulares, caindo a intervalos curtos */
        if (tempoAtual > nextSpawnEnemy1) {
            inimigos.add(new Inimigo1(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.20 + Math.random() * 0.15, (3 * Math.PI) / 2, 9.0, 0.0, tempoAtual));
            nextSpawnEnemy1 = tempoAtual + ENEMY1_SPAWN_DELAY;
        }
        /* Tipo 2: diamantes, mais espaçados */
        if (tempoAtual > nextSpawnEnemy2) {
            inimigos.add(new Inimigo2(inimigo2SpawnX, -10.0, 0.42, (double)(3 * Math.PI) / 2, 12.0, 0.0, tempoAtual));
            contadorInimigo2++;
            if(contadorInimigo2 < 10){
                nextSpawnEnemy2 = tempoAtual + 150;
            }else{
                contadorInimigo2 = 0;
                inimigo2SpawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
                nextSpawnEnemy2 = (long) (tempoAtual + ENEMY2_SPAWN_DELAY + Math.random() * 3000);        
            }
       }
    }

    /* (2) Função que realiza a atualização dos estados das Entidades do jogo, como a posição do 
     * elemento na tela.
     */

    private void updateAll(long delta, long tempoAtual) {
        /* Verifica se inimigos devem nascer. */
        if(!spawnouChefe) spawnEnemies(tempoAtual);
        spawnChefe(tempoAtual);
        /* Atualiza o fundo. */
        fundo.update(delta);
        /* Atualiza os inimigos. */
        projeteisInimigos.updateProjeteis(delta);
        for (EntidadeInimigo e : inimigos) {
            e.update(delta, 0,0);
            e.disparar(projeteisInimigos, tempoAtual);
        }
        /* Atualiza o jogador (caso saia da tela ou tenha finalizado a sua explosão). */
        jogador.update(tempoAtual);
        jogador.updateProjeteis(delta);

        chefe2.update(delta, jogador.getX(), jogador.getY());

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
        /* Desenha os inimigos. */
        for (EntidadeInimigo e : inimigos) {
            e.draw();
        }
        projeteisInimigos.drawProjeteisInimigo();
        
        jogador.draw();
        chefe2.draw();

        /* Apresenta o frame. */
        GameLib.display();
    }

    private void busyWait(long target) {
        while (System.currentTimeMillis() < target) Thread.yield();
    }
}
