package Mecanicas.interfaces;

import Mecanicas.constantes.Estados;

/* inferface EntidadeInimigo 
 * Interface 'EntidadeInimigo' para ser utlizada na 'GameManager' quando necessário tratar os diferentes inimigos em uma mesma lista. 
*/

public interface EntidadeInimigo{
    public Estados getEstado();
    public double getX();
    public double getY();
    public double getAngulo();
    public long getProximoTiro();
    public void setProximoTiro(long proximoTiro);
    public boolean colideCom(Colidivel outro);
    public void emColisao();
    public void draw();
    public void update(long delta);
}