package archivos;

import clases.Contacto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase XArchivo que gestiona operaciones de lectura y escritura
 * de archivos de texto relacionados con objetos Contacto.
 */
public class XArchivo {

    private Contacto contacto; // Objeto Contacto que se manipulará
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de Texto", "txt");
    private File file = null; // Archivo actual
    private boolean isopen = false; // Bandera para saber si se abrió un archivo
    private ArrayList<String> contenido = new ArrayList<>(); // Almacena los registros leídos de *.txt
    private int index = 0; // Control del registro actualmente visible

    // Constructor por defecto
    public XArchivo() {
    }

    /**
     * Constructor que inicializa el archivo con un nombre específico.
     *
     * @param nombre Nombre del archivo a crear o abrir.
     */
    public XArchivo(String nombre) {
        file = new File("/datos/" + nombre + ".txt");
        System.out.print("Implementado por Edwin Esneyder");
    }

    /**
     * Retorna el nombre del archivo abierto.
     *
     * @return Nombre del archivo o "Sin Titulo" si no hay archivo abierto.
     */
    public String getFileName() {
        return (file != null) ? file.getName() : "Sin Titulo";
    }

    /**
     * Guarda el contenido en un nuevo archivo con el nombre especificado.
     *
     * @param texto Nombre del archivo a guardar (sin extensión).
     */
    public void GuardarComo(String texto) {
        file = new File("/datos/" + texto + ".txt");
        this.isopen = false;
        this.contenido.clear();
        this.index = 1;
        JOptionPane.showMessageDialog(null, "Archivo '" + file.getName() + "' guardado.");
        this.isopen = true;
    }

    /**
     * Guarda el contenido en el archivo actual.
     *
     * @param texto Nombre del archivo a guardar (sin extensión).
     */
    public void Guardar(String texto) {
        file = new File("/datos/" + texto + ".txt");
        this.isopen = false;
        this.contenido.clear();
        this.index = 1;
        JOptionPane.showMessageDialog(null, "Archivo '" + file.getName() + "' guardado.");
        this.isopen = true;
    }

    /**
     * Actualiza un nuevo registro al final del archivo.
     *
     * @param texto Contenido a agregar al archivo.
     */
    public void Actualizar(String texto) {
        if (this.file != null) {
            if (escribir(this.file, texto)) {
                JOptionPane.showMessageDialog(null, "Archivo '" + this.file.getName() + "' actualizado.");
            }
        } else {
            GuardarComo(texto); // Si no hay archivo abierto, guarda como nuevo
        }
    }

    /**
     * Abre un archivo basado en la cédula proporcionada.
     *
     * @param cedula Cédula utilizada para abrir el archivo correspondiente.
     */
    public void Abrir(String cedula) {
        this.file = new File(cedula + ".txt");
        leer(this.file);
        this.isopen = true;
    }

    /**
     * Escribe un registro en el archivo de texto especificado.
     *
     * @param fichero Archivo donde se escribirá el contenido.
     * @param texto Contenido a escribir en el archivo.
     * @return true si la escritura fue exitosa, false en caso contrario.
     */
    private boolean escribir(File fichero, String texto) {
        boolean res = false;
        PrintWriter writer = null;

        try {
            // Verifica que la extensión exista, si no, la agrega
            if (!fichero.getName().endsWith(".txt")) {
                fichero = new File(fichero + ".txt");
            }
            writer = new PrintWriter(fichero);

            // Si hay un archivo abierto, añade contenido anterior
            if (this.isopen) {
                for (String line : contenido) {
                    writer.println(line);
                }
                writer.println(texto); // Añade nueva línea al final
                this.contenido.add(texto);
            } else { // Guardando por primera vez
                this.contenido.add(texto);
                writer.println(texto);
            }
            this.file = fichero; // Actualiza el archivo actual
            res = true; // Indica que la operación fue exitosa
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex);
        } finally {
            if (writer != null) {
                writer.close(); // Asegura que el escritor se cierre
            }
        }
        
        return res; // Retorna resultado de la operación
    }

    /**
     * Lee un archivo línea por línea y almacena los registros en un ArrayList.
     *
     * @param fichero Archivo a leer.
     * @return true si la lectura fue exitosa, false en caso contrario.
     */
    public boolean leer(File fichero) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fichero));
            this.contenido.clear();
            String linea;

            while ((linea = reader.readLine()) != null) {
                this.contenido.add(linea); // Almacena cada línea leída
            }
            Siguiente(); // Muestra el primer registro en la interfaz
            return true; // Indica que la lectura fue exitosa
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close(); // Asegura que el lector se cierre
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
        }
        
        return false; // Indica que hubo un error en la lectura
    }

    /**
     * Avanza al siguiente registro del ArrayList y lo muestra en pantalla.
     */
    public void Siguiente() {
        contacto = new Contacto();
        
        if (this.file != null) {
            // Incrementa el índice y reinicia si supera el tamaño del contenido
            this.index = (index >= contenido.size()) ? 1 : index + 1;

            int count = 1;
            for (String tmp : contenido) { 
                if (count == index) { 
                    String[] datos = tmp.split(","); // Separa el registro por campos
                    this.contacto.setCedula(datos[0]);                    
                    this.contacto.setNombre(datos[1]);                    
                    this.contacto.setApellidos(datos[2]);
                    break; 
                }
                count++;
            }
        }
    }
}