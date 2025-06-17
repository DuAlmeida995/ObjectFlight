package Mecanicas.bases;

public class MovimentoBase {

    double vx, vy;

    public MovimentoBase(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }
    
    public double getVX(){ return this.vx;}
    public double getVY(){ return this.vy;}
    public void setVX(double vx){ this.vx = vx;}
    public void setVY(double vy){ this.vy = vy;}

}
