package Jogo.carregadores;

import java.io.*;
import java.util.*;


public class CarregadorFase {
    private ArrayList<SpawnInfo> spawnInfo = new ArrayList<>();

    public CarregadorFase(){
    }

    private void bufferizacao(String arquivo, long tempoAtual) throws IOException{
        spawnInfo.clear();
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        
        String linha;
        String [] dados;

        while((linha = br.readLine()) != null){
            dados = linha.split("\\s+");
            String tipo = dados[0];
            switch (tipo) {
                case "INIMIGO":
                    spawnInfo.add(new SpawnInfo(tipo + " " + dados[1], 0, Long.parseLong(dados[2]) + tempoAtual , Double.parseDouble(dados[3]), Double.parseDouble(dados[4])));    
                    break;    
                case "CHEFE":
                    spawnInfo.add(new SpawnInfo(tipo, Integer.parseInt(dados[2]), Long.parseLong(dados[3]) + tempoAtual, Double.parseDouble(dados[4]), Double.parseDouble(dados[5])));
                    break;
                default:
                    break;
            }
        }
        
        br.close();
    }

    public void carregar(String arquivo, long tempoAtual){
        try{
            bufferizacao(arquivo, tempoAtual);
        }catch(IOException e){
            System.out.println("Erro no carregamento da fase: "+ arquivo);
            e.printStackTrace();
        }
    }

    public ArrayList<SpawnInfo> getSpawnInfo(){
        return this.spawnInfo;
    }

}
