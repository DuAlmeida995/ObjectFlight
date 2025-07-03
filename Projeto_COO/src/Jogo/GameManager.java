package Jogo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Jogo.carregadores.*;

import static Mecanicas.constantes.Estados.ACTIVE;
import static Mecanicas.constantes.Estados.INACTIVATE;

import java.awt.Color;

import Mecanicas.bases.DisparadorBase;
import Mecanicas.chefes.*;
import Mecanicas.interfaces.*;
import Mecanicas.jogador.*;
import Mecanicas.projetil.*;
import Mecanicas.inimigos.*;
import Mecanicas.powerups.*;
import Mecanicas.background.BackgroundEstrela;

public class GameManager {
    private boolean running;
    private long tempoAtual;
    private int faseAtual;
    private int numFase;
    private String [] fases;
    private boolean chefeDerrotado;

    private Jogador jogador;
    private int vidaJogador;

    private Chefe chefe;
    private DisparadorBase projeteisInimigos;

    private BackgroundEstrela fundo;
    
    private CarregadorJogo car_jogo;
    private CarregadorFase car_fase;
    
    
    private List<EntidadeInimigo> inimigos;

    private long tempoSpawnInimigo1;
    private double posicaoXInimigo1;
    private double posicaoYInimigo1;
    private long INIMIGO1_DELAY_SPAWN;   

    private long tempoSpawnInimigo2;
    private double posicaoXInimigo2;
    private double posicaoYInimigo2;
    private long INIMIGO2_DELAY_SPAWN;   
    private int contadorInimigo2;

    private int vidaMaximaChefe;
    private long tempoSpawnChefe; 
    private double posicaoXChefe;
    private double posicaoYChefe;
    private boolean spawnouChefe;

    private List<PowerUp> powerUPs;
    private long proximoPowerUp;
    private long proximoPowerUpTiroTriplo;          


    public GameManager() {
        GameLib.initGraphics();

        this.tempoAtual                = System.currentTimeMillis();
        this.chefeDerrotado            = false;
        this.spawnouChefe              = false;
        this.INIMIGO1_DELAY_SPAWN      = 500;
        this.INIMIGO2_DELAY_SPAWN      = 500;
        this.contadorInimigo2          = 0;
         
        this.car_jogo                  = new CarregadorJogo();
        this.car_fase                  = new CarregadorFase();

        carregarJogo("Configuracao/jogo.txt");

        this.running                   = true;
        this.jogador                   = new Jogador(GameLib.WIDTH/2, (int)(GameLib.HEIGHT*0.9), tempoAtual, vidaJogador);
        this.powerUPs                  = new ArrayList<>();
        this.proximoPowerUp            = System.currentTimeMillis() + 1000;
        this.proximoPowerUpTiroTriplo  = System.currentTimeMillis() + 1000;
        this.inimigos                  = new ArrayList<>();
        this.projeteisInimigos         = new DisparadorBase(tempoAtual + 500);
        this.fundo                     = new BackgroundEstrela();      
    }

    private void carregarJogo(String arquivo){
        car_jogo.carregar(arquivo);
        JogoInfo jogoInfo = car_jogo.getJogoInfo();
        
        vidaJogador = jogoInfo.getVidaJogador();
        numFase     = jogoInfo.getNumFase();
        faseAtual   = 1;
        fases       = jogoInfo.getFases();

        novaFase(faseAtual);

    }

    private void carregarDadosFase(String arquivo){
        car_fase.carregar(arquivo, tempoAtual);
        ArrayList<SpawnInfo> spawnInfo = car_fase.getSpawnInfo();
        Iterator<SpawnInfo> iterator = spawnInfo.iterator(); 
        while (iterator.hasNext()) {
            SpawnInfo s = iterator.next();
            switch (s.getTipo()) {
                case "INIMIGO 1":
                    this.tempoSpawnInimigo1 = s.getTempoSpawn();
                    this.posicaoXInimigo1 = s.getX();
                    this.posicaoYInimigo1 = s.getY();
                    iterator.remove();
                    break;
                case "INIMIGO 2":
                    this.tempoSpawnInimigo2 = s.getTempoSpawn();
                    this.posicaoXInimigo2 = s.getX();
                    this.posicaoYInimigo2 = s.getY();
                    iterator.remove();
                    break;
                case "CHEFE":
                    this.vidaMaximaChefe = s.getVidaMaxima();
                    this.tempoSpawnChefe = s.getTempoSpawn();
                    this.posicaoXChefe = s.getX();
                    this.posicaoYChefe = s.getY();
                    iterator.remove();
                    break;
                default:
                    break;
            }
        }
    }

