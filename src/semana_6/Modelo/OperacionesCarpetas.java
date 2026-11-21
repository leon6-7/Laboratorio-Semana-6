package semana_6.Modelo;

import semana_6.Excepciones.ConsolaException;
import semana_6.Excepciones.ElementoExistenteException;
import semana_6.Excepciones.ElementoNoEncontradoException;
import semana_6.Excepciones.NombreInvalidoException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OperacionesCarpetas {

    private final SistemaArchivos sistema;

    public OperacionesCarpetas(SistemaArchivos sistema) {
        this.sistema = sistema;
    }

    public String mkdir(String nombre) throws ConsolaException {
        File nuevaCarpeta = sistema.resolverEnActual(nombre);
        if (nuevaCarpeta.exists()) {
            throw new ElementoExistenteException("Ya existe una carpeta o archivo con el nombre '" + nombre.trim() + "'.");
        }
        if (!nuevaCarpeta.mkdir()) {
            throw new ConsolaException("No se pudo crear la carpeta '" + nombre.trim() + "'.");
        }
        return "Carpeta creada: " + nuevaCarpeta.getName();
    }

    public String rm(String nombre) throws ConsolaException {
        File objetivo = sistema.resolverEnActual(nombre);
        if (!objetivo.exists()) {
            throw new ElementoNoEncontradoException("No se encontró el archivo o carpeta '" + nombre.trim() + "'.");
        }
        if (!eliminarRecursivo(objetivo)) {
            throw new ConsolaException("No se pudo eliminar '" + nombre.trim() + "'.");
        }
        return "Elemento eliminado: " + objetivo.getName();
    }

    private boolean eliminarRecursivo(File elemento) {
        if (elemento.isDirectory()) {
            File[] hijos = elemento.listFiles();
            if (hijos != null) {
                for (File hijo : hijos) {
                    if (!eliminarRecursivo(hijo)) {
                        return false;
                    }
                }
            }
        }
        return elemento.delete();
    }

    public String dir() {
        File actual = sistema.getCarpetaActual();
        File[] elementos = obtenerElementosOrdenados(actual);
        StringBuilder sb = new StringBuilder();
        sb.append(" Directorio de ").append(sistema.getRutaActual()).append("\n\n");

        if (elementos.length == 0) {
            sb.append("La carpeta está vacía.\n");
            sb.append("               0 archivos               0 carpetas");
            return sb.toString();
        }

        int totalArchivos = 0;
        int totalCarpetas = 0;
        long bytesArchivos = 0;

        for (File elemento : elementos) {
            String fecha = Formato.fechaModificacion(elemento);
            String tipoOTamano;
            if (elemento.isDirectory()) {
                tipoOTamano = String.format("%-14s", "<DIR>");
                totalCarpetas++;
            } else {
                long tamano = elemento.length();
                bytesArchivos += tamano;
                tipoOTamano = String.format("%14s", Formato.tamanoLegible(tamano));
                totalArchivos++;
            }
            sb.append(String.format("%-20s  %s  %s%n", fecha, tipoOTamano, elemento.getName()));
        }

        sb.append(String.format("%n%16d archivo(s) %14s%n", totalArchivos, Formato.tamanoLegible(bytesArchivos)));
        sb.append(String.format("%16d carpeta(s)", totalCarpetas));
        return sb.toString();
    }

    public String ren(String nombreActual, String nombreNuevo) throws ConsolaException {
        File origen = sistema.resolverEnActual(nombreActual);
        if (!origen.exists()) {
            throw new ElementoNoEncontradoException("No se encontró el archivo o carpeta '" + nombreActual.trim() + "'.");
        }
        File destino = sistema.resolverEnActual(nombreNuevo);
        if (destino.exists()) {
            throw new ElementoExistenteException("Ya existe un archivo o carpeta con el nombre '" + nombreNuevo.trim() + "'.");
        }
        if (!origen.renameTo(destino)) {
            throw new ConsolaException("No se pudo renombrar '" + origen.getName() + "' a '" + destino.getName() + "'.");
        }
        return "Renombrado: " + origen.getName() + " -> " + destino.getName();
    }

    public String tree() {
        File actual = sistema.getCarpetaActual();
        StringBuilder sb = new StringBuilder();
        sb.append(sistema.getRutaActual()).append("\n");
        construirArbol(actual, "", sb);
        return sb.toString().trim();
    }

    private void construirArbol(File carpeta, String prefijo, StringBuilder sb) {
        File[] elementos = obtenerElementosOrdenados(carpeta);
        for (int i = 0; i < elementos.length; i++) {
            File elemento = elementos[i];
            boolean esUltimo = (i == elementos.length - 1);
            String conector = esUltimo ? "└── " : "├── ";
            sb.append(prefijo).append(conector).append(elemento.getName());
            if (elemento.isDirectory()) {
                sb.append("/");
            }
            sb.append("\n");
            if (elemento.isDirectory()) {
                String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
                construirArbol(elemento, nuevoPrefijo, sb);
            }
        }
    }

    public String find(String texto) throws ConsolaException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new NombreInvalidoException("Debe indicar el texto a buscar.");
        }
        String criterio = texto.trim().toLowerCase();
        List<File> resultados = new ArrayList<>();
        buscarRecursivo(sistema.getCarpetaActual(), criterio, resultados);

        if (resultados.isEmpty()) {
            return "No se encontraron archivos o carpetas que contengan \"" + texto.trim() + "\".";
        }

        StringBuilder sb = new StringBuilder();
        for (File resultado : resultados) {
            String etiqueta = resultado.isDirectory() ? "[DIR]     " : "[ARCHIVO] ";
            sb.append(etiqueta).append(sistema.obtenerRutaVirtual(resultado)).append("\n");
        }
        sb.append(resultados.size()).append(" resultado(s) encontrado(s).");
        return sb.toString();
    }

    private void buscarRecursivo(File carpeta, String criterio, List<File> resultados) {
        File[] elementos = obtenerElementosOrdenados(carpeta);
        for (File elemento : elementos) {
            if (elemento.getName().toLowerCase().contains(criterio)) {
                resultados.add(elemento);
            }
            if (elemento.isDirectory()) {
                buscarRecursivo(elemento, criterio, resultados);
            }
        }
    }

    public String info(String nombre) throws ConsolaException {
        File elemento = sistema.resolverEnActual(nombre);
        if (!elemento.exists()) {
            throw new ElementoNoEncontradoException("No se encontró el archivo o carpeta '" + nombre.trim() + "'.");
        }
        String tipo = elemento.isDirectory() ? "Carpeta" : "Archivo";
        String ruta = sistema.obtenerRutaVirtual(elemento);
        long tamanoBytes = calcularTamano(elemento);
        String tamano = Formato.tamanoLegible(tamanoBytes) + " (" + tamanoBytes + " bytes)";
        String fecha = Formato.fechaModificacion(elemento);

        StringBuilder sb = new StringBuilder();
        sb.append("Nombre:              ").append(elemento.getName()).append("\n");
        sb.append("Tipo:                ").append(tipo).append("\n");
        sb.append("Ruta:                ").append(ruta).append("\n");
        sb.append("Tamaño:              ").append(tamano).append("\n");
        sb.append("Última modificación: ").append(fecha);
        return sb.toString();
    }

    private long calcularTamano(File elemento) {
        if (elemento.isFile()) {
            return elemento.length();
        }
        long total = 0;
        File[] hijos = elemento.listFiles();
        if (hijos != null) {
            for (File hijo : hijos) {
                total += calcularTamano(hijo);
            }
        }
        return total;
    }

    private File[] obtenerElementosOrdenados(File carpeta) {
        File[] elementos = carpeta.listFiles();
        if (elementos == null || elementos.length == 0) {
            return new File[0];
        }
        Arrays.sort(elementos, new Comparator<File>() {
            @Override
            public int compare(File a, File b) {
                if (a.isDirectory() && !b.isDirectory()) {
                    return -1;
                }
                if (!a.isDirectory() && b.isDirectory()) {
                    return 1;
                }
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });
        return elementos;
    }
}
