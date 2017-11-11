package chess;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author German - pc
 */
public class Board extends JPanel{
    private int x,y;
    private BufferedImage m_board = null;
    private BufferedImage []black_pieces = new BufferedImage[6];
    private BufferedImage []white_pieces = new BufferedImage[6];
    private int [][]table = {{6,1,0,0,0,0,11,16},
                             {5,1,0,0,0,0,11,15},
                             {4,1,0,0,0,0,11,14},
                             {3,1,0,0,0,0,11,12},
                             {2,1,0,0,0,0,11,13},
                             {4,1,0,0,0,0,11,14},
                             {5,1,0,0,0,0,11,15},
                             {6,1,0,0,0,0,11,16}};
        
    private final int m_boardWidth;
    private final int m_boardHeight;
    
    private final int piece_Width;
    private final int piece_Height;
    
    // Los parámetros boardWidth y boardHeight
    // tendrán el valor del tamaño de la ventana.
    // De manera que la imagen del tablero ocupe toda la pantalla (frame principal).
    public Board(int boardWith, int boardHeight) {
        
        m_boardWidth  = boardWith;
        m_boardHeight = boardHeight;
        
        piece_Width = ((boardWith)/ 8) + 1;
        piece_Height = ((boardHeight - 25) / 8);
        
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
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Se pinta el tablero adaptando la imagen al tamaño del frame principal.
        // NOTA: La resta de '-7' y '-30' se hace para una mejor presentación a la hora de desplegar el tablero.
        // Ya que esos parámetros representan la esquina inferior derecha de la imagen.
        // Y al intentarla despleglar del mismo tamaño de la ventana, sobresale un poco.
        g.drawImage(m_board.getScaledInstance(m_boardWidth, m_boardHeight, Image.SCALE_DEFAULT), 0, 0, m_boardWidth - 7, m_boardHeight - 30, null);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8 ; j++){
                if(table[i][j] < 10 && table[i][j] > 0){    
                    g.drawImage(black_pieces[table[i][j]-1].getScaledInstance(piece_Width - 4, piece_Height , Image.SCALE_DEFAULT), (piece_Width * i),(piece_Height * j)+2, piece_Width - 10, piece_Height - 1 , null);
                }
                else
                    if(table[i][j] > 10)
                        g.drawImage(white_pieces[table[i][j]-11].getScaledInstance(piece_Width - 4 , piece_Height , Image.SCALE_DEFAULT), (piece_Width * i),(piece_Height * j)+2, piece_Width - 5, piece_Height - 1 , null);
            }
        }
    }
    
    public void getXY(int x, int y){
        this.x = x;
        this.y = y;
        x = Math.round((x + 1)/ piece_Width);
        y = Math.round((y + 30)/ piece_Height);
        y--;
        //la operacion de dividir X y Y entre los tamaños de las piezas
        //se realiza para obtener la posicion de la matriz que corresponde
        //a la casilla clickeada.
        System.out.println(x+","+y);
    }

}