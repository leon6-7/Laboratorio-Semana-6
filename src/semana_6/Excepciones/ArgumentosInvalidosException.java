/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Excepciones;

/**
 *
 * @author Abril
 */
//El mensaje se muestra en consola
public class ArgumentosInvalidosException extends ConsolaException {
    //Tira el mensaje dado
    public ArgumentosInvalidosException(String mensaje) {
        super(mensaje);
    }

    //Mensaje predeterminado
    public ArgumentosInvalidosException(String comando, String uso) {
        super("La sintaxis del comando " + comando + " no es correcta.\nUso: " + uso);
    }
}