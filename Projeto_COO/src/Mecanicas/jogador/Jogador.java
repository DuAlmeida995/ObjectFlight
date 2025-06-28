package Mecanicas.jogador;

import java.awt.Color;
import java.util.List;

import Jogo.GameLib;

import Mecanicas.bases.EntidadeBase;
import Mecanicas.bases.ExplosaoBase;
import Mecanicas.bases.MovimentoBase;
import Mecanicas.bases.VidaBase;
import Mecanicas.bases.AtiradorBase;

import Mecanicas.constantes.Estados;
import static Mecanicas.constantes.Estados.*;

import Mecanicas.interfaces.Colidivel;

import Mecanicas.projetil.*;

/* class Jogador
 * Classe que implementa a entidade de jogador no jogo.
*/

public class Jogador implements Colidivel{

    AtiradorBase ati_base;  /* Objeto para administrar as propriedades de 'Atirador'  */
    EntidadeBase ent_base;  /* Objeto para administrar as propriedades de 'Entidade'  */
    ExplosaoBase exp_base;  /* Objeto para administrar as propriedades de 'Explosao'  */
    MovimentoBase mov_base; /* Objeto para administrar as propriedades de 'Movimento' */
    VidaBase vid_base;      /* Objeto para administrar as propriedades de 'Vida'      */
    
    private boolean tiroTriploAtivo = false;
    private long tempoFimTiroTriplo = 0;

    public Jogador(double x, double y, long tempoAtual, int vidaMaxima) {
        ent_base = new EntidadeBase(x,y,12); /* Raio -> 12 */ 
        mov_base = new MovimentoBase(0.25, 0.25); /* Velocidade no eixo X e eixo Y -> 0.25 */
        exp_base = new ExplosaoBase(0, 0);
        ati_base = new AtiradorBase(tempoAtual + 100); /* Cadência dos disparos dos projéteis de 100 ms */
        vid_base = new VidaBase(vidaMaxima);
    }

    /* Funções getters e setters de posição e um getter para raio, velocidade no eixo X e eixo Y, estado e lista de projéteis.*/
    
    /* posição */
    public double getX() { return ent_base.getX();}
    public double getY() { return ent_base.getY();}
    public void setX(double x) { ent_base.setX(x);}
    public void setY(double y) { ent_base.setY(y);}

    /* raio */
    public double getRaio() { return ent_base.getRaio();}
        
    /* velocidade no eixo X e eixo Y */
    public double getVX(){ return mov_base.getVX();}
    public double getVY(){ return mov_base.getVY();}

    /* estado */
    public Estados getEstado(){ return ent_base.getEstado();}

    /* 'pool' de projéteis */
    public List<Projetil> getProjetilPool(){ return ati_base.getProjeteis();}

    /* ------------------------------------------------------------- Mecânicas do Jogador ------------------------------------------------------------- 
     * 
     * (1) Vida;
     * (2) Power Up's; 
     * (3) Colisão;
     * (4) Atirar;
     * (5) Atualização e desenho;
     * 
    */
    
    /* (1) funções básicas de controle da vida do Jogador. */

    public boolean estaMorto() { return vid_base.estaMorto();}
    public boolean estaInvencivel() { return vid_base.estaInvencivel();}
    public void reduzir() { vid_base.reduzir();}
    public void resetar() { vid_base.resetar();}


    /* (2) funções de ativações dos power up's de: Invencibilidade e TiroTriplo */

    public void ativarInvencibilidadePorPowerUp(int duracao) {
        vid_base.ativarInvencibilidadeTemporaria(duracao);
    }
    public void ativarTiroTriplo(int duracao) {
        tiroTriploAtivo = true;
        tempoFimTiroTriplo = System.currentTimeMillis() + duracao;
    }


    /* (3) funções de execução da lógica de colisão e eventual explosão do jogador. */

    /* calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel outro){ return ent_base.colideCom(outro);}

    /* atualiza os atributos do jogador caso este exploda. */
    public void emExplosao() { ent_base.emExplosao(exp_base, 2000); }


    /* (4) função que faz com que o jogador atire um projétil ou projéteis (dado o power up), ativando a função de atirar da classe ProjetilPool. */
    
    public void disparar(long tempoAtual){
        if(ati_base.podeDisparar(tempoAtual)){
            /* Tiro principal */
            ati_base.disparar(ent_base.getX(), ent_base.getY() - ent_base.getRaio() * 2, 0.0, -1.0, 0);
            if (tiroTriploAtivo) {
                /* Tiros laterais, ativado pelo power-up de tiro triplo */
                ati_base.disparar(ent_base.getX(), ent_base.getY(), -0.2, -1.0, 0);
                ati_base.disparar(ent_base.getX(), ent_base.getY(), 0.2, -1.0, 0);
            }
            ati_base.setProximoTiro(tempoAtual + 100);
        }
    }


    /* (5) funções de atualizações do Jogador e desenho (também de seus projéteis) ao longo do tempo de jogo. */

    /* atualiza os atributos do jogador ao longo do tempo de jogo em duas condições:
    *  (i) caso esse tenha explodido e, passado o tempo da explosão, renasce;
    *  (ii) caso, ao calcular o input do usuário, o jogador não saia da tela do jogo.
    *  Além disso, atualiza a lógica de invencibilidade dos frames para o cálculo de redução de vida do jogador.*/
    public void update(long tempoAtual){
        /* condição (i) */
        if(ent_base.getEstado() == EXPLODING){
            if(tempoAtual > exp_base.getexplosaoFim()){
                ent_base.setEstado(ACTIVE);
            }
        }
        /* condição (ii) */
        if(ent_base.getX() < 0.0) ent_base.setX(0);
		if(ent_base.getX() >= GameLib.WIDTH) ent_base.setX(GameLib.WIDTH - 1);
		if(ent_base.getY() < 25.0) ent_base.setY(25.0);
		if(ent_base.getY() >= GameLib.HEIGHT) ent_base.setY(GameLib.HEIGHT - 1);
    
        vid_base.updateInvencibilidade();
    }

    /* atualiza os atributos dos projéteis do jogador. */
    public void updateProjeteis(long deltaTime){ ati_base.updateProjeteis(deltaTime);}

    /* desenha a entidade Jogador, seus projéteis na tela e sua barra de vida. */
    public void draw() {
        if (ent_base.getEstado() == EXPLODING) {
            double alpha = (System.currentTimeMillis() - exp_base.getexplosaoComeco()) / 
                        (double) (exp_base.getexplosaoFim() - exp_base.getexplosaoComeco());
            GameLib.drawExplosion(ent_base.getX(), ent_base.getY(), alpha);
        } else {
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(ent_base.getX(), ent_base.getY(), ent_base.getRaio());
        }
        ati_base.drawProjeteisJogador();
        vid_base.drawVidaJogador();
    }
}
