package archivos;

import clases.Contacto;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Archivo {

    private Contacto contacto;
    private File file = null;
    private File dir = null;
    private JFileChooser fileChooser;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de Texto", "txt");
    private ArrayList<String> contenido = new ArrayList<>();
    private boolean isOpen = false; // Bandera de control para saber si se abrió un archivo

    // Constructor de clase
    public Archivo() {
        this.dir = new File("datos\\");
        dir.mkdirs();
        this.isOpen = false;
    }

    public Archivo(String path, boolean condicion) {
        this.dir = new File(path);
        dir.mkdirs();
        this.isOpen = condicion;
    }

    public Archivo(String nombre) {
        this.dir = new File("datos\\");
        dir.mkdirs();
        this.file = new File("datos\\" + nombre + ".txt");
        this.isOpen = true;
    }

    public String[] listarArchivosExt() {
        return dir.list() != null ? dir.list() : new String[]{""};
    }

    public String[] listarArchivos() {
        return listarArchivos("datos\\");
    }

    public String[] listarArchivos(String path) {
        this.dir = new File(path);
        dir.mkdirs();

        File[] listaArchivos = dir.listFiles();
        if (listaArchivos == null) {
            return new String[]{""};
        }

        String[] salida = new String[listaArchivos.length];
        for (int i = 0; i < listaArchivos.length; i++) {
            salida[i] = listaArchivos[i].getName().replace(".txt", "");
        }
        return salida;
    }

    public String getFileName() {
        return file != null ? file.getName() : "Sin Titulo";
    }

    public void extExiste(File archivo) {
        if (!archivo.getName().endsWith(".txt")) {
            archivo = new File(archivo + ".txt");
        }
    }

    public void guardar(String nombre, String datos) {
        this.file = new File("datos\\" + nombre + ".txt");
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            writer.println(datos);
            JOptionPane.showMessageDialog(null, "Archivo '" + file.getName() + "' guardado ");
            this.isOpen = true;
        } catch (IOException ex) {
            System.err.println("Error al guardar el archivo: " + ex.getMessage());
        }
    }

    public void guardarComo(String path, String nombre, String datos) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path + nombre + ".txt", false)))) {
            writer.println(datos);
            JOptionPane.showMessageDialog(null, "Archivo '" + nombre + ".txt' guardado como nuevo.");
            this.isOpen = true;
        } catch (IOException ex) {
            System.err.println("Error al crear el archivo: " + ex.getMessage());
        }
    }

    public boolean eliminar(String texto) {
        String mensaje;
        this.file = new File("datos\\" + texto + ".txt");

        if (file.exists() && file.delete()) {
            mensaje = "El fichero ha sido borrado satisfactoriamente";
            JOptionPane.showMessageDialog(null, mensaje);
            return true;
        } else {
            mensaje = "El fichero no puede ser borrado";
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
    }

    public Contacto consulta(String texto) {
        if (!existe(texto)) {
            JOptionPane.showMessageDialog(null, "La información requerida no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try (BufferedReader buferLectura = new BufferedReader(new FileReader("datos\\" + texto + ".txt"))) {
            String comparar;
            while ((comparar = buferLectura.readLine()) != null) {
                String[] tokens = comparar.split(","); // Usar split en lugar de StringTokenizer
                for (String token : tokens) {
                    contenido.add(token.trim());
                }
                convReg();
            }
            this.isOpen = true;
        } catch (IOException e) {
            System.err.println("Error al tratar de leer el archivo: " + e.getMessage());
        }

        return this.contacto;
    }

    private void convReg() {
        if (!contenido.isEmpty()) {
            this.contacto = new Contacto();
            this.contacto.setCedula(contenido.get(0));
            this.contacto.setNombre(contenido.get(1));
            this.contacto.setApellidos(contenido.get(2));
            this.contacto.setfNacimiento(contenido.get(3));
            this.contacto.setProvincia(contenido.get(4));
            this.contacto.setTelefono(contenido.get(5));
            this.contacto.setCorreo(contenido.get(6));
        } else {
            JOptionPane.showMessageDialog(null, "La información requerida no existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean existe(String texto) {
        this.file = new File("datos\\" + texto + ".txt");
        return file.exists();
    }

    public void importar() {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            this.file = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Archivo importado: " + file.getName());
        }
    }

    public void abrir(String nombre) {
        this.file = new File("datos\\" + nombre + ".txt");

        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No fue posible abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Contacto consultaImportar() {
        String comparar;
        String token = "";
        importar(); // Llama al método para importar el archivo

        if (file != null) {
            try (BufferedReader buferLectura = new BufferedReader(new FileReader(this.file))) {
                while ((comparar = buferLectura.readLine()) != null) {
                    String[] tokens = comparar.split(","); // Usar split en lugar de StringTokenizer
                    for (String t : tokens) {
                        contenido.add(t.trim());
                    }
                    convReg(); // Convierte registros en objeto Contacto
                }
                this.isOpen = true;
            } catch (IOException e) {
                System.err.println("Error al tratar de leer el archivo: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha importado ningún archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return this.contacto;
    }

    public void actualizar(String nombre, String datos) {
        if (this.isOpen) {
            guardar(nombre, datos);
        } else {
            JOptionPane.showMessageDialog(null, "No hay un archivo abierto para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
