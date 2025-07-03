package Mecanicas.bases;

import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;

/* class EntidadeInimigoBase
 * Classe que serve de base para as entidades de inimigo do jogo. 
*/

public class EntidadeInimigoBase implements Colidivel{

    EntidadeBase ent_base;   /* Objeto para administrar as propriedades de 'Entidade' */
    ExplosaoBase exp_base;   /* Objeto para administrar as propriedades de 'Explosao' */
    DisparadorBase ati_base; /* Objeto para administrar as propriedades de 'Atirador' */

    double angulo, v, vr;    /* Ângulo, velocidade e velocidade de rotação */

    public EntidadeInimigoBase(double x, double y, double v, double angulo, double raio, double vr, long proximoTiro){
        ent_base = new EntidadeBase(x, y, raio);
        exp_base = new ExplosaoBase(0, 0); 
        ati_base = new DisparadorBase(proximoTiro);
        this.angulo = angulo;
        this.v = v;
        this.vr = vr;
    }  

    /* Funções getters e setters para a posição, raio, velocidade, ângulo, velocidade de 
    rotação, estado da entidade e atributos de explosão. */
   
    /* posição */
    public double getX() { return ent_base.getX();}
    public void setX(double x) {ent_base.setX(x);}
    public double getY() { return ent_base.getY();}
    public void setY(double y) {ent_base.setY(y);}
    
    /* raio */
    public double getRaio() { return ent_base.getRaio();}
    public void setRaio(double raio) {ent_base.setRaio(raio);}

    /* velocidade */
    public double getV(){ return this.v;}
    public void setV(double v){ this.v = v;}

    /* ângulo */
    public double getAngulo(){ return this.angulo;}
    public void setAngulo(double angulo){ this.angulo = angulo;}

    /* velocidade de rotação */
    public double getVR() { return this.vr;}
    public void setVR(double vr) { this.vr = vr;}

    /* estado */
    public Estados getEstado() { return ent_base.getEstado();}
    public void setEstado(Estados estado) {ent_base.setEstado(estado);}

    /* atributos de explosão */
    public double getexplosaoComeco() { return exp_base.getexplosaoComeco();}
    public void setExplosaoComeco(double explosaoComeco) { exp_base.setExplosaoComeco(explosaoComeco);}
    public double getexplosaoFim() { return exp_base.getexplosaoFim();}
    public void setExplosaoFim(double explosaoFim) { exp_base.setExplosaoFim(explosaoFim);}

    /* atributos de atirador */

    public long getProximoTiro() { return ati_base.getProximoTiro();}
    public void setProximoTiro(long proximoTiro) { ati_base.setProximoTiro(proximoTiro);}

    /* Função que calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel outro){ return ent_base.colideCom(outro);}

    /* Função que atualiza o estado do inimigo quando este entra em contato com uma entidade colidível. */
    public void emExplosao() { ent_base.emExplosao(exp_base, 500);}
 
}
