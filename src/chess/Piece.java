package chess;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Aaron
 */
public class Piece extends JPanel{
    private BufferedImage pieza = null;
    private final int nroPieza;
    private final int Width;
    private final int Height;
    private int i,j;
    
    public Piece(BufferedImage pieza,int nroPieza,int Width,int Height,int i, int j){
        this.pieza = pieza;
        this.nroPieza = nroPieza;
        this.Width = Width;
        this.Height = Height;
        this.i = i;
        this.j = j;
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(Width,Height));
    }
    public int getNroPieza(){
        return nroPieza;
    }
    public int getI(){
        return i;
    }
    public int getJ(){
        return j;
    }
    public void setI(int i){
        this.i = i;
    }
    public void setJ(int j){
        this.j = j;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(pieza!=null)
            g.drawImage(pieza.getScaledInstance(Width , Height, Image.SCALE_DEFAULT), 0, 0, Width-7, Height, null);
    }
}
