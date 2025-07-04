package Mecanicas.interfaces;

import Mecanicas.constantes.Estados;
import Mecanicas.jogador.Jogador;

/* inferface PowerUp 
 * Interface 'PowerUp' para ser utlizada na 'GameManager' quando necessário tratar os diferentes powerups em uma mesma lista. 
*/

public interface PowerUp{
    public Estados getEstado();

    public void ativar(Jogador jogador, long duracao);
    
    public boolean colideCom(Colidivel outro);
    public void remover();
    
    public void update(long delta);
    public void draw();

}    

