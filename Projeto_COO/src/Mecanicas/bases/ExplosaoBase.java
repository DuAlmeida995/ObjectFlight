package Mecanicas.bases;

/* classe ExplosaoBase
 * Classe que serve de base para os atributos de explosões das entidades de inimigo e jogador do jogo.
*/

public class ExplosaoBase {

    private double explosaoComeco, explosaoFim; /* Tempo em que começa e termina as explosões */

    public ExplosaoBase(double explosaoComeco, double explosaoFim){
        this.explosaoComeco = explosaoComeco;
        this.explosaoFim = explosaoFim;
    }

    /* Funções getters e setters para o tempo de começo e fim da explosão. */

    /* atributos de explosão */
    public double getexplosaoComeco() { return this.explosaoComeco;}
    public double getexplosaoFim() { return this.explosaoFim;}
    public void setExplosaoComeco(double explosaoComeco) { this.explosaoComeco = explosaoComeco;}
    public void setExplosaoFim(double explosaoFim) { this.explosaoFim = explosaoFim;}
}
