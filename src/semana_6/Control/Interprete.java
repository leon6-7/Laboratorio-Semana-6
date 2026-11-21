/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Control;
import semana_6.Excepciones.ArgumentosInvalidosException;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author Abril
 */


public class Interprete {
    
    private String[] comandos= {"Mkdir", "Mfile", "Rm", "Cd", "..",
    "Dir", "Date", "Time", "Wr", "Rd", "Ap", "Ren", 
    "Copy", "Find", "Info", "Tree", "Cls", "Help", "Exit"};

    public String[] Separacion(String comandoYArgumento){
        String[] cYaParse = comandoYArgumento.split(" ");
        try{
            if(cYaParse.length>2||cYaParse.length<1){   
                throw new ArgumentosInvalidosException("Cantidad de Argumentos Invalida");
             }
        }catch(ArgumentosInvalidosException e){
            System.out.println("Message");
            return null;
        }
        
     return cYaParse;
    }
    
    public boolean comandoValido(String... comando){
            for(String e: comandos){
                if(e.equals(comando[0])){
                          return true;
                }
             }
            return false;
    }
   private void validarArgumentos(Comando comando, int cantidad) throws ArgumentosInvalidosException {
        if (cantidad < comando.minArgumentos || cantidad > comando.maxArgumentos) {
            throw new ArgumentosInvalidosException(comando.nombre, comando.sintaxis());
        }
    }
    public void crearCarpeta(String ruta){
    File nuevoFile = new File(ruta);
    try{
    
    
    }catch(IOException e){
    
    }
    
    
    }
    
   
   
}
