package Mecanicas.projetil;

import java.util.List;
import java.util.ArrayList;

/* class ProjetilPool
 * Classe que gerencia uma lista de projéteis das entidades do jogo.
*/

public class ProjetilPool {
    private List<Projetil> projeteis = new ArrayList<>(); /* Lista da Coleção Java para administrar os projéteis */

    /* Função getter para a 'pool' de projéteis da entidade. */

    /* 'pool' de projéteis */
    public List<Projetil> getProjeteis() {
        return projeteis;
    }

    /* ------------------------------------------------------------- Mecânicas do ProjetilPool ------------------------------------------------------------- 
     * 
     * (1) Disparo;
     * (2) Atualização e desenho.
     *  
    */


    /* (1) função para realizar o disparo de um projétil, isto é, cria um objeto 'Projetil' e adiciona a 'pool'. */

    public void disparar(double x, double y, double vx, double vy, double raio) {
        projeteis.add(new Projetil(x, y, vx, vy, raio));
    }


    /* (2) funções de atualizações do da 'pool' de projéteis e desenho (de acordo com a entidade disparante) ao longo do tempo de jogo. */

    /* atualiza os atributos da 'pool' de projéteis ao longo do tempo de jogo em duas condições:
     * (i) caso o projétil individual tenha saido da tela, é removido da lista;
     * (ii) caso contrário, atualiza os projéteis da lista. */
    public void update(long delta) {
        /* condição (i) */
        projeteis.removeIf(p -> p.estaForaDaTela());
        /* condição (ii) */
        for (Projetil p : projeteis) {
            p.update(delta);
        }
    }

    /* desenha os projéteis do jogador. */
    public void drawProjeteisJogador() {
        for (Projetil p : projeteis) {
            p.drawJogador();
        }
    }

    /* desenha os projéteis do inimigo. */
    public void drawProjeteisInimigo(){
        for (Projetil p : projeteis){
            p.drawInimigo();
        }
    }
}
