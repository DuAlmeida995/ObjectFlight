package Mecanicas.bases;

public class ExplosaoBase {
    double explosaoComeco, explosaoFim;

    public ExplosaoBase(double explosaoComeco, double explosaoFim){
        this.explosaoComeco = explosaoComeco;
        this.explosaoFim = explosaoFim;
    }

    public double getexplosaoComeco() { return this.explosaoComeco;}
    public void setExplosaoComeco(double explosaoComeco) { this.explosaoComeco = explosaoComeco;}

    public double getexplosaoFim() { return this.explosaoFim;}
    public void setExplosaoFim(double explosaoFim) { this.explosaoFim = explosaoFim;}
}
