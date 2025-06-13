package Mecanicas

 public class GameEngineTeste {
     public static final int INACTIVE = 0;
     public static final int ACTIVE = 1;
     public static final int EXPLODING = 2;

     boolean running = true;
     /* variáveis usadas no controle de tempo efetuado no main loop */
     long delta;
     long currentTime = System.currentTimeMillis();

     public void verificaColisao(Colidivel um, Colidivel[] dois) {
         for (int i = 0; i < dois.length; i++) {
             double dx = dois[i].getX() - um.getX();
             double dy = dois[i].getY() - um.getY();
             double dist = Math.sqrt(dx * dx + dy * dy);

             if (dist < (um.getRadius() + dois.getRadius()) * 0.8) {
                 um.setEstado() = EXPLODING;
                 um.setExplosaoComeco(currentTime);
                 um.setExplosaoFim(currentTime + 2000);
             }
         }
     }
     public void verificaColisaoInimigo(Colidivel[] um, Colidivel[] dois) {
         for (int i = 0; i < um.length; i++) verificaColisao(um[i], dois);
 }







 }