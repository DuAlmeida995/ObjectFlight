package Jogo.carregadores;

public class JogoInfo {
    private int vidaJogador;
    private int numFase;
    String [] fases;


    public JogoInfo(int vidaJogador, int numFase, String [] fases){
        this.vidaJogador = vidaJogador;
        this.numFase = numFase;
        this.fases = fases;
    }

    public int getVidaJogador(){ return this.vidaJogador;}
    public int getNumFase() { return this.numFase;}
    public String[] getFases() { return this.fases;}
}
