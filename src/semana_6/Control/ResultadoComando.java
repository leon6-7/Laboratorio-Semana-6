/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Control;

public class ResultadoComando {

    private final String texto;
    private final boolean limpiar;
    private final boolean salir;

    private ResultadoComando(String texto, boolean limpiar, boolean salir) {
        this.texto = (texto == null) ? "" : texto;
        this.limpiar = limpiar;
        this.salir = salir;
    }

    public static ResultadoComando texto(String texto) {
        return new ResultadoComando(texto, false, false);
    }
    
    public static ResultadoComando limpiar() {
        return new ResultadoComando("", true, false);
    }
    
    public static ResultadoComando salir() {
        return new ResultadoComando("", false, true);
    }
    
    public String getTexto() {
        return texto;
    }
    
    public boolean debeLimpiar() {
        return limpiar;
    }
    
    public boolean debeSalir() {
        return salir;
    }
    
    public boolean estaVacio() {
        return texto.isEmpty();
    }
}