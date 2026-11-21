/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Excepciones;

/**
 *
 * @author Abril
 */
public class ArgumentosInvalidosException extends ConsolaException{
     public ArgumentosInvalidosException(String mensaje) {
        super(mensaje);
    }
     
    public ArgumentosInvalidosException(String detalle, String uso) {
         super("La sintaxis del comando " + detalle + " no es correcta.\nUso: " + uso);
    }
}
