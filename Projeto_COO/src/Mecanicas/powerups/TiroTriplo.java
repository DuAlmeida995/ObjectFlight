package Mecanicas.powerups;

import java.awt.Color;
import Jogo.GameLib;
import Mecanicas.bases.EntidadeBase;
import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;
import Mecanicas.jogador.Jogador;

import static Mecanicas.constantes.Estados.*;

public class TiroTriplo extends EntidadeBase implements Colidivel {
    private Estados estado = ACTIVE;
    private Jogador jogador; 
    private boolean tiroTriploAtivo;
    private long tempoTiroTriplo;
    private long tempoTotal;

    public TiroTriplo(double x, double y) {
        super(x, y, 10); // raio 10
    }

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


    public void update(long delta) {
        if (getY() > GameLib.HEIGHT + 10) estado = EXPLODING;
        
        if (estado == ACTIVE){
            setY(getY() + delta * 0.15); // velocidade vertical
        }

        if(tiroTriploAtivo){
            tempoTiroTriplo --;
            if(tempoTiroTriplo <= 0){
                tiroTriploAtivo = false;
                aplicar();
                estado = INACTIVATE;
            }
        }
    }   

    public void draw() {
        if (estado == ACTIVE) {
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

    public Estados getEstado() { return estado; }

    public void desativar() { estado = EXPLODING; }
}