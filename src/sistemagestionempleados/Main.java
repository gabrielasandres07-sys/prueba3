/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemagestionempleados;

/**
 *
 * @author USER
 */
import javax.swing.SwingUtilities;
import sistemagestionempleados.vista.VentanaEmpleados;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> {
            VentanaEmpleados ventana = new VentanaEmpleados();
            ventana.setVisible(true);
        });
        
    }
    
}
