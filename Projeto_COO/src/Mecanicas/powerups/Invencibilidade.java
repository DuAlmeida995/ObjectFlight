package Mecanicas.powerups;

import static Mecanicas.constantes.Estados.ACTIVE;
import static Mecanicas.constantes.Estados.EXPLODING;
import static Mecanicas.constantes.Estados.INACTIVATE;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.bases.EntidadeBase;
import Mecanicas.constantes.Estados;
import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.PowerUp;
import Mecanicas.jogador.Jogador;

public class Invencibilidade implements Colidivel, PowerUp {

    private EntidadeBase ent_base;
    private Jogador jogador;
    private long tempoInvencibilidade;
    private long tempoTotal;
    private boolean invencibilidadeAtivo;
    

    public Invencibilidade(double x, double y) {
        this.ent_base = new EntidadeBase(x, y, 10.0); // raio = 10.0
        ent_base.setEstado(ACTIVE);
    }

    public void ativar(Jogador jogador, long tempoInvencibilidade){
        this.jogador = jogador;
        invencibilidadeAtivo = true;
        this.tempoInvencibilidade = tempoInvencibilidade;
        this.tempoTotal = tempoInvencibilidade;
        aplicar();
    }

    public void aplicar(){
        if(invencibilidadeAtivo) jogador.setCor(Color.WHITE);
        else jogador.setCor(Color.BLUE);
        jogador.getVidaBase().setInvencibilidade(invencibilidadeAtivo);
    }   

    public void update(long delta) {
        if (ent_base.getY() > GameLib.HEIGHT) ent_base.setEstado(EXPLODING);

        if (ent_base.getEstado() == ACTIVE) {
            ent_base.setY(ent_base.getY() + 0.2 * delta);
        }

        if(invencibilidadeAtivo){
            tempoInvencibilidade --;
            if(tempoInvencibilidade <= 0){
                invencibilidadeAtivo = false;
                aplicar();
                ent_base.setEstado(INACTIVATE);
            }
        }
    }

    public void draw() {
        if (ent_base.getEstado() == ACTIVE) {
            GameLib.setColor(Color.WHITE);
            GameLib.drawCircle(ent_base.getX(), ent_base.getY(), ent_base.getRaio());
        }

        if(invencibilidadeAtivo){
            float porcent = (float)tempoInvencibilidade/tempoTotal;

            GameLib.setColor(Color.DARK_GRAY);
            GameLib.fillRect(80.0, GameLib.HEIGHT - 60, 100.0, 5);

            GameLib.setColor(Color.WHITE);
            GameLib.fillRect(30 + (100 * porcent) / 2, GameLib.HEIGHT - 60, 100 * porcent, 5);
        }
    }

    public boolean colideCom(Colidivel outro) { return ent_base.colideCom(outro);}

    public void desativar() { ent_base.setEstado(EXPLODING); }

    // Implementação de Colidivel
    public double getX() { return ent_base.getX(); }
    public double getY() { return ent_base.getY(); }
    public double getRaio() { return ent_base.getRaio();}
    public Estados getEstado() { return ent_base.getEstado(); }
}