/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Vista;

/**
 *
 * @author ANGILI PALACIOS
 */
import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AreaSalida extends JPanel {

    private final JTextArea texto;
    private final JScrollPane scroll;

    public AreaSalida() {
        setLayout(new BorderLayout());
        setBackground(EstiloConsola.fondo);

        texto = new JTextArea();
        texto.setEditable(false);
        texto.setBackground(EstiloConsola.fondo);
        texto.setForeground(EstiloConsola.verde);
        texto.setCaretColor(EstiloConsola.verde);
        texto.setFont(EstiloConsola.fuente);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setMargin(new Insets(8, 10, 8, 10));

        scroll = new JScrollPane(texto);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    public void escribir(String msg) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        texto.append(msg);
        bajar();
    }

    public void linea(String l) {
        texto.append((l != null ? l : "") + System.lineSeparator());
        bajar();
    }

    public void limpiar() {
        texto.setText("");
        bajar();
    }

    private void bajar() {
        texto.setCaretPosition(texto.getDocument().getLength());
    }
}