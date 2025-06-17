package Mecanicas.bases;

import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;

import static Mecanicas.constantes.Estados.*;

public class EntidadeInimigoBase implements Colidivel{

    EntidadeBase ent_base;
    ExplosaoBase exp_base;
    AtiradorBase ati_base;

    double angulo, v, vr;

    public EntidadeInimigoBase(double x, double y, double v, double angulo, double raio, double vr){
        ent_base = new EntidadeBase(x, y, raio);
        exp_base = new ExplosaoBase(0, 0);
        ati_base = new AtiradorBase();
        this.v = v;
        this.angulo = angulo;
        this.vr = vr;
    }  

    public double getX() { return ent_base.getX();}
    public void setX(double x) {ent_base.setX(x);}

    public double getY() { return ent_base.getY();}
    public void setY(double y) {ent_base.setY(y);}
    
    public double getRaio() { return ent_base.getRaio();}
    public void setRaio(double raio) {ent_base.setRaio(raio);}

    public double getV(){ return this.v;}
    public void setV(double v){ this.v = v;}

    public double getAngulo(){ return this.angulo;}
    public void setAngulo(double angulo){ this.angulo = angulo;}

    public double getVR() { return this.vr;}
    public void setVR(double vr) { this.vr = vr;}

    public Estados getEstado() { return ent_base.getEstado();}
    public void setEstado(Estados estado) {ent_base.setEstado(estado);}

    public double getexplosaoComeco() { return exp_base.getexplosaoComeco();}
    public void setExplosaoComeco(double explosaoComeco) { exp_base.setExplosaoComeco(explosaoComeco);}

    public double getexplosaoFim() { return exp_base.getexplosaoFim();}
    public void setExplosaoFim(double explosaoFim) { exp_base.setExplosaoFim(explosaoFim);}

    public boolean estaAtivo(){ return ent_base.estaAtivo();}

    public boolean colideCom(Colidivel outro){ return ent_base.colideCom(outro);}

    public void emColisao() {
        if (ent_base.getEstado() == ACTIVE) {
                ent_base.setEstado(EXPLODING);
                exp_base.setExplosaoComeco(System.currentTimeMillis());
                exp_base.setExplosaoFim(exp_base.getexplosaoComeco() + 500);
        }
    }

    public void atirar(long tempoAtual){
        ati_base.atirar(ent_base.getX(), ent_base.getY(), 0, 
        this.v, ent_base.getRaio(), tempoAtual, 500);
    }
}
