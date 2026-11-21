/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Excepciones;

/**
 *
 * @author ANGILI PALACIOS
 */
public class ElementoNoEncontradoException extends ConsolaException{
    public ElementoNoEncontradoException(String nombre) {
        super("El sistema no puede encontrar '" + nombre );
    }
}
