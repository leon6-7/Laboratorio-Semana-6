package semana_6;

import javax.swing.SwingUtilities;
import semana_6.Control.Interprete;
import semana_6.Control.ResultadoComando;
import semana_6.Modelo.SistemaArchivos;
import semana_6.Vista.VentanaConsola;

public class SEMANA_6 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaArchivos sistema = new SistemaArchivos();
            Interprete interprete = new Interprete(sistema);
            VentanaConsola ventana = new VentanaConsola(interprete.getPrompt());

            ventana.alEnter(e -> {
                String comando = ventana.leerComando();

                ventana.linea(ventana.getPrompt() + comando);

                ResultadoComando resultado = interprete.ejecutar(comando);

                if (resultado.debeSalir()) {
                    ventana.dispose();
                    return;
                }

                if (resultado.debeLimpiar()) {
                    ventana.limpiar();
                } else if (!resultado.estaVacio()) {
                    ventana.linea(resultado.getTexto());
                }

                ventana.setPrompt(interprete.getPrompt());
                ventana.enfocar();
            });

            ventana.mostrar();
        });
    }

}


