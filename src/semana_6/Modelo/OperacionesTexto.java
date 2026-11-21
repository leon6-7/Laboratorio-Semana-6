package semana_6.Modelo;

import semana_6.Excepciones.ConsolaException;
import semana_6.Excepciones.ElementoExistenteException;
import semana_6.Excepciones.ElementoNoEncontradoException;
import semana_6.Excepciones.NombreInvalidoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OperacionesTexto {

    private final SistemaArchivos sistema;

    public OperacionesTexto(SistemaArchivos sistema) {
        this.sistema = sistema;
    }

    public String mfile(String nombreArchivo) throws ConsolaException {
        File archivo = sistema.resolverEnActual(nombreArchivo);
        if (archivo.exists()) {
            throw new ElementoExistenteException("Ya existe un archivo o carpeta con el nombre '" + nombreArchivo.trim() + "'.");
        }
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.flush();
        } catch (IOException e) {
            throw new ConsolaException("No se pudo crear el archivo '" + nombreArchivo.trim() + "': " + e.getMessage());
        }
        return "Archivo creado: " + archivo.getName();
    }

    public File obtenerArchivoExistente(String nombreArchivo) throws ConsolaException {
        File archivo = sistema.resolverEnActual(nombreArchivo);
        if (!archivo.exists()) {
            throw new ElementoNoEncontradoException("No se encontró el archivo '" + nombreArchivo.trim() + "'.");
        }
        if (archivo.isDirectory()) {
            throw new ConsolaException("'" + nombreArchivo.trim() + "' es una carpeta, no un archivo.");
        }
        return archivo;
    }

    public String wr(String nombreArchivo, String contenido) throws ConsolaException {
        File archivo = obtenerArchivoExistente(nombreArchivo);
        escribirTexto(archivo, contenido, false);
        return "Contenido escrito en " + archivo.getName() + ".";
    }

    public String wr(String nombreArchivo, List<String> lineas) throws ConsolaException {
        return wr(nombreArchivo, unirLineas(lineas));
    }

    public String ap(String nombreArchivo, String contenido) throws ConsolaException {
        File archivo = obtenerArchivoExistente(nombreArchivo);
        String textoFinal = contenido == null ? "" : contenido;
        if (archivo.length() > 0 && !textoFinal.isEmpty() && !terminaConSaltoDeLinea(archivo)) {
            textoFinal = System.lineSeparator() + textoFinal;
        }
        escribirTexto(archivo, textoFinal, true);
        return "Contenido agregado a " + archivo.getName() + ".";
    }

    public String ap(String nombreArchivo, List<String> lineas) throws ConsolaException {
        return ap(nombreArchivo, unirLineas(lineas));
    }

    private void escribirTexto(File archivo, String contenido, boolean append) throws ConsolaException {
        try (FileWriter writer = new FileWriter(archivo, append)) {
            if (contenido != null && !contenido.isEmpty()) {
                writer.write(contenido);
            }
            writer.flush();
        } catch (IOException e) {
            throw new ConsolaException("Error al escribir en el archivo '" + archivo.getName() + "': " + e.getMessage());
        }
    }

    private boolean terminaConSaltoDeLinea(File archivo) {
        char ultimo = '\0';
        try (FileReader reader = new FileReader(archivo)) {
            int c;
            while ((c = reader.read()) != -1) {
                ultimo = (char) c;
            }
        } catch (IOException e) {
            return true;
        }
        return ultimo == '\n' || ultimo == '\r';
    }

    private String unirLineas(List<String> lineas) {
        if (lineas == null || lineas.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lineas.size(); i++) {
            if (i > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(lineas.get(i));
        }
        return sb.toString();
    }

    public String rd(String nombreArchivo) throws ConsolaException {
        File archivo = obtenerArchivoExistente(nombreArchivo);
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(archivo)) {
            char[] buffer = new char[1024];
            int leidos;
            while ((leidos = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, leidos);
            }
        } catch (IOException e) {
            throw new ConsolaException("Error al leer el archivo '" + archivo.getName() + "': " + e.getMessage());
        }
        return sb.toString();
    }

    public String copy(String nombreOrigen, String nombreDestino) throws ConsolaException {
        File origen = obtenerArchivoExistente(nombreOrigen);
        File destino = sistema.resolverEnActual(nombreDestino);
        if (destino.exists()) {
            throw new ElementoExistenteException("Ya existe un archivo o carpeta con el nombre '" + nombreDestino.trim() + "'.");
        }
        try (FileReader reader = new FileReader(origen);
             FileWriter writer = new FileWriter(destino)) {
            char[] buffer = new char[1024];
            int leidos;
            while ((leidos = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, leidos);
            }
            writer.flush();
        } catch (IOException e) {
            throw new ConsolaException("Error al copiar el archivo: " + e.getMessage());
        }
        return "1 archivo(s) copiado(s): " + origen.getName() + " -> " + destino.getName();
    }

    public String grep(String texto, String nombreArchivo) throws ConsolaException {
        if (texto == null || texto.isEmpty()) {
            throw new NombreInvalidoException("Debe especificar el texto a buscar.");
        }
        File archivo = obtenerArchivoExistente(nombreArchivo);
        String criterio = texto.toLowerCase();
        StringBuilder coincidencias = new StringBuilder();
        int totalCoincidencias = 0;
        int numeroLinea = 0;

        try (FileReader fileReader = new FileReader(archivo);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                numeroLinea++;
                if (linea.toLowerCase().contains(criterio)) {
                    totalCoincidencias++;
                    coincidencias.append("Línea ").append(numeroLinea).append(": ").append(linea).append("\n");
                }
            }
        } catch (IOException e) {
            throw new ConsolaException("Error al buscar en el archivo '" + archivo.getName() + "': " + e.getMessage());
        }

        if (totalCoincidencias == 0) {
            return "No se encontraron coincidencias de \"" + texto + "\" en " + archivo.getName() + ".";
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("Buscando \"").append(texto).append("\" en ").append(archivo.getName()).append("...\n");
        resultado.append(coincidencias);
        resultado.append(totalCoincidencias).append(" coincidencia(s) encontrada(s).");
        return resultado.toString();
    }
}
