package chess;

import java.awt.*;
import static java.awt.FlowLayout.CENTER;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import org.jpl7.PrologException;
import org.jpl7.Query;

/**
 *
 * @author German - pc, Aaron
 */
public class Board extends JPanel{
    
    //Cantidad máxima de piezas en el tablero.
    private final static byte MAX_PIECES = 32;
    
    //Cantidad de tipos de piezas (Peón, Alfil, Caballo, Rey, Reina, Torre).
    private final static byte TYPE_PIECES = 6;
    
    //Tamaño X/Y del tablero
    private final static byte TABLE_SIZE = 8;
    
    private int t = -1;
    private int cx, cy;
    private boolean move;
    private BufferedImage m_board = null;
    private BufferedImage []black_pieces = new BufferedImage[TYPE_PIECES];
    private BufferedImage []white_pieces = new BufferedImage[TYPE_PIECES];
    
    //(Negra - Blanca)
    //(1 - 11) - Peón       (2 - 12) - Rey
    //(3 - 13) - Reina      (4 - 14) - Alfil
    //(5 - 15) - Caballo    (6 - 16) - Torre
    private int [][]table = {{6,5,4,2,3,4,5,6},
                             {1,1,1,1,1,1,1,1},
                             {0,0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0,0},
                             {11,11,11,11,11,11,11,11},
                             {16,15,14,13,12,14,15,16}};
    private Piece [] piezas = new Piece[MAX_PIECES];
        
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
            for(byte i = 0; i < TYPE_PIECES; i++){
                black_pieces[i] = ImageIO.read(new File("images/enum pieces/"+(i+1)+".png"));
                white_pieces[i] = ImageIO.read(new File("images/enum pieces/"+(i+11)+".png"));
            }
            
        } catch (IOException ex1) {
            ex1.printStackTrace();
        } 
        
        //Agregar piezas
        for(byte i = 0, k = 0; i < TABLE_SIZE; i++) {
            //Chequear si es una posición sin piezas.
            if (table[i][0] == 0) {
                //Aumenta i hasta el siguiente conjunto de piezas.
                i += 3;
                continue;
            }
            for(byte j = 0; j < TABLE_SIZE; j++, k++) {
                BufferedImage bi = (table[i][j] > 10) ? white_pieces[table[i][j] - 11]: black_pieces[table[i][j] - 1];
                piezas[k] = new Piece(bi, table[i][j], piece_Width, piece_Height, i, j);
                piezas[k].setBounds(piece_Width * j - 3, piece_Height * i, piece_Width, piece_Height);
                add(piezas[k]);
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
    
    public void getXY(int x, int y)  throws InterruptedException {
        cx = Math.round((y + 30) / piece_Height);
        cy = Math.round((x + 1) / piece_Width);
        cx--;
        //la operacion de dividir X y Y entre los tamaños de las piezas
        //se realiza para obtener la posicion de la matriz que corresponde
        //a la casilla clickeada
        if(t == -1) {
            for(int i = 0 ; i < piezas.length;i++)
                if(cx == piezas[i].getI() && cy == piezas[i].getJ() && (piezas[i].getNroPieza() >= 11 && piezas[i].getNroPieza() <= 16)){
                    piezas[i].setOpaque(true);
                    piezas[i].setBackground(Color.green);
                    repaint();
                    t = i;
                    break;
                }
        }
        else
            if (piezas[t].getI() != cx || piezas[t].getJ() != cy){
                if(true/*MoveOn(piezas[t], cx, cy)*/){
                    this.remove(piezas[t]);
                    piezas[t].setBounds(piece_Width * cy - 3, piece_Height * cx,piece_Width,piece_Height);
                    table[piezas[t].getI()][piezas[t].getJ()] = 0;
                    table[cx][cy] = piezas[t].getNroPieza();
                    piezas[t].setI(cx);
                    piezas[t].setJ(cy);
                    piezas[t].setFirstMovement(false);
                    this.add(piezas[t]);
                    piezas[t].setOpaque(false);
                    repaint();
                    t = -1;
                    PCmove();
                    
                }
            } else {
                piezas[t].setOpaque(false);
                repaint();
                t = -1;
              }
            
        //la variable t es una especie de bandera, su funcion es guardar el indice del arreglo
        // de la pieza que se haya clickeado y una vez de mueva la pieza se devueve al valor de -1
        // si se clickea un espacio vacion la t se mantiene en su valor de -1.
        //Nota lo ideal seria hacer las consultas a prolog dentro de esta funcion.
        System.out.println(cx+","+cy);
    }
    
    public void PCmove() throws InterruptedException{
        Random R = new Random();
        int t;
        do{
            t = R.nextInt(16);
            if (piezas[t].getNroPieza() > 0 && piezas[t].getNroPieza() <= 6)
                break;
        }while(true);
        int cx = R.nextInt(8);
        int cy = R.nextInt(8);
        
        if(/*MoveOn(piezas[t], cx, cy)*/true){
            this.remove(piezas[t]);
            piezas[t].setBounds(piece_Width * cy, piece_Height * cx,piece_Width,piece_Height);
            table[piezas[t].getI()][piezas[t].getJ()] = 0;
            table[cx][cy] = piezas[t].getNroPieza();
            piezas[t].setI(cx);
            piezas[t].setJ(cy);
            this.add(piezas[t]);
            repaint();    
        }
    }
    
    public boolean MoveOn(Piece piece, int cx, int cy) throws PrologException, IllegalArgumentException {
        
        String conection = "consult('white_move.pl')",
               comprobar = "";
        Query query, move;
            
        //Consulta a white_move.pl
        query = new Query(conection);
        query.hasSolution();
        
        //Enviar tablero
        for(byte i = 0; i < MAX_PIECES; ++i) {
            //assertz(aliado(X, Y))
            String assertz = "assertz(pieza(";
            Piece p = piezas[i];
            if (p == null)
                continue;
            assertz = (p.getNroPieza() > 10) ? assertz.concat("1, ") : assertz.concat("0, ");
            assertz = assertz.concat(p.getI() + "," + p.getJ() + "))");
            Query q = new Query(assertz);
            q.hasSolution();
        }

        //Movimiento de...
        switch(piece.getNroPieza()) {
            case 11:
                //Peón
                comprobar += comprobar.concat("peon(");
                break;
            case 12:
                //Rey
                comprobar += comprobar.concat("rey(");
                break;
            case 13:
                //Reina
                //TODO: REINA
                break;
            case 14:
                //Alfil
                comprobar += comprobar.concat("alfil(");
                
                // Se le envía en qué cuadrante se moverá el alfil.                
                if(cx < piece.getI() && cy < piece.getJ()) 
                    comprobar += "1, ";
                else if(cx > piece.getI() && cy > piece.getJ())
                    comprobar += "2, ";
                else if(cx < piece.getI() && cy > piece.getJ())
                    comprobar += "3, ";
                else if(cx > piece.getI() && cy < piece.getJ())
                    comprobar += "4, ";
                else 
                    comprobar += "0, ";
                
                break;
            case 15:
                //Caballo
                comprobar += comprobar.concat("caballo(");
                break;
            case 16:
                //Torre
                comprobar += comprobar.concat("torre(");
                break;
            default:
                throw new IllegalArgumentException("Pieza inválida");
        }

        //Concatenar todo.
        comprobar = comprobar.concat(piece.getI() + "," + cx + "," + piece.getJ() + "," + cy + ").");
        //Enviar consulta
        move = new Query(comprobar);

        if (true/*move.hasSolution()*/)
            return true;
        else{
            piezas[t].setOpaque(false);
            t = -1;
            JOptionPane.showMessageDialog(null, "Movimiento inválido");
        }
        return false;
    }
}
