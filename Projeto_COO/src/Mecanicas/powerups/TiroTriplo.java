package Mecanicas.powerups;

import java.awt.Color;
import Jogo.GameLib;
import Mecanicas.bases.EntidadeBase;
import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.PowerUp;
import Mecanicas.jogador.Jogador;

import static Mecanicas.constantes.Estados.*;

public class TiroTriplo implements Colidivel, PowerUp {

    private EntidadeBase ent_base;
    private Jogador jogador; 
    private boolean tiroTriploAtivo;
    private long tempoTiroTriplo;
    private long tempoTotal;

    public TiroTriplo(double x, double y) {
        this.ent_base = new EntidadeBase(x, y, 10); // raio 10
    }

    public double getX(){ return ent_base.getX();}
    public double getY(){ return ent_base.getY();}
    public double getRaio(){ return ent_base.getRaio();}

    public void ativar(Jogador jogador, long tempoTiroTriplo){
        tiroTriploAtivo = true;
        this.tempoTiroTriplo = tempoTiroTriplo;
        this.tempoTotal = tempoTiroTriplo;
        this.jogador = jogador;
        aplicar();
    }
 
    public void aplicar(){
        jogador.setTiroTriplo(tiroTriploAtivo);
    }

    public boolean estaAtivo() { return tiroTriploAtivo; }

    public boolean colideCom(Colidivel o){ return ent_base.colideCom(o);}

    public void update(long delta) {
        if (ent_base.getY() > GameLib.HEIGHT + 10) ent_base.setEstado(EXPLODING);
        
        if (ent_base.getEstado() == ACTIVE){
            ent_base.setY(ent_base.getY() + delta * 0.15); // velocidade vertical
        }

        if(tiroTriploAtivo){
            tempoTiroTriplo --;
            if(tempoTiroTriplo <= 0){
                tiroTriploAtivo = false;
                aplicar();
                ent_base.setEstado(INACTIVATE);
            }
        }
    }   

    public void draw() {
        if (ent_base.getEstado() == ACTIVE) {
            GameLib.setColor(Color.ORANGE);
            GameLib.drawDiamond(getX(), getY(), getRaio());
        }

        if(tiroTriploAtivo){
            float porcent = (float)tempoTiroTriplo/tempoTotal;
            GameLib.setColor(Color.DARK_GRAY);
            GameLib.fillRect(80.0, GameLib.HEIGHT - 40, 100.0, 5);

            GameLib.setColor(Color.WHITE);
            GameLib.fillRect(30 + (100 * porcent) / 2, GameLib.HEIGHT - 40, 100 * porcent, 5);
        }   
    }

    public Estados getEstado() { return ent_base.getEstado(); }

    public void desativar() { ent_base.setEstado(EXPLODING); }
}