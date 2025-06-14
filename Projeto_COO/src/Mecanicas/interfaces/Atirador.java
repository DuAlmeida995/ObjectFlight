package Mecanicas.interfaces;

import Mecanicas.projetil.ProjetilPool;

public interface Atirador {
    boolean podeAtirar(long now);
    void atirar(long tempoAtual, ProjetilPool projetil);
}