package chess;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author German - pc, Aaron
 */
public class FormFrame extends JFrame implements MouseListener{
    // Para el tamaño de la ventana.
    private final int m_width;
    private final int m_height;
    private Board board;
    public FormFrame(int width, int height) {
        m_width  = width;
        m_height = height;
        board = new Board(m_width, m_height);
        
        // Propiedades de la ventana.
        setSize(m_width, m_height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chess");
        setResizable(false);
        
        // Se inserta el tablero al frame.
        add(board);
        
        addMouseListener(this);     
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        try{
        board.getXY(x, y);
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
