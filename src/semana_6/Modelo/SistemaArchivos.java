package semana_6.Modelo;

import semana_6.Excepciones.ConsolaException;
import semana_6.Excepciones.ElementoNoEncontradoException;
import semana_6.Excepciones.NombreInvalidoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SistemaArchivos {

    private static final String NOMBRE_RAIZ_POR_DEFECTO = "ConsolaSimulada";
    private static final String UNIDAD_SIMULADA = "C:\\";
    private static final String CARACTERES_INVALIDOS = "\\/:*?\"<>|";

    private final File carpetaRaiz;
    private File carpetaActual;

    public SistemaArchivos() {
        this(new File(NOMBRE_RAIZ_POR_DEFECTO));
    }

    public SistemaArchivos(File carpetaRaiz) {
        File raizAbsoluta = obtenerCanonicaOAbsoluta(carpetaRaiz);
        if (!raizAbsoluta.exists()) {
            raizAbsoluta.mkdirs();
        }
        this.carpetaRaiz = raizAbsoluta;
        this.carpetaActual = raizAbsoluta;
    }

    private File obtenerCanonicaOAbsoluta(File archivo) {
        try {
            return archivo.getCanonicalFile();
        } catch (IOException e) {
            return archivo.getAbsoluteFile();
        }
    }

    public File getCarpetaRaiz() {
        return carpetaRaiz;
    }

    public File getCarpetaActual() {
        return carpetaActual;
    }

    public String getRutaActual() {
        return obtenerRutaVirtual(carpetaActual);
    }

    public String getPrompt() {
        return getRutaActual() + ">";
    }

    public String obtenerRutaVirtual(File archivo) {
        if (archivo == null) {
            return UNIDAD_SIMULADA + carpetaRaiz.getName();
        }
        File objetivo = obtenerCanonicaOAbsoluta(archivo);
        List<String> segmentos = new ArrayList<>();
        File cursor = objetivo;
        while (cursor != null && !cursor.equals(carpetaRaiz)) {
            segmentos.add(0, cursor.getName());
            cursor = cursor.getParentFile();
        }
        StringBuilder sb = new StringBuilder(UNIDAD_SIMULADA).append(carpetaRaiz.getName());
        for (String segmento : segmentos) {
            sb.append("\\").append(segmento);
        }
        return sb.toString();
    }

    public void validarNombre(String nombre) throws NombreInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre no puede estar vacío.");
        }
        String limpio = nombre.trim();
        if (limpio.equals(".") || limpio.equals("..")) {
            throw new NombreInvalidoException("El nombre '" + limpio + "' no es válido.");
        }
        for (int i = 0; i < CARACTERES_INVALIDOS.length(); i++) {
            char c = CARACTERES_INVALIDOS.charAt(i);
            if (limpio.indexOf(c) >= 0) {
                throw new NombreInvalidoException("El nombre contiene caracteres no permitidos: " + c);
            }
        }
    }

    public boolean estaDentroDeRaiz(File archivo) {
        if (archivo == null) {
            return false;
        }
        File objetivo = obtenerCanonicaOAbsoluta(archivo);
        File cursor = objetivo;
        while (cursor != null) {
            if (cursor.equals(carpetaRaiz)) {
                return true;
            }
            cursor = cursor.getParentFile();
        }
        return false;
    }

    public File resolverEnActual(String nombre) throws ConsolaException {
    validarNombre(nombre);

    File archivo = new File(carpetaActual, nombre.trim());
    File archivoNormalizado = obtenerCanonicaOAbsoluta(archivo);

    if (!estaDentroDeRaiz(archivoNormalizado)) {
        throw new ConsolaException("No se permite acceder fuera de la carpeta raíz.");
    }

        return archivoNormalizado;
    }

    public void cd(String nombreCarpeta) throws ConsolaException {
        validarNombre(nombreCarpeta);
        File destino = new File(carpetaActual, nombreCarpeta.trim());
        if (!destino.exists()) {
            throw new ElementoNoEncontradoException("El sistema no puede encontrar la carpeta especificada: " + nombreCarpeta);
        }
        if (!destino.isDirectory()) {
            throw new ConsolaException("El nombre de directorio no es válido: " + nombreCarpeta);
        }
        File destinoNormalizado = obtenerCanonicaOAbsoluta(destino);
        if (!estaDentroDeRaiz(destinoNormalizado)) {
            throw new ConsolaException("No se permite salir de la carpeta raíz.");
        }
        this.carpetaActual = destinoNormalizado;
    }

    public void regresar() {
        if (carpetaActual.equals(carpetaRaiz)) {
            return;
        }
        File padre = carpetaActual.getParentFile();
        if (padre != null && estaDentroDeRaiz(padre)) {
            this.carpetaActual = obtenerCanonicaOAbsoluta(padre);
        }
    }
}
