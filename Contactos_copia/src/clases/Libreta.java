package clases;

import archivos.Archivo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Clase Libreta que gestiona una colección de contactos.
 * Permite realizar operaciones como agregar, eliminar, actualizar y buscar contactos.
 */
public class Libreta {

    private ArrayList<Contacto> libreta; // Lista de contactos
    private Archivo archivo; // Manejo de archivos para guardar y cargar contactos
    private String[] listaContactos; // Lista de nombres de archivos de contactos

    /**
     * Constructor por defecto que inicializa la libreta.
     */
    public Libreta() {
        archivo = new Archivo("", true);
        llenarLibreta(); // Carga los contactos desde los archivos existentes
    }

    /**
     * Llena la libreta con los contactos almacenados en archivos.
     */
    public void llenarLibreta() {
        if (archivo == null) {
            archivo = new Archivo("", true);
        }
        listaContactos = archivo.listarArchivos(); // Obtiene la lista de archivos
        System.out.println(Arrays.toString(listaContactos));
        libreta = new ArrayList<>();
        
        // Carga cada contacto desde su archivo correspondiente
        for (String listaContacto : listaContactos) {
            archivo = new Archivo(listaContacto);
            libreta.add(archivo.consulta(listaContacto)); // Consulta el contacto y lo añade a la libreta
        }
    }

    /**
     * Lista todos los contactos en forma de cadena.
     *
     * @return Un arreglo de cadenas representando los contactos.
     */
    public String[] listarLibreta() {
        String[] sal = new String[libreta.size()];
        for (int i = 0; i < libreta.size(); i++) {
            sal[i] = libreta.get(i).toString(); // Convierte cada contacto a cadena
        }
        return sal;
    }

    /**
     * Lista las cédulas de todos los contactos en la libreta.
     *
     * @return Un arreglo de cadenas con las cédulas.
     */
    public String[] listarCedulas() {
        String[] sal = new String[libreta.size()];
        for (int i = 0; i < libreta.size(); i++) {
            sal[i] = libreta.get(i).getCedula(); // Obtiene la cédula de cada contacto
        }
        return sal;
    }

    /**
     * Lista los archivos disponibles en el sistema.
     *
     * @return Un arreglo de nombres de archivos.
     */
    public String[] listarArchivos() {
        return archivo.listarArchivos();
    }

    /**
     * Elimina un contacto basado en su cédula.
     *
     * @param cedula Cédula del contacto a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarContacto(String cedula) {
        boolean eli = archivo.eliminar(cedula); // Llama al método eliminar del archivo
        llenarLibreta(); // Recarga la libreta después de eliminar
        return eli;
    }

    /**
     * Actualiza un contacto existente en la libreta.
     *
     * @param c Objeto Contacto con la información actualizada.
     */
    public void actualizarContacto(Contacto c) {        
        archivo = new Archivo(c.getCedula()); // Crea un nuevo objeto Archivo para el contacto
        this.archivo.actualizar(c.getCedula(), c.toString()); // Actualiza el archivo del contacto
        llenarLibreta(); // Recarga la libreta después de actualizar
    }

    /**
     * Busca contactos que contengan el texto especificado.
     *
     * @param texto Texto a buscar en los contactos.
     * @return Una lista de Contactos que coinciden con la búsqueda.
     */
    public ArrayList<Contacto> buscarArchivos(String texto) {
        System.out.println(texto);
        ArrayList<Contacto> nuevaLibreta = new ArrayList<>();
        
        for (Iterator<Contacto> iterator = libreta.iterator(); iterator.hasNext();) {
            Contacto next = iterator.next();
            if (next.toString().toLowerCase().contains(texto.toLowerCase())) { // Busca coincidencias ignorando mayúsculas/minúsculas
                nuevaLibreta.add(next); // Añade el contacto a la nueva lista si coincide
            }
        }
        
        this.libreta = nuevaLibreta; // Actualiza la libreta actual a la nueva lista filtrada
        return nuevaLibreta;
    }

    /**
     * Guarda todos los contactos en un archivo llamado "Libreta".
     */
    public void guardarLibreta() {
        for (int i = 0; i < libreta.size(); i++) {
            archivo.guardarComo("", "Libreta", libreta.get(i).toString()); // Guarda cada contacto en el archivo "Libreta"
        }
    }

    /**
     * Obtiene la cédula del contacto en una posición específica.
     *
     * @param pos Posición del contacto en la lista.
     * @return La cédula del contacto o una cadena vacía si no existe.
     */
    public String getCedula(int pos) {
        return (pos < libreta.size()) ? this.libreta.get(pos).getCedula() : ""; // Devuelve la cédula o vacío si está fuera de rango
    }

    @Override
    public String toString() {
        System.out.println(Arrays.toString(libreta.toArray()));
        return "Libreta{" + "libreta=" + Arrays.toString(libreta.toArray()) + ", listaContactos=" + Arrays.toString(listaContactos) + '}';
    }

    /**
     * Método principal para probar la clase Libreta.
     *
     * @param arg Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String arg[]) {
        Libreta l = new Libreta();
        System.out.println(l.buscarArchivos("Cartago")); // Busca contactos que contengan "Cartago"
        // l.guardarLibreta(); // Descomentar para guardar la libreta
    }
}