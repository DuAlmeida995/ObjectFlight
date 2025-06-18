package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;
import Mecanicas.bases.EntidadeInimigoBase;
import Mecanicas.interfaces.Colidivel;

import static Mecanicas.constantes.Estados.*;

public class Inimigo2{
        private int contadorTiro;

        EntidadeInimigoBase entIni_base;

        public Inimigo2(double x, double y, double v, double angulo, double raio, double vr) {
                entIni_base = new EntidadeInimigoBase(x, y, v, angulo, raio, vr);
        }

        public void move(long delta) {
                if (entIni_base.getEstado() == EXPLODING) {
                        if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                                entIni_base.setEstado(INACTIVATE);
                        }
                        return;
                }

                if (entIni_base.getEstado() == ACTIVE) {
                        // Verifica se saiu da tela
                        if (entIni_base.getY()> GameLib.HEIGHT + 10) {
                                entIni_base.setEstado(INACTIVATE);
                                return;
                        }

                        // Move reto para baixo
                        entIni_base.setY(entIni_base.getY() + entIni_base.getV() * delta);
                }
        }

        public void update(long deltaTime) {
                // Se estiver explodindo, aguarda fim e depois inativa
                if (entIni_base.getEstado() == EXPLODING) {
                        if (deltaTime > entIni_base.getexplosaoFim()) {
                                entIni_base.setEstado(INACTIVATE);
                        }
                        return;
                }

                // Movimento normal
                move(deltaTime);

                // Lógica de tiro
                contadorTiro++;
                if (contadorTiro >= 100 ) {
                        entIni_base.atirar(deltaTime);
                        contadorTiro = 0;
                }
        }

        public void draw() {
                if (entIni_base.getEstado() == EXPLODING) {
                        // Desenha a explosão
                        double alpha = (System.currentTimeMillis() - entIni_base.getexplosaoComeco()) / (double)(entIni_base.getexplosaoFim() - entIni_base.getexplosaoComeco());
                        GameLib.drawExplosion(entIni_base.getX(), entIni_base.getY(), alpha);
                } else {
                        // Desenha o inimigo diamante
                        GameLib.setColor(Color.MAGENTA);
                        GameLib.drawDiamond(entIni_base.getX(), entIni_base.getY(), entIni_base.getRaio());
                }
        }

        public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

        public void emColisao(Colidivel outro){ entIni_base.colideCom(outro);}
        
        public void atirar(long tempoAtual) { entIni_base.atirar(tempoAtual);}

}
