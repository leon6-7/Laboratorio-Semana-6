/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Excepciones;

/**
 *
 * @author ANGILI PALACIOS
 */
public class ComandoDesconocidoException extends ConsolaException{
    public ComandoDesconocidoException(String cmd) {
        super("'" + cmd + "' no se reconoce como un comando interno o externo");
    }
    
}
