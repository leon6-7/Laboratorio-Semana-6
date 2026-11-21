package semana_6;

import javax.swing.SwingUtilities;
import semana_6.Control.Interprete;
import semana_6.Modelo.SistemaArchivos;
import semana_6.Vista.VentanaConsola;

public class SEMANA_6 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaArchivos sistema = new SistemaArchivos();
            Interprete interprete = new Interprete(sistema);
            VentanaConsola ventana = new VentanaConsola(interprete.getPrompt());

            ventana.mostrar();
        });
    }
}