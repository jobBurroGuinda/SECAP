/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Expediente;

import Logica.Persona;

/**
 *
 * @author Job
 */
public class Paciente extends Persona {
    
    private int idPaciente;
    private String ocupacion;
    private String fechaNacimeinto;
    
    private int idContacto;
    private String domicilio;
    private Long telefono;
    private Long telefono2;
    private String email;
    private String facebook;
    
    private int edad;
    
    private int idSexo;
    private String sexo;

    public Paciente() {
    }

    public Paciente(String nombre, String apellidoPaterno, String apellidoMaterno) {
        super(nombre, apellidoPaterno, apellidoMaterno);
    }

    public Paciente(int idPaciente, String ocupacion, String fechaNacimeinto, int idContacto, String domicilio, Long telefono, Long telefono2, String email, String facebook, int idSexo, String sexo) {
        this.idPaciente = idPaciente;
        this.ocupacion = ocupacion;
        this.fechaNacimeinto = fechaNacimeinto;
        this.idContacto = idContacto;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.email = email;
        this.facebook = facebook;
        this.idSexo = idSexo;
        this.sexo = sexo;
    }

    public Paciente(int idPaciente, String ocupacion, String fechaNacimeinto, String domicilio, Long telefono, Long telefono2, String email, String facebook, int idSexo) {
        this.idPaciente = idPaciente;
        this.ocupacion = ocupacion;
        this.fechaNacimeinto = fechaNacimeinto;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.email = email;
        this.facebook = facebook;
        this.idSexo = idSexo;
    }

    public Paciente(int idPaciente, String ocupacion, String fechaNacimeinto, String domicilio, Long telefono, Long telefono2, String email, String facebook, int idSexo, String nombre, String apellidoPaterno, String apellidoMaterno) {
        super(nombre, apellidoPaterno, apellidoMaterno);
        this.idPaciente = idPaciente;
        this.ocupacion = ocupacion;
        this.fechaNacimeinto = fechaNacimeinto;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.email = email;
        this.facebook = facebook;
        this.idSexo = idSexo;
    }

    public Paciente(int idPaciente, String ocupacion, String fechaNacimeinto, int idContacto, String domicilio, Long telefono, Long telefono2, String email, String facebook, int idSexo, String sexo, String nombre, String apellidoPaterno, String apellidoMaterno) {
        super(nombre, apellidoPaterno, apellidoMaterno);
        this.idPaciente = idPaciente;
        this.ocupacion = ocupacion;
        this.fechaNacimeinto = fechaNacimeinto;
        this.idContacto = idContacto;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.email = email;
        this.facebook = facebook;
        this.idSexo = idSexo;
        this.sexo = sexo;
    }

    public Paciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    
    
    /**
     * @return the idPaciente
     */
    public int getIdPaciente() {
        return idPaciente;
    }

    /**
     * @param idPaciente the idPaciente to set
     */
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * @return the ocupacion
     */
    public String getOcupacion() {
        return ocupacion;
    }

    /**
     * @param ocupacion the ocupacion to set
     */
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    /**
     * @return the fechaNacimeinto
     */
    public String getFechaNacimeinto() {
        return fechaNacimeinto;
    }

    /**
     * @param fechaNacimeinto the fechaNacimeinto to set
     */
    public void setFechaNacimeinto(String fechaNacimeinto) {
        this.fechaNacimeinto = fechaNacimeinto;
    }

    /**
     * @return the idContacto
     */
    public int getIdContacto() {
        return idContacto;
    }

    /**
     * @param idContacto the idContacto to set
     */
    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    /**
     * @return the domicilio
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * @param domicilio the domicilio to set
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * @return the telefono
     */
    public Long getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the telefono2
     */
    public Long getTelefono2() {
        return telefono2;
    }

    /**
     * @param telefono2 the telefono2 to set
     */
    public void setTelefono2(Long telefono2) {
        this.telefono2 = telefono2;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the facebook
     */
    public String getFacebook() {
        return facebook;
    }

    /**
     * @param facebook the facebook to set
     */
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    /**
     * @return the idSexo
     */
    public int getIdSexo() {
        return idSexo;
    }

    /**
     * @param idSexo the idSexo to set
     */
    public void setIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    
    
   
    
    
}
