/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Control;

/**
 *
 * @author Abril
 */
 public enum Comando {
        MKDIR("Mkdir", "<nombre>", "Crea una nueva carpeta.", 1, 1),
        MFILE("Mfile", "<nombre.ext>", "Crea un nuevo archivo.", 1, 1),
        RM("Rm", "<nombre>", "Elimina un archivo o carpeta (y su contenido).", 1, 1),
        CD("Cd", "<nombre carpeta>", "Cambia a la carpeta indicada.", 1, 1),
        ATRAS("..", "", "Regresa a la carpeta anterior.", 0, 0),
        DIR("Dir", "", "Lista los archivos y carpetas de la carpeta actual.", 0, 0),
        DATE("Date", "", "Muestra la fecha actual del sistema.", 0, 0),
        TIME("Time", "", "Muestra la hora actual del sistema.", 0, 0),
        WR("Wr", "<archivo.ext>", "Escribe texto en un archivo (termina con EXIT).", 1, 1),
        RD("Rd", "<archivo.ext>", "Lee y muestra el contenido de un archivo.", 1, 1),
        AP("Ap", "<archivo.ext>", "Agrega texto al final de un archivo (termina con EXIT).", 1, 1),
        REN("Ren", "<actual> <nuevo>", "Renombra un archivo o carpeta.", 2, 2),
        COPY("Copy", "<origen> <destino>", "Copia el contenido de un archivo a uno nuevo.", 2, 2),
        FIND("Find", "<nombre>", "Busca archivos o carpetas por nombre (incluye subcarpetas).", 1, 1),
        INFO("Info", "<nombre>", "Muestra tipo, ruta, tamaño y fecha de un archivo o carpeta.", 1, 1),
        TREE("Tree", "", "Muestra la estructura de carpetas y archivos en forma de árbol.", 0, 0),
        CLS("Cls", "", "Limpia la pantalla.", 0, 0),
        HELP("Help", "", "Muestra la lista de comandos disponibles.", 0, 0),
        EXIT("Exit", "", "Cierra la aplicación.", 0, 0);

        public final String nombre;
        public final String parametros;
        public final String descripcion;
        public final int minArgumentos;
        public final int maxArgumentos;

        Comando(String nombre, String parametros, String descripcion, int minArgumentos, int maxArgumentos) {
            this.nombre = nombre;
            this.parametros = parametros;
            this.descripcion = descripcion;
            this.minArgumentos = minArgumentos;
            this.maxArgumentos = maxArgumentos;
        }
        
        String sintaxis() {
            if (parametros.isEmpty()) {
                return nombre;
            }else {
                return nombre + " " + parametros;
            }
        }

        static Comando de(String nombre) {
            for (Comando comando : values()) {
                if (comando.nombre.equals(nombre)) {
                    return comando;
                }
            }
            return null;
        }
    }
 