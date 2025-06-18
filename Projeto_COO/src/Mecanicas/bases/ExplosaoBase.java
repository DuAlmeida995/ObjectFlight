package Mecanicas.bases;

/* class ExplosaoBase
 * Classe que serve de base para os atributos de explosões das entidades de inimigo e jogador do jogo.
*/

public class ExplosaoBase {

    double explosaoComeco, explosaoFim; /* Tempo em que começa e termina as explosões */

    public ExplosaoBase(double explosaoComeco, double explosaoFim){
        this.explosaoComeco = explosaoComeco;
        this.explosaoFim = explosaoFim;
    }

    /* Funções getters e setters para o tempo de começo e fim da explosão. */
    public double getexplosaoComeco() { return this.explosaoComeco;}
    public void setExplosaoComeco(double explosaoComeco) { this.explosaoComeco = explosaoComeco;}
    public double getexplosaoFim() { return this.explosaoFim;}
    public void setExplosaoFim(double explosaoFim) { this.explosaoFim = explosaoFim;}

}
