/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author German - pc
 */
public class Chess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            (new FormFrame(500, 500)).setVisible(true);
        }
        catch(final Exception e) {
            e.printStackTrace();
        }
    }
}
