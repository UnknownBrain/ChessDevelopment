package chess;

import java.awt.*;
import static java.awt.FlowLayout.CENTER;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import org.jpl7.Query;

/**
 *
 * @author German - pc, Aaron
 */
public class Board extends JPanel{
    private int t = -1;
    int cx,cy;
    boolean move;
    private BufferedImage m_board = null;
    private BufferedImage []black_pieces = new BufferedImage[6];
    private BufferedImage []white_pieces = new BufferedImage[6];
    private int [][]table = {{6,5,4,2,3,4,5,6},
                             {1,1,1,1,1,1,1,1},
                             {0,0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0,0},
                             {11,11,11,11,11,11,11,11},
                             {16,15,14,13,12,14,15,16}};
    private Piece [] piezas = new Piece[32];
        
    private final int m_boardWidth;
    private final int m_boardHeight;
    
    private final int piece_Width;
    private final int piece_Height;
    
    // Los parámetros boardWidth y boardHeight
    // tendrán el valor del tamaño de la ventana.
    // De manera que la imagen del tablero ocupe toda la pantalla (frame principal).
    public Board(int boardWith, int boardHeight) {
        
        this.setLayout(null);
        
        m_boardWidth  = boardWith;
        m_boardHeight = boardHeight;
        
        piece_Width = ((boardWith)/ 8) + 1;
        piece_Height = (boardHeight - 25) / 8;
        
        try {
            // Se carga la imagen del tablero.
            
            // NOTA: El parámetro para cargar la imagen se usa de esta forma
            // ya que se asume que siempre tendrá esa ruta.
            // Para las piezas el parámetro será tipeado dinámicamente por el desarrollador
            // para que seleccione la pieza con que le haya tocado trabajar.
            m_board = ImageIO.read(new File("images/board/board.jpg"));
            for(int i = 0;i < 6;i++){
                black_pieces[i] = ImageIO.read(new File("images/enum pieces/"+(i+1)+".png"));
                white_pieces[i] = ImageIO.read(new File("images/enum pieces/"+(i+11)+".png"));
            }
            
        } catch (IOException ex1) {
            System.out.println(ex1.toString());
        } 
        int k = 0;
        for(int i = 0 ; i < 8 ; i++){
            for(int j = 0 ; j < 8 ;j++){
                if(table[i][j] > 0 && table[i][j] < 10){
                    piezas[k] = new Piece(black_pieces[table[i][j] - 1],table[i][j],piece_Width,piece_Height,i,j);
                    piezas[k].setBounds(piece_Width * j, piece_Height * i,piece_Width,piece_Height);
                    add(piezas[k]);
                    k++;
                }
                else{
                    if(table[i][j] > 10){
                        piezas[k] = new Piece(white_pieces[table[i][j] - 11],table[i][j],piece_Width,piece_Height,i,j);
                        piezas[k].setBounds(piece_Width * j, piece_Height * i,piece_Width,piece_Height);
                        add(piezas[k]);
                        k++;
                    }
                }
            }
        }
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Se pinta el tablero adaptando la imagen al tamaño del frame principal.
        // NOTA: La resta de '-7' y '-30' se hace para una mejor presentación a la hora de desplegar el tablero.
        // Ya que esos parámetros representan la esquina inferior derecha de la imagen.
        // Y al intentarla despleglar del mismo tamaño de la ventana, sobresale un poco.
        g.drawImage(m_board.getScaledInstance(m_boardWidth, m_boardHeight, Image.SCALE_DEFAULT), 0, 0, m_boardWidth - 7, m_boardHeight - 30, null);
    } 
    
    public void getXY(int x, int y) {
        cx = Math.round((y + 30)/ piece_Height);
        cy = Math.round((x + 1)/ piece_Width);
        cx--;
        //la operacion de dividir X y Y entre los tamaños de las piezas
        //se realiza para obtener la posicion de la matriz que corresponde
        //a la casilla clickeada
        if(t == -1){
            for(int i = 0 ; i < piezas.length;i++)
                if(cx == piezas[i].getI() && cy == piezas[i].getJ() && (piezas[i].getNroPieza() >= 11 && piezas[i].getNroPieza() <= 16)){
                    t = i;
                    break;
                }
        }
        else if(MoveOn(piezas[t], cx, cy)){
            this.remove(piezas[t]);
            piezas[t].setBounds(piece_Width * cy, piece_Height * cx,piece_Width,piece_Height);
            piezas[t].setI(cx);
            piezas[t].setJ(cy);
            this.add(piezas[t]);
            repaint();
            t =-1;
        }
        //la variable t es una especie de bandera, su funcion es guardar el indice del arreglo
        // de la pieza que se haya clickeado y una vez de mueva la pieza se devueve al valor de -1
        // si se clickea un espacio vacion la t se mantiene en su valor de -1.
        //Nota lo ideal seria hacer las consultas a prolog dentro de esta funcion.
        System.out.println(cx+","+cy);
    }
    
    public boolean MoveOn(Piece piece, int cx, int cy) {
        
        String conection = "consult('white_move.pl')";
        String comprobar;
        boolean mover = false;
        Query query, move;
        
        try {
                //Movimiento de Peon
                if(piece.getNroPieza() == 11) {
                    
                    query = new Query(conection);
                    query.hasSolution();
                    comprobar = "peon(" + piece.getI() + "," + cx + "," + piece.getJ() + "," + cy + ").";
                    move = new Query(comprobar);
                    
                    mover = move.hasSolution();
                    
                    if(mover) {
                        return true;
                    }
                    
                    if(!mover){
                        JOptionPane.showMessageDialog(null, "Movimiento no valido");
                    }
                }
        
                //Movimiento del Rey
                if(piece.getNroPieza() == 12) {
            
                    query = new Query(conection);
                    query.hasSolution();
                    comprobar = "rey(" + piece.getI() + "," + cx + "," + piece.getJ() + "," + cy + ").";
                    move = new Query(comprobar);
                    
                    mover = move.hasSolution();
                    
                    if(mover) {
                        return true;
                    }
                    
                    if(!mover){
                        JOptionPane.showMessageDialog(null, "Movimiento no valido");
                    }
                }
        
                //Movimiento de la Reina
                if(piece.getNroPieza() == 13) {
                    return true;
                }
        
                //Movimiento del Alfil
                if(piece.getNroPieza() == 14) {
            
                    return true;
                }
        
                //Movimiento del Caballo 
                if(piece.getNroPieza() == 15) {
            
                    query = new Query(conection);
                    query.hasSolution();
                    comprobar = "caballo(" + piece.getI() + "," + cx + "," + piece.getJ() + "," + cy + ").";
                    move = new Query(comprobar);
                    
                    mover = move.hasSolution();
                    
                    if(mover) {
                        return true;
                    }
                    
                    if(!mover){
                        JOptionPane.showMessageDialog(null, "Movimiento no valido");
                    }
                }
        
                //Movimiento de la Torre
                if(piece.getNroPieza() == 16) {
            
                    query = new Query(conection);
                    query.hasSolution();
                    comprobar = "torre(" + piece.getI() + "," + cx + "," + piece.getJ() + "," + cy + ").";
                    move = new Query(comprobar);
                    
                    mover = move.hasSolution();
                    
                    if(mover) {
                        return true;
                    }
                    
                    if(!mover){
                        JOptionPane.showMessageDialog(null, "Movimiento no valido");
                    }
                }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return false;
    }
}
