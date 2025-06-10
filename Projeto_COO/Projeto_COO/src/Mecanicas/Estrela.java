package Mecanicas

import java.awt.Color;
import java.awt.Graphics2D;

public class Star extends Entidade implements Movivel {

    public Star(int x, int y) {
        super(x, y);
        this.velocidadeY = 1; // Move devagar
        this.largura = 2;
        this.altura = 2;
    }