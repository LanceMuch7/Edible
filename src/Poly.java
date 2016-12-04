
import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathan
 * @date 
 */
public class Poly {
    private int Shape;
    private int Size;
    private Color Col;
    private int[] Coords;
    private double[] Direction;
    private int Start;
    
    public Poly(int shape, Color color, int size, int[] C, double[] dir, int t) {
        Shape = shape;
        Size = size;
        Col = color;
        Coords = C;
        Direction = dir;
        Start = t;
    }
    
    public double getDirection(int i) {
        return Direction[i];
    }
    public int getCoords(int i) {
        return Coords[i];
    }
    public int getShape() {
        return Shape;
    }
    public int getSize() {
        return Size;
    }
    public Color getColor() {
        return Col;
    }
    public int getAge() {
        return Start;
    }
    
    public void setCoords(int[] C) {
        Coords = C;
    }
    public void setDirection(double[] dir) {
        Direction = dir;
    }
    public void setSize(int size) {
        Size = size;
    }
}