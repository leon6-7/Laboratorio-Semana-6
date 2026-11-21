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
                String cmd = ventana.leerComando();
                ventana.linea(ventana.getPrompt() + " " + cmd);

                ResultadoComando res = interprete.ejecutar(cmd);

                if (res != null) {
                    if (res.debeLimpiar()) {
                        ventana.limpiar();
                    }
                    if (res.getTexto() != null && !res.getTexto().isEmpty()) {
                        ventana.imprimir(res.getTexto());
                    }
                    ventana.setPrompt(interprete.getPrompt());
                    if (res.debeSalir()) {
                        ventana.dispose();
                        return;
                    }
                }

                ventana.enfocar();
            });

            ventana.mostrar();
        });
    }
}