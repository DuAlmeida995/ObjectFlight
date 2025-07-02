package Jogo.carregadores;

public class SpawnInfo {
    
    String tipo;
    int vidaMaxima;
    long tempoSpawn;
    double posicaoX;
    double posicaoY;

    public SpawnInfo(String tipo, int vidaMaxima, long TempoSpawn, double posicaoX, double posicaoY){
        this.tipo = tipo;
        this.vidaMaxima = vidaMaxima;
        this.tempoSpawn = TempoSpawn;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }

    public String getTipo(){ return this.tipo;}

    public long getTempoSpawn(){ return this.tempoSpawn;}
    public void setTempoSpawn(long tempoSpawn){ this.tempoSpawn = tempoSpawn;}
    public int getVidaMaxima(){ return this.vidaMaxima;}
    public double getX(){ return this.posicaoX;}
    public double getY(){ return this.posicaoY;}
}
