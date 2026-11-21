package semana_6.Modelo;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Formato {

    private static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String FORMATO_HORA = "HH:mm:ss";
    private static final DecimalFormat DECIMAL = new DecimalFormat("0.00");

    private Formato() {
    }

    public static String tamanoLegible(long bytes) {
        if (bytes < 0) {
            return "0 B";
        }
        if (bytes < 1024) {
            return bytes + " B";
        }
        double kb = bytes / 1024.0;
        if (kb < 1024) {
            return DECIMAL.format(kb) + " KB";
        }
        double mb = kb / 1024.0;
        if (mb < 1024) {
            return DECIMAL.format(mb) + " MB";
        }
        double gb = mb / 1024.0;
        return DECIMAL.format(gb) + " GB";
    }

    public static String fechaModificacion(long milisegundos) {
        if (milisegundos <= 0) {
            return "N/D";
        }
        return new SimpleDateFormat(FORMATO_FECHA_HORA).format(new Date(milisegundos));
    }

    public static String fechaModificacion(File archivo) {
        if (archivo == null || !archivo.exists()) {
            return "N/D";
        }
        return fechaModificacion(archivo.lastModified());
    }

    public static String date() {
        return "La fecha actual es: " + new SimpleDateFormat(FORMATO_FECHA).format(new Date());
    }

    public static String time() {
        return "La hora actual es: " + new SimpleDateFormat(FORMATO_HORA).format(new Date());
    }
}
