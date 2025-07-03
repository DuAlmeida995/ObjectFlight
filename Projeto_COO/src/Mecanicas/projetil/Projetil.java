package Mecanicas.projetil;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.bases.EntidadeBase;
import Mecanicas.bases.MovimentoBase;

import Mecanicas.constantes.Estados;

import Mecanicas.interfaces.Colidivel;

/* class Projetil
 * Classe que serve para gerenciar o projétil no jogo.
*/

public class Projetil implements Colidivel{

    EntidadeBase ent_base;  /* Objeto para administrar as propriedades de 'Entidade'  */
    MovimentoBase mov_base; /* Objeto para administrar as propriedades de 'Movimento' */

    public Projetil(double x, double y, double vx, double vy, double raio) {
        ent_base = new EntidadeBase(x,y, raio);
        mov_base = new MovimentoBase(vx, vy);
    }

    /* Funções getters e setter de estado e getter de posição e raio.*/

    /* posicão */
    public double getX() { return ent_base.getX();}
    public double getY() { return ent_base.getY();}

    /* raio */
    public double getRaio() { return ent_base.getRaio();}

    /* estado */
    public Estados getEstados() { return ent_base.getEstado();}
    public void setEstado(Estados estados) { ent_base.setEstado(estados);}

    
    /* ------------------------------------------------------------- Mecânicas do Projetil ------------------------------------------------------------- 
     * 
     * (1) Fora da tela;
     * (2) Colisão;
     * (3) Atualização e desenho.
     *  
    */


    /* (1) função para verificar se o projétil saiu dos limites do jogo, afim de ser removido da 'pool' de projéteis. */

    public boolean estaForaDaTela() {
        return (ent_base.getX() < 0 || ent_base.getX() > GameLib.WIDTH || ent_base.getY() < 0 || ent_base.getY() > GameLib.HEIGHT);
    }


    /* (2) função de execução da lógica de colisão. */

    /* calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel outro) { return ent_base.colideCom(outro);}


    /* (3)  funções de atualizações do projétil e desenho (de acordo com a entidade disparante) ao longo do tempo de jogo. */

    /* atualiza os atributos do projétil ao longo do tempo de jogo. */
    public void update(long delta){
        ent_base.setX(ent_base.getX() + mov_base.getVX() * delta);
        ent_base.setY(ent_base.getY() + mov_base.getVY() * delta);    
    }

    /* desenha o projétil no estilo do jogador. */
    public void drawJogador() {
        GameLib.setColor(Color.GREEN);
		GameLib.drawLine(ent_base.getX(), ent_base.getY() - 5, ent_base.getX(), ent_base.getY() + 5);
		GameLib.drawLine(ent_base.getX() - 1, ent_base.getY() - 3, ent_base.getX() - 1, ent_base.getY() + 3);
		GameLib.drawLine(ent_base.getX() + 1, ent_base.getY() - 3, ent_base.getX() + 1, ent_base.getY() + 3);
    }

    /* desenha o projétil no estilo do inimigo. */
    public void drawInimigo(){
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(ent_base.getX(), ent_base.getY(), ent_base.getRaio());
    }
}