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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class VentanaConsola extends JFrame {

    private final AreaSalida salida;
    private final PanelEntrada entrada;

    public VentanaConsola(String inicio) {
        super("Administrator: Command Prompt");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(EstiloConsola.tamIni);
        setMinimumSize(EstiloConsola.tamMin);
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstiloConsola.fondo);
        setLayout(new BorderLayout());

        salida = new AreaSalida();
        entrada = new PanelEntrada(inicio);

        add(salida, BorderLayout.CENTER);
        add(entrada, BorderLayout.SOUTH);

        salida.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                enfocar();
            }
        });

        encabezado();
    }

    private void encabezado() {
        linea("Microsoft Windows [Version 10.0.22621.521]");
        linea("(c) Microsoft Corporation. All rights reserved.");
        linea("");
    }

    public void imprimir(String msg) {
        salida.escribir(msg);
    }

    public void linea(String l) {
        salida.linea(l);
    }

    public void limpiar() {
        salida.limpiar();
    }

    public void setPrompt(String p) {
        entrada.setPrompt(p);
    }

    public String getPrompt() {
        return entrada.getPrompt();
    }

    public String leerComando() {
        String cmd = entrada.getTexto();
        entrada.limpiar();
        return cmd;
    }

    public void enfocar() {
        entrada.enfocar();
    }

    public void alEnter(ActionListener listener) {
        entrada.alEnter(listener);
    }

    public void mostrar() {
        setVisible(true);
        enfocar();
    }
}