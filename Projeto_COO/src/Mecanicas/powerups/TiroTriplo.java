package Mecanicas.powerups;

import Mecanicas.bases.PowerUpBase;
import Mecanicas.constantes.Estados;

import Mecanicas.interfaces.Colidivel;
import Mecanicas.interfaces.PowerUp;

import Mecanicas.jogador.Jogador;

import static Mecanicas.constantes.Estados.*;

/* classe TiroTriplo
 *  Classe que serve para gerenciar o powerup de tiro triplo no jogador, fazendo-o atirar três projéteis em formato de leque por um determinado tempo.     
*/

public class TiroTriplo implements Colidivel, PowerUp {

    private PowerUpBase pow_base;   /* Objeto para administrar as propriedades de 'Entidade'  */
    
    private Jogador jogador;         /* Referência para o objeto jogador dentro do jogo, afim de alterá-lo. */


    public TiroTriplo(double x, double y) {
        this.pow_base = new PowerUpBase(x, y, 10); /* Raio -> 10 */
    }

    /* Funções getters de posição, raio e estado.*/
    
    /* posição */
    public double getX(){ return pow_base.getX();}
    public double getY(){ return pow_base.getY();}

    /* raio */
    public double getRaio(){ return pow_base.getRaio();}

    /* estado */
    public Estados getEstado() { return pow_base.getEstado(); }


    /* ------------------------------------------------------------- Mecânicas do Jogador ------------------------------------------------------------- 
     * 
     * (1) Ativação e desativação;
     * (2) Aplicação;
     * (3) Colisão;
     * (4) Atualização e desenho.
     * 
    */
    
    /* (1) funções de ativação e desativação do efeito de tiro triplo no jogador. */

    /* ativa o efeito do tiro triplo, iniciando o contador do powerup e aplicando no jogador */
    public void ativar(Jogador jogador, long tempoTiroTriplo){
        this.jogador = jogador;
        pow_base.ativar(tempoTiroTriplo);;
        aplicar();
    }

    /* desativa o efeito do tiro triplo, aplica ao jogador a mudança, e atribuindo o estado de inativo ao powerup, podendo ser assim removido */
    public void desativar(){
        pow_base.desativar();
        aplicar();
    }


    /* (2) função que aplica o efeito ao jogador, permitindo (ou não) ele realizar o tiro triplo dentre de sua função 'disparar(long tempoAtual).' */

    public void aplicar(){
        jogador.setTiroTriplo(pow_base.getPowerUpAtivo());
    }

    
    /* (3) funções de execução da lógica de colisão e eventual remoção do powerup.*/
 
    /* calcula se uma entidade entra em colisão com outra. */ 
    public boolean colideCom(Colidivel o){ return pow_base.colideCom(o);}
    
    /* atualiza o estado do powerup para explosão afim de, assim, remover o objeto físico do powerup na lógica do jogo, porém mantendo o objeto ativo 
     * para o cálculo do tempo do efeito e o desenho da barra*/
    public void remover() {
        pow_base.setEstado(EXPLODING); 
    }
 

    /* (4) funções de atualizações do objeto de powerup e desenho (também da barra de tempo) ao longo do tempo de jogo */

    /* atualiza os atributos do powerup ao longo do tempo de jogo em duas condições:
    *  (i) caso esse tenha ultrapassado os limites do jogo, torna-se inativo;
    *  (ii) caso o tempo do efeito tenha passado, torna-se inativo;
    *  (iii) caso nenhuma dessas condições tenha sido alcançadas, o powerup é atualizado conforme sua lógica de movimento no jogo*/    
    public void update(long delta) {
        pow_base.update(delta);
    }   

    /* desenha a entidade do powerup e barra de tempo, caso o efeito esteja ativo */
    public void draw() {
        pow_base.drawTiroTriplo();
    }
}