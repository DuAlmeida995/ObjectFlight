package Mecanicas.interfaces;

import Mecanicas.bases.DisparadorBase;
import Mecanicas.constantes.Estados;

/* inferface Chefe 
 * Interface 'Chefe' para ser utlizada na 'GameManager' quando necessário tratar os diferentes tipos de chefes em mesma funções. 
*/

public interface Chefe {
    public Estados getEstado();
    
    public boolean estaInvencivel();
    public void reduzir();
    public boolean estaMorto();
    
    public void disparar(DisparadorBase projeteisInimgos, long tempoAtual); 

    public boolean colideCom(Colidivel outro);
    public void emExplosao();
 
    public void update(long delta, double posJogadorX, double posJogadorY);
    public void draw();
}