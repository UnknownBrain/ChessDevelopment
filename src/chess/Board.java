package chess;

import java.awt.*;
import static java.awt.FlowLayout.CENTER;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import org.jpl7.PrologException;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

/**
 *
 * @author German - pc, Aaron
 */
public class Board extends JPanel {
    
    //Nombre de carpeta con archivos .pl
    private final static String PROLOG_FOLDER = "prolog files";
    
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
            for(byte i = 0; i < TYPE_PIECES; i++) {
                black_pieces[i] = ImageIO.read(new File("images/enum pieces/"+(i+1)+".png"));
                white_pieces[i] = ImageIO.read(new File("images/enum pieces/"+(i+11)+".png"));
            }
            
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        
        //Agregar piezas
        agregarPiezas();
        
        //Enviar tablero a tablero.pl
        enviarTableroProlog();
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
    
    //Agregar piezas
    private void agregarPiezas() {
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
    
    //Enviar tablero a tablero.pl
    private void enviarTableroProlog() {
        //Cambiar directorio raíz por carpeta con archivos Prolog.
        Query find = new Query("working_directory(_, '" + PROLOG_FOLDER + "')");
        find.hasSolution();
        
        //Consultar tablero.pl
        Query q = new Query("consult('tablero.pl')");
        q.hasSolution();
        for (Piece p : piezas) {
            //assertz(pieza(COLOR, X, Y)) //0 Negro //1 Blanco
            String assertz = "assertz(pieza(";
            assertz = (p.getNroPieza() > 10) ? assertz.concat("1,") : assertz.concat("0,");
            assertz = assertz.concat(p.getI() + "," + p.getJ() + "))");
            q = new Query(assertz);
            q.hasSolution();
            //System.out.println(assertz + " " + q.hasSolution());
        }
    }
    
    //Seleccionar una pieza. Colorear el fondo de verde y repintar.
    private void setSeleccionado(final byte i) {
        piezas[i].setOpaque(true);
        piezas[i].setBackground(Color.green);
        repaint();
    }
    
    //Deseleccionar una pieza. Regresa a ser transparente.
    private void setDeseleccionado() {
        piezas[t].setOpaque(false);
        repaint();
    }
    
    //Buscar una pieza. colorPieza es '0' o '1'.
    //Blanco = 1. Negro = 0.
    private int buscarPieza(final int cx, final int cy, final byte colorPieza) {
        //Por defecto, son los rangos de las piezas blancas.
        byte rangoInicio = 11,
             rangoFinal  = 16;
        if (colorPieza == 0) {
            rangoInicio = 1;
            rangoFinal = 6;
        }
        for(byte i = 0; i < piezas.length; i++)
            if (piezas[i] != null) {
                if(cx == piezas[i].getI() && 
                        cy == piezas[i].getJ() && 
                        (piezas[i].getNroPieza() >= rangoInicio && piezas[i].getNroPieza() <= rangoFinal)
                  )
                    return i;
            }
        return -1;
    }
    
    //Moverá la pieza al lugar deseado. 
    //Multiplier es algo para acomodar el setBounds, es insignificante.
    //colorPieza es para el llamado del método buscarPieza.
    private void moverPieza(final byte multiplier, final int t, final int cx, final int cy, final byte colorPieza) {
        
        this.remove(piezas[t]);
        piezas[t].setBounds(piece_Width * cy - multiplier, piece_Height * cx,piece_Width,piece_Height);
        table[piezas[t].getI()][piezas[t].getJ()] = 0;
        table[cx][cy] = piezas[t].getNroPieza();

        //Buscar si no existe un enemigo en donde
        //se vaya a mover el jugador correspondiente.
        int u = buscarPieza(cx, cy, (byte) colorPieza);

        //Consultar tablero.pl
        Query q = new Query("consult('tablero.pl')");
        q.hasSolution();

        byte c = (byte)((piezas[t].getNroPieza() > 10) ? 1 : 0);

        //Comer pieza.
        if(u != -1) {
            table[piezas[u].getI()][piezas[u].getJ()] = piezas[t].getNroPieza();
            Query eliminar = new Query("retract(pieza(" + c + "," + piezas[u].getI() + "," + piezas[u].getJ() + "))");
            eliminar.hasSolution();
            this.remove(piezas[u]);
            repaint();
            piezas[u] = null;
        }

        //Mover pieza.
        //renombrar(C, X1, Y1, X2, Y2).
        Query renombrar = new Query("renombrar(" + c + 
                "," + piezas[t].getI() + "," + piezas[t].getJ() + "," + cx + "," + cy + ")");
        renombrar.hasSolution();
        /*Query eliminar = new Query("retract(pieza(" + c + "," + piezas[t].getI() + "," + piezas[t].getJ() + "))");
        eliminar.hasSolution();
        Query insertar = new Query("assertz(pieza(" + c + "," + cx + "," + cy + "))");
        insertar.hasSolution();*/

        piezas[t].setI(cx);
        piezas[t].setJ(cy);
        this.add(piezas[t]);
    }
    
    public void getXY(int x, int y) throws InterruptedException {
        cx = Math.round((y + 30) / piece_Height);
        cy = Math.round((x + 1) / piece_Width);
        cx--;
        //la operacion de dividir X y Y entre los tamaños de las piezas
        //se realiza para obtener la posicion de la matriz que corresponde
        //a la casilla clickeada
        if(t == -1) {
            //Usuario. Pieza Blanca. Color = 1.
            t = buscarPieza(cx, cy, (byte)1);
            setSeleccionado((byte)t);
        }
        else
            if (piezas[t].getI() != cx || piezas[t].getJ() != cy) {
                if(MoveOn(piezas[t], cx, cy)) {
                    moverPieza((byte)3, t, cx, cy, (byte)0);
                    piezas[t].setOpaque(false);
                    repaint();
                    t = -1;
                    PCmove();
                    
                }
            } else {
                setDeseleccionado();
                t = -1;
              }
            
        //la variable t es una especie de bandera, su funcion es guardar el indice del arreglo
        // de la pieza que se haya clickeado y una vez se mueva la pieza se devueve al valor de -1
        // si se clickea un espacio vacio la t se mantiene en su valor de -1.
        System.out.println(cx+","+cy);
    }
    
    private void PCmove() throws InterruptedException {
        Random R = new Random();
        int t;
        boolean x = true;
        do {
            t = R.nextInt(16);
            if(piezas[t] != null) {
                //if (piezas[t].getNroPieza() > 0 && piezas[t].getNroPieza() <= 6) {
                    x = false;
                //}
            }
        }while(x);
        
        boolean flag = true;
        do{
            cx = R.nextInt(8);
            cy = R.nextInt(8); 

            if(MoveOn(piezas[t], cx, cy) /*&& buscarPieza(cx,cy,(byte)0) == -1*/) {
                moverPieza((byte)0, t, cx, cy, (byte)1);
                repaint(); 
                flag = false;
            }
        }while(flag);
        t =-1;
    }
    
    public boolean MoveOn(final Piece piece, int cx, int cy) throws PrologException, IllegalArgumentException {
        int nro = piece.getNroPieza();
        if(nro > 10) nro -= 10;
        Query query;
        String comprobar = "";
        switch(nro) {
            case 1:
                //Peón
                
                int color = -1;
                String peon = "consult('peon.pl')";
                
                //Consultar peon.pl
                Query q = new Query(peon);
                q.hasSolution();
                
                if(piece.getNroPieza() < 10){
                    comprobar = "salta_n("+0+","+piece.getFirstMovement()+","+piece.getI()+","+piece.getJ()+","+cx+","+cy+").";
                    //color = table[cx][cy] > 10 ? 1 : 0;
                    //comprobar = "black_peon(" + piece.getI() + "," + cx+ ","+ piece.getJ() + ","+ cy+ "," + table[cx][cy] + "," + color +").";
                }
                else{
                    comprobar = "salta_b("+1+","+piece.getFirstMovement()+","+piece.getI()+","+piece.getJ()+","+cx+","+cy+").";
                    //color = table[cx][cy] > 10 ? 1 : 0;
                    //comprobar = "white_peon(" + piece.getI() + "," + cx+ ","+ piece.getJ() + ","+ cy+ "," + table[cx][cy] + "," + color +").";
                }
                break;
            case 2:
                //Rey
                query = new Query("consult('king.pl')");
                query.hasSolution();
                if (piece.getNroPieza() < 10) {
                    
                    Variable c = new Variable("C");
                    
                    //Para PC. La PC retornará una lista de posiciones. Aquí se guardan.
                    ArrayList<Coordenada> prologPositions = new ArrayList<>();

                    query = new Query(
                        "mover([" + piece.getI() + "," + piece.getJ() + "]," + c + ")"
                    );
                    Map<String, Term> solution;

                    query.open();
                    while((solution = query.getSolution()) != null) {
                        Term t = (Term) solution.get("C");
                        //Recuperando las coordenadas
                        prologPositions.add(new Coordenada(t.arg(1).intValue(), t.arg(2).arg(1).intValue()));
                    }
                    
                    for (Coordenada coord : prologPositions) {
                        int enemigo = buscarPieza(coord.getX(), coord.getY(), (byte)1);
                        if (enemigo != -1) {
                            this.cx = piezas[enemigo].getI();
                            this.cy = piezas[enemigo].getJ();
                        }
                    }
                }
                else
                    comprobar = "mover(" + piece.getI() + "," + piece.getJ() + "," + cx + "," + cy + ")."; 
                break;
            case 3:
                //Reina
                //TODO: REINA
                query = new Query("consult('Reina.pl')");
                query.hasSolution();
                comprobar = "queen(" + ((piece.getNroPieza() > 10) ? "1" : "0") + ",_,";
                
                comprobar = comprobar.concat(piece.getI() + "," + piece.getJ() + "," + cx + "," + cy + ").");
                                
                break;
            case 4:                
                //Alfil
                //TODO: Revisar esto, Germán.
                //Consulta a alfil.pl
                query = new Query("consult('alfil.pl')");
                query.hasSolution();
                comprobar = "alfil(";
                
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
                //Concatenar todo.
                comprobar = comprobar.concat(piece.getI() + "," + cx + "," + piece.getJ() + "," + cy + ").");

                break;
            case 5:
                //Cabasho
                query = new Query("consult('caballo.pl')");
                query.hasSolution();
                comprobar = "caballo(" + ((piece.getNroPieza() > 10) ? "1" : "0") + ",";
                
                //Concatenar todo.
                comprobar = comprobar.concat(piece.getI() + "," + cx + "," + piece.getJ() + "," + cy + ").");

                break;
            case 6:
                // Torre
                query = new Query("consult('torre.pl')");
                query.hasSolution();
                comprobar = "torre(" + ((piece.getNroPieza() > 10) ? "1" : "0") + ",";
                   
                if(cx > piece.getI())
                    comprobar += "1, ";
                else if(cy > piece.getJ())
                    comprobar += "2, ";
                else if(cx < piece.getI())
                    comprobar += "3, ";
                else if(cy < piece.getJ())
                    comprobar += "4, ";
                else
                    comprobar += "0, ";
                //Concatenar todo.
                comprobar = comprobar.concat(piece.getI() + "," + cx + "," + piece.getJ() + "," + cy + ").");
                break;
            default:
                throw new IllegalArgumentException("Pieza inválida");
        }

        //Enviar consulta
        Query move = new Query(comprobar);
        
        if (move.hasSolution()) {
            piece.setFirstMovement(false);
            return true;
        }
        else {
            piezas[t].setOpaque(false);
            repaint();
            t = -1;
            JOptionPane.showMessageDialog(null, "Movimiento inválido");
        }
        return false;
    }
}