    private void novaFase(int numeroFase){
        faseAtual = numeroFase;
        spawnouChefe = false;
        chefeDerrotado = false;
        carregarDadosFase(fases[faseAtual-1]);
    }

    /*  ------------------------------------------------------------- Loop do Jogo -------------------------------------------------------------                                             
     * 
     * Loop do jogo que realiza quatro principais funções:
     * (1) detecta colisões do jogador com inimigos, do jogador com os projéteis do inimigo;
     * (2) atualiza o estado dos elementos na tela ao passo que o tempo passa;
     * (3) captura e calcula, conforme a entrada do usuário pelo teclado, mudanças no estado
     * do jogador;
     * (4) desenha a cena apartir do momento atual. 
     * (5) espera um intervalo de tempo.
     *                                                    
    */

    public void loop() {
        while (running) {

            long delta = System.currentTimeMillis() - tempoAtual;      
            tempoAtual = System.currentTimeMillis();

            detectarColisoes();
            
            updateAll(delta,tempoAtual);
            
            handleInput(delta);
            
            renderAll();
            
            busyWait(tempoAtual + 3);
        }

        System.exit(0);
    }

    /* (1) detecta possíveis colisões das Entidades colidíveis no jogo. */

    private void detectarColisoes() {
        if (jogador.getEstado() == ACTIVE && !jogador.estaInvencivel()) {
            /* Jogador x Inimigos */
            for (EntidadeInimigo e : inimigos) {
                if (jogador.colideCom((Colidivel)e)) {
                    jogador.tomaDano();
                }  
            }
            /* Jogador x Projéteis inimigos */
            for (Projetil p : projeteisInimigos.getProjeteis()) {
                if (jogador.colideCom(p)) {
                    jogador.tomaDano();
                }
            }

            if(spawnouChefe){
                if(jogador.colideCom((Colidivel) chefe)){
                    jogador.tomaDano();
                }
            }
        }
        /* Projéteis do jogador x inimigos */
        for (Projetil p : jogador.getProjetilPool()) {
            for (EntidadeInimigo e : inimigos) {
                if(e.getEstado() == ACTIVE){
                    if (e.colideCom(p)) {
                        e.emExplosao();
                    }
                }
            }
            if(spawnouChefe && !chefe.estaInvencivel()){
                if(chefe.colideCom(p)){
                    chefe.reduzir();
                    if(chefe.estaMorto()){
                        chefe.emExplosao();
                    }
                }
            }
        }
        
        for (PowerUp pu : powerUPs) {
            if(pu.getEstado() == ACTIVE){
                if (jogador.colideCom((Colidivel) pu)) {
                    pu.ativar(jogador, 5000); // 300 frames (~5s a 60fps)
                    pu.desativar();
                }
            }
        }
    }


    /* (2) realiza a atualização dos estados das Entidades do jogo, como a posição do 
     * elemento na tela e o spawn de inimigos e powerups.
    */

    private void spawnInvencibilidade(long tempoAtual) {
        if (tempoAtual > proximoPowerUp) {
            double x = Math.random() * (GameLib.WIDTH - 20) + 10;  // Posição X aleatória dentro da tela
            double y = -10; // Spawnar acima da tela, para descer depois
            powerUPs.add(new Invencibilidade(x, y));
            proximoPowerUp = tempoAtual + 15000; // Spawn a cada 15 segundos (ajuste como quiser)
        }
    }

    private void spawnPowerUpTiroTriplo(long tempoAtual) {
        if (tempoAtual > proximoPowerUpTiroTriplo) {
            double x = Math.random() * (GameLib.WIDTH - 20) + 10;
            double y = -10;
            powerUPs.add(new TiroTriplo(x, y));
            proximoPowerUpTiroTriplo = tempoAtual + 15000; // novo spawn a cada 30s
        }
    }

