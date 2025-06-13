package Mecanicas;

public interface Atirador {
    boolean podeAtirar(long now);
    void atirar(long tempoAtual, ProjetilPool projetil);
}