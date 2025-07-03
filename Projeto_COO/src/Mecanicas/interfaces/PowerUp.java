package Mecanicas.interfaces;

import Mecanicas.constantes.Estados;
import Mecanicas.jogador.Jogador;

public interface PowerUp{
    public Estados getEstado();
    public void ativar(Jogador jogador, long duracao);
    public boolean colideCom(Colidivel outro);
    public void desativar();
    public void update(long delta);
    public void draw();

}    

