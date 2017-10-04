/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import javax.swing.JFrame;

/**
 *
 * @author German - pc
 */
public class FormFrame extends JFrame {
    // Para el tamaño de la ventana.
    private final int m_width;
    private final int m_height;
    
    public FormFrame(int width, int height) {
        m_width  = width;
        m_height = height;
        
        // Propiedades de la ventana.
        setSize(m_width, m_height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chess");
        setResizable(false);
        
        // Se inserta el tablero al frame.
        add(new Board(m_width, m_height));
    }
}
