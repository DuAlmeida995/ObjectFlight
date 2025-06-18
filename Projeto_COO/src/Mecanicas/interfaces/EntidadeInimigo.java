package Mecanicas.interfaces;

import Mecanicas.constantes.Estados;

public interface EntidadeInimigo{
    public Estados getEstado();
    public void draw();
    public boolean colideCom(Colidivel outro);
    public void emColisao();
    public void update(long delta);
}