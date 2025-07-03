package Mecanicas.inimigos;

import java.awt.Color;

import Jogo.GameLib;

import Mecanicas.bases.DisparadorBase;
import Mecanicas.bases.EntidadeInimigoBase;

import Mecanicas.constantes.Estados;
import static Mecanicas.constantes.Estados.*;

import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.EntidadeInimigo;


/* class Inimigo2
 * Classe que implementa a entidade de inimigo tipo 2 no jogo.
*/

public class Inimigo2 implements EntidadeInimigo, Colidivel{

    EntidadeInimigoBase entIni_base; /* Objeto que administra as propriedades de 'Entidade Inimigo' */

    private boolean rotacaoCompleta; /* Variável para controlar quando o inimigo rotaciona */
    private boolean deveDisparar;    /* Variável para controlar quando o inimigo deve atira */
    private double anteriorY;        /* Variável para a lógica do movimento */
    private double threshold ;       /* Variável de threshold para o movimento */

    public Inimigo2(double x, double y, double v, double angulo, double raio, double vr, long tempoAtual) {
        entIni_base     = new EntidadeInimigoBase(x, y, v, angulo, raio, vr, tempoAtual);
        rotacaoCompleta = false;
        deveDisparar    = false;
        threshold       = GameLib.HEIGHT * 0.30;
    }

    /* Funções getters de posição, raio e estado.*/

    /* posição */
    public double getX(){ return entIni_base.getX();}
    public double getY(){ return entIni_base.getY();}

    /* raio */
    public double getRaio() { return entIni_base.getRaio();}

    /* estado */
    public Estados getEstado(){ return entIni_base.getEstado();}

    /* ------------------------------------------------------------- Mecânicas do Inimigo2 ------------------------------------------------------------- 
     *  
     * (1) Colisão;
     * (2) Atirar;
     * (3) Atualização e desenho;
     * 
    */


    /* (1) funções de execução da lógica de colisão e eventual explosão do Inimigo2 */

    /* calcula se uma entidade entra em colisão com outra. */
    public boolean colideCom(Colidivel outro){ return entIni_base.colideCom(outro);}

    /* atualiza os atributos do Inimigo2 caso este exploda. */
    public void emExplosao(){entIni_base.emExplosao();}


    /* (2) função que faz com que o Inimigo2 atire projéteis, inserindo estes na 'pool' de projéteis de inimigos do jogo. */

    /* garante que o disparo ocorrerá apenas uma vez após a rotação está completada. */
    public boolean deveDisparar() {
        if (deveDisparar) {
                deveDisparar = false;  /* Reseta após verificar */
                return true;
        }
        return false;
    }

    /* realiza o disparo */
    public void disparar(DisparadorBase projeteisInimigos, long tempoAtual){
        if (deveDisparar()) {
            /* Dispara 3 projéteis em um formato de leque */
            double[] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
            for (double angle : angles) {
                /* Adiciona variação aleatória no ângulo */
                double a = angle + Math.random() * Math.PI/6 - Math.PI/12;
                double vx = Math.cos(a) * 0.30;
                double vy = Math.sin(a) * 0.30;
                projeteisInimigos.disparar(entIni_base.getX(), entIni_base.getY(), vx, vy, 2.0);
            }
        }
    }


    /* (3) funções de atualizações do Inimigo2 e desenho ao longo do tempo de jogo. */

    /* atualiza os atributos do Inimigo2 ao longo do tempo de jogo em três instâncias:
    *  (i) caso esse tenha explodido, torna-se inativo;
    *  (ii) caso esse tenha ultrapassado os limites do jogo.
    *  (iii) caso nenhuma dessas condições tenha sido alcançadas, o inimigo é atualizado conforme sua lógica de movimento no jogo. */
    public void update(long deltaTime) {
        /* instância (i) */
        if (entIni_base.getEstado() == EXPLODING) {
            if (System.currentTimeMillis() > entIni_base.getexplosaoFim()) {
                entIni_base.setEstado(INACTIVATE);
            }
            return;
        }
        /* instância (ii) */
        if(entIni_base.getX() < - 10 || entIni_base.getX() > GameLib.WIDTH + 10){
            entIni_base.setEstado(INACTIVATE);
            return;
        }
        /* instância (iii) */
        /* movimenta-se entrando na tela no sentido vertical para baixo, realizando uma rotação e saindo da tela */
        anteriorY = entIni_base.getY();
        entIni_base.setX(entIni_base.getX() + entIni_base.getV()*Math.cos(entIni_base.getAngulo()) * deltaTime);
        entIni_base.setY(entIni_base.getY() + entIni_base.getV()*Math.sin(entIni_base.getAngulo()) * deltaTime * (-1.0));
        entIni_base.setAngulo(entIni_base.getAngulo() + entIni_base.getVR() * deltaTime);
        if (!rotacaoCompleta) {
            if(anteriorY < threshold && entIni_base.getY() >= threshold){
            if(entIni_base.getX() < GameLib.WIDTH / 2) entIni_base.setVR(0.003);
            else entIni_base.setVR(-0.003);
             }
            if(entIni_base.getVR() > 0 && Math.abs(entIni_base.getAngulo() - 3 * Math.PI) < 0.05){
                entIni_base.setVR(0.0);
                entIni_base.setAngulo(3*Math.PI);
                rotacaoCompleta = true;
                deveDisparar = true;
            }
            if(entIni_base.getVR() < 0 && Math.abs(entIni_base.getAngulo()) <  0.05){
                entIni_base.setVR(0.0);
                entIni_base.setAngulo(0.0);
                rotacaoCompleta = true;
                deveDisparar = true;
            }
        }
    }    

    /* desenha a entidade Inimigo2. */
    public void draw() {
        if (entIni_base.getEstado() == EXPLODING) {
            double alpha = (System.currentTimeMillis() - entIni_base.getexplosaoComeco()) / (double)(entIni_base.getexplosaoFim() - entIni_base.getexplosaoComeco());
            GameLib.drawExplosion(entIni_base.getX(), entIni_base.getY(), alpha);
        }else {
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(entIni_base.getX(), entIni_base.getY(), entIni_base.getRaio());
        }
    }
}
