/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Control;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Abril
 */
public class ModoEscritura {

    private final File archivo;
    private final boolean agregar;
    private final List<String> lineas = new ArrayList<>();

  
    public ModoEscritura(File archivo, boolean agregar) {
        this.archivo = Objects.requireNonNull(archivo, "El archivo de destino no puede ser null");
        this.agregar = agregar;
    }

    public void agregarLinea(String linea) {
        lineas.add(linea == null ? "" : linea);
    }

    public List<String> getLineas() {
        return Collections.unmodifiableList(lineas);
    }

    public File getArchivo() {
        return archivo;
    }

    public boolean esAgregar() {
        return agregar;
    }
}