    private void gerenciarSpawns(long tempoAtual){
        if(!spawnouChefe){
            if (tempoAtual > tempoSpawnInimigo1) {
                inimigos.add(new Inimigo1(Math.random() * (GameLib.WIDTH - 20.0) + posicaoXInimigo1, posicaoYInimigo1 *(-1.0), 0.20 + Math.random() * 0.15, (3 * Math.PI) / 2, 9.0, 0.0, tempoAtual));
                tempoSpawnInimigo1 = tempoAtual + INIMIGO1_DELAY_SPAWN;
            }
            /* Tipo 2: diamantes, mais espaçados */
            if (tempoAtual > tempoSpawnInimigo2) {
                inimigos.add(new Inimigo2(posicaoXInimigo2, posicaoYInimigo2 *(-1.0), 0.42, (double)(3 * Math.PI) / 2, 12.0, 0.0, tempoAtual));
                contadorInimigo2++;
                if(contadorInimigo2 < 10){
                    tempoSpawnInimigo2 = tempoAtual + 150;
                }else{
                    contadorInimigo2 = 0;
                    posicaoXInimigo1 = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
                    tempoSpawnInimigo2 = (long) (tempoAtual + INIMIGO2_DELAY_SPAWN + Math.random() * 3000);        
                }
            }

            if(tempoAtual > tempoSpawnChefe){
                spawnouChefe = true;

                if(faseAtual == 1){
                    chefe = new Chefe1(posicaoXChefe, posicaoYChefe *(-1.0),  0.10 + Math.random() * 0.15, (3 * Math.PI) / 2, 50.0, 0.0, tempoAtual, vidaMaximaChefe);
                }
                if(faseAtual == 2){
                    chefe = new Chefe2(posicaoXChefe, posicaoYChefe * (-1.0),  0.10 + Math.random() * 0.15, (3 * Math.PI) / 2, 50.0, 0.0, tempoAtual, vidaMaximaChefe);
                }
            }
        }
    }

    private void updateAll(long delta, long tempoAtual) {
        /* verifica se inimigos devem nascer. */
        spawnInvencibilidade(tempoAtual);
        spawnPowerUpTiroTriplo(tempoAtual);
        gerenciarSpawns(tempoAtual);
        
        
        /* atualiza o fundo. */
        fundo.update(delta);
        /* atualiza os inimigos. */
        projeteisInimigos.updateProjeteis(delta);
       
        /* atualiza o jogador (caso saia da tela ou tenha finalizado a sua explosão). */
        jogador.update(tempoAtual);
        jogador.updateProjeteis(delta);
        
        if(spawnouChefe){
            chefe.update(delta, jogador.getX(), jogador.getY());
            chefe.disparar(projeteisInimigos, tempoAtual);
            if(chefe.getEstados() == INACTIVATE){
                chefeDerrotado = true;
            }
        }

        if(chefeDerrotado && faseAtual < numFase){   
            novaFase(faseAtual+1);
        }
        
        Iterator<PowerUp> itPU = powerUPs.iterator();
        while (itPU.hasNext()) {
            PowerUp p = itPU.next();
            p.update(delta);
            if(p.getEstado() == INACTIVATE) itPU.remove();
        } 

        /* remove inimigos inativos */
        Iterator<EntidadeInimigo> it = inimigos.iterator();
        while (it.hasNext()) {
            EntidadeInimigo e = it.next();
            e.update(delta);
            e.disparar(projeteisInimigos, tempoAtual);
            if (e.getEstado() == INACTIVATE) it.remove();
        }    
    }


    /* (3) realiza a captura dos comandos inseridos no teclado pelo usuário, atualizando assim
     * a posição do jogador e os tiros na tela e, possivelmente, realizando o fechamento do jogo.
     */

    private void handleInput(long delta) {
        if(jogador.getEstado() == ACTIVE){
            if(GameLib.iskeyPressed(GameLib.KEY_UP)) jogador.setY(jogador.getY() - delta * jogador.getVY());
		    if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) jogador.setY(jogador.getY() + delta * jogador.getVY());
		    if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) jogador.setX(jogador.getX() - delta * jogador.getVX());
		    if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) jogador.setX(jogador.getX() + delta  * jogador.getVX());
            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                jogador.disparar(tempoAtual);
            }
            if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
            running = false;
            }
        }
    }


    /* (4) desenha os elementos na tela conforme o estado atual do jogo. */

    private void renderAll() {
        /* Seta a cor de fundo padrão e o tamanho da janela. */
        GameLib.setColor(Color.BLACK);
        GameLib.fillRect(0, 0, GameLib.WIDTH, GameLib.HEIGHT);
        /* Desenha o fundo do jogo. */
        fundo.drawBackground();
        fundo.drawForeground();
        /* Desenha o jogador. */
        /* Desenha os inimigos. */
        for (EntidadeInimigo e : inimigos) e.draw();
        projeteisInimigos.drawProjeteisInimigo();
        for (PowerUp p : powerUPs) p.draw();
        jogador.draw();
        if(spawnouChefe) chefe.draw();
        /* Apresenta o frame. */
        GameLib.display();
    }


    /* (5) espera um intervalo de tempo. */
    private void busyWait(long target) {
        while (System.currentTimeMillis() < target) Thread.yield();
    }
}
