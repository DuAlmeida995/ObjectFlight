package Jogo.carregadores;

import java.io.*;

public class CarregadorJogo {
    private JogoInfo jogoInfo;

    public CarregadorJogo(){
        
    }

    private void bufferizacao(String arquivo) throws IOException{
    
        BufferedReader br = new BufferedReader(new FileReader(arquivo));

        String linha; 
        
        int vidaJogador;
        int numFase;
        

        vidaJogador = Integer.parseInt(br.readLine());
        numFase = Integer.parseInt(br.readLine());

        String [] fases = new String[numFase];

        int i = 0;

        while ((linha = br.readLine()) != null) {
            fases[i] = "Configuracao/" + linha;
            i++;
        }
        
        jogoInfo = new JogoInfo(vidaJogador, numFase, fases);
        br.close();
    }

    public void carregar(String arquivo){
        try{
            bufferizacao(arquivo);
        }catch(IOException e){
            System.out.println("Erro no carregamento da fase: "+ arquivo);
            e.printStackTrace();
        }
    }

    public JogoInfo getJogoInfo(){ return this.jogoInfo;}
    
}
