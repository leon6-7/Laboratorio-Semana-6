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
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelEntrada extends JPanel {

    private final JLabel prompt;
    private final JTextField campo;

    public PanelEntrada(String inicio) {
        setLayout(new BorderLayout(4, 0));
        setBackground(EstiloConsola.fondo);
        setBorder(BorderFactory.createEmptyBorder(4, 10, 10, 10));

        prompt = new JLabel(inicio);
        prompt.setBackground(EstiloConsola.fondo);
        prompt.setForeground(EstiloConsola.verde);
        prompt.setFont(EstiloConsola.fuente);

        campo = new JTextField();
        campo.setBackground(EstiloConsola.fondo);
        campo.setForeground(EstiloConsola.verde);
        campo.setCaretColor(EstiloConsola.verde);
        campo.setFont(EstiloConsola.fuente);
        campo.setBorder(BorderFactory.createEmptyBorder());

        add(prompt, BorderLayout.WEST);
        add(campo, BorderLayout.CENTER);
    }

    public void setPrompt(String p) {
        prompt.setText(p != null ? p : ">");
    }

    public String getPrompt() {
        return prompt.getText();
    }

    public String getTexto() {
        return campo.getText();
    }

    public void limpiar() {
        campo.setText("");
    }

    public void enfocar() {
        campo.requestFocusInWindow();
    }

    public void alEnter(ActionListener listener) {
        campo.addActionListener(listener);
    }
}