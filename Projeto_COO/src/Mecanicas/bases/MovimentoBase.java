package Mecanicas.bases;

/* classe MovimentoBase
 * Classe que serve de base para as entidades que se movem em ambos eixos, isto é, jogador e projéteis.
*/

public class MovimentoBase {

    private double vx, vy; /* Velocidade no eixo x e y */

    public MovimentoBase(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }
    
    /* Funçoes getters e setters para a velocidade no eixo x e y*/
    
    /* velocidade */
    public double getVX(){ return this.vx;}
    public double getVY(){ return this.vy;}
    public void setVX(double vx){ this.vx = vx;}
    public void setVY(double vy){ this.vy = vy;}

}
