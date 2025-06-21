package Mecanicas.projetil;

import java.util.List;
import java.util.ArrayList;

/* class ProjetilPool
 * Classe que gerencia uma lista de projéteis das entidades do jogo.
*/

public class ProjetilPool {
    private List<Projetil> projeteis = new ArrayList<>(); /* Lista da Coleção Java para administrar os projéteis */

    /* Função getter para a lista de projéteis da entidade. */
    public List<Projetil> getProjeteis() {
        return projeteis;
    }

    /* Função para realizar o disparo de um projétil, isto é, cria um objeto 'Projetil' e adiciona a lista. */
    public void disparar(double x, double y, double vx, double vy, double raio) {
        projeteis.add(new Projetil(x, y, vx, vy, raio));
    }

    /* Função atualizar os atributos dos projéteis guardado na 'pool' ao longo do tempo de jogo. */
    public void update(long deltaTime) {
        projeteis.removeIf(p -> p.estaForaDaTela());
        for (Projetil p : projeteis) {
            p.update(deltaTime);
        }
    }

    /* Função que desenha os projéteis do jogador. */
    public void drawProjeteisJogador() {
        for (Projetil p : projeteis) {
            p.drawJogador();
        }
    }

    /* Função que desenha os projéteis do inimigo. */
    public void drawProjeteisInimigo(){
        for (Projetil p : projeteis){
            p.drawInimigo();
        }
    }
}
