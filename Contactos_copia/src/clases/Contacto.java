/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Fabián
 */
/**
 * Clase Contacto que representa un contacto en la libreta de direcciones.
 * Contiene información personal como cédula, nombre, apellidos, fecha de
 * nacimiento, provincia, teléfono y correo electrónico.
 */
public class Contacto {

    private String cedula; // Cédula del contacto
    private String nombre; // Nombre del contacto
    private String apellidos; // Apellidos del contacto
    private String fNacimiento; // Fecha de nacimiento del contacto
    private String provincia; // Provincia del contacto
    private String telefono; // Teléfono del contacto
    private String correo; // Correo electrónico del contacto

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cédula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the fNacimiento
     */
    public String getfNacimiento() {
        return fNacimiento;
    }

    /**
     * @param fNacimiento the fNacimiento to set
     */
    public void setfNacimiento(String fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param teléfono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Contacto() {
    }

    /**
     * Constructor que inicializa todos los atributos del contacto.
     *
     * @param cedula Cédula del contacto.
     * @param nombre Nombre del contacto.
     * @param apellidos Apellidos del contacto.
     * @param fNacimiento Fecha de nacimiento del contacto.
     * @param provincia Provincia del contacto.
     * @param telefono Teléfono del contacto.
     * @param correo Correo electrónico del contacto.
     */
    public Contacto(String cedula, String nombre, String apellidos, String fNacimiento, String provincia, String telefono, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fNacimiento = fNacimiento;
        this.provincia = provincia;
        this.telefono = telefono;
        this.correo = correo;
    }

    @Override
    public String toString() {
        return cedula + "," + nombre + "," + apellidos + "," + fNacimiento + "," + provincia + "," + telefono + "," + correo;
    }
}
