/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana_6.Control;
import semana_6.Excepciones.*;
import semana_6.Modelo.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Abril
 */
public class Interprete {

    private static final String PALABRA_FIN = "EXIT";

    private final SistemaArchivos sistema;
    private final OperacionesCarpetas carpetas;
    private final OperacionesTexto texto;

    private ModoEscritura modoEscritura;

    public Interprete(SistemaArchivos sistema) {
        this.sistema = sistema;
        this.carpetas = new OperacionesCarpetas(sistema);
        this.texto = new OperacionesTexto(sistema);
    }

    public ResultadoComando ejecutar(String linea) {
        try {
            if (modoEscritura != null) {
                return procesarLineaEscritura(linea == null ? "" : linea);
            }

            return ejecutarComando(linea);

        } catch (ConsolaException ex) {
            return ResultadoComando.texto(ex.getMessage());

        } catch (RuntimeException ex) {
            modoEscritura = null;
            return ResultadoComando.texto("Error inesperado: " + ex.getMessage());
        }
    }

    public String getPrompt() {
        return sistema.getPrompt();
    }

    public boolean estaEnModoEscritura() {
        return modoEscritura != null;
    }

    private ResultadoComando ejecutarComando(String linea) throws ConsolaException {
        String limpia = linea == null ? "" : linea.trim();

        if (limpia.isEmpty()) {
            return ResultadoComando.texto("");
        }

        String[] partes = limpia.split("\\s+");
        Comando comando = Comando.de(partes[0]);

        if (comando == null) {
            throw new ComandoDesconocidoException(partes[0]);
        }

        List<String> argumentos = Arrays.asList(partes).subList(1, partes.length);

        validarArgumentos(comando, argumentos.size());

        return despachar(comando, argumentos, limpia);
    }

    private void validarArgumentos(Comando comando, int cantidad)
            throws ArgumentosInvalidosException {

        if (cantidad < comando.minArgumentos || cantidad > comando.maxArgumentos) {
            throw new ArgumentosInvalidosException(comando.nombre, comando.sintaxis());
        }
    }

    private ResultadoComando despachar(Comando comando, List<String> args, String linea)
            throws ConsolaException {

        return switch (comando) {

            case MKDIR -> ResultadoComando.texto(carpetas.mkdir(args.get(0)));

            case MFILE -> ResultadoComando.texto(texto.mfile(args.get(0)));

            case RM -> ResultadoComando.texto(carpetas.rm(args.get(0)));

            case CD -> {
                sistema.cd(args.get(0));
                yield ResultadoComando.texto("");
            }

            case ATRAS -> {
                sistema.regresar();
                yield ResultadoComando.texto("");
            }

            case DIR -> ResultadoComando.texto(carpetas.dir());

            case DATE -> ResultadoComando.texto(
                    "La fecha actual es: " + Formato.date());

            case TIME -> ResultadoComando.texto(
                    "La hora actual es: " + Formato.time());

            case WR -> iniciarEscritura(args.get(0), false);

            case RD -> ResultadoComando.texto(texto.rd(args.get(0)));

            case AP -> iniciarEscritura(args.get(0), true);

            case REN -> ResultadoComando.texto(
                    carpetas.ren(args.get(0), args.get(1)));

            case COPY -> ResultadoComando.texto(
                    texto.copy(args.get(0), args.get(1)));

            case FIND -> ResultadoComando.texto(carpetas.find(args.get(0)));

            case INFO -> ResultadoComando.texto(carpetas.info(args.get(0)));

            case TREE -> ResultadoComando.texto(carpetas.tree());

            case CLS -> ResultadoComando.limpiar();

            case HELP -> ResultadoComando.texto(construirAyuda());

            case EXIT -> ResultadoComando.salir();

            case GREP -> ResultadoComando.texto(
                    texto.grep(extraerTextoGrep(linea, comando),
                            args.get(args.size() - 1)));
        };
    }

    private String extraerTextoGrep(String linea, Comando comando) {
        String resto = linea.substring(comando.nombre.length()).trim();

        int i = resto.length() - 1;

        while (i >= 0 && !Character.isWhitespace(resto.charAt(i))) {
            i--;
        }

        return resto.substring(0, i).trim();
    }

    private ResultadoComando iniciarEscritura(String nombre, boolean agregar)
            throws ConsolaException {

        texto.obtenerArchivoExistente(nombre);
        modoEscritura = new ModoEscritura(
                texto.obtenerArchivoExistente(nombre), agregar);

        String intro = agregar
                ? "Escribe el texto que se agregará a " + nombre + "."
                : "Escribe el texto de " + nombre + " (se reemplazará su contenido).";

        return ResultadoComando.texto(
                intro + "\nEscribe EXIT en una línea para terminar.");
    }

    private ResultadoComando procesarLineaEscritura(String linea)
            throws ConsolaException {

        if (!linea.equals(PALABRA_FIN)) {
            modoEscritura.agregarLinea(linea);
            return ResultadoComando.texto("");
        }

        ModoEscritura modo = modoEscritura;
        modoEscritura = null;

        String resultado;

        if (modo.esAgregar()) {
            resultado = texto.ap(modo.getArchivo().getName(), modo.getLineas());
        } else {
            resultado = texto.wr(modo.getArchivo().getName(), modo.getLineas());
        }

        return ResultadoComando.texto(resultado);
    }

    private String construirAyuda() {
        int ancho = 0;

        for (Comando comando : Comando.values()) {
            ancho = Math.max(ancho, comando.sintaxis().length());
        }

        StringBuilder ayuda = new StringBuilder("Comandos disponibles:");

        for (Comando comando : Comando.values()) {
            ayuda.append('\n').append(
                    String.format("%-" + (ancho + 3) + "s%s",
                            comando.sintaxis(), comando.descripcion));
        }

        return ayuda.toString();
    }
}
