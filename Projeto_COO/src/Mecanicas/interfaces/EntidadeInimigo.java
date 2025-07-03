package Mecanicas.interfaces;

import Mecanicas.bases.DisparadorBase;
import Mecanicas.constantes.Estados;

/* inferface EntidadeInimigo 
 * Interface 'EntidadeInimigo' para ser utlizada na 'GameManager' quando necessário tratar os diferentes inimigos em uma mesma lista. 
*/

public interface EntidadeInimigo{
    public Estados getEstado();
    public double getX();
    public double getY();
    
    public void disparar(DisparadorBase projeteisInimigos, long tempoAtual);
    
    public boolean colideCom(Colidivel outro);
    public void emExplosao();
    
    public void update(long delta);
    public void draw();
}