/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lda_classification;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author
 */
public class LDA_Classification {

    /**
     * @param args the command line arguments
     */
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
        } catch (IllegalAccessException ex) {
        } catch (InstantiationException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        MainMenu M1 = new MainMenu();
        M1.show();
    }
    
}
