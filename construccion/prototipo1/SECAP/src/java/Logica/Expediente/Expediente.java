/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Expediente;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Job
 */
public class Expediente extends Paciente {
    
    private int idExpediente;
    private String folio;
    private int idFechasExpediente;
    private String fechaExpedicion;
    private String fechaActualizacion;

    public Expediente() {
    }

    public Expediente(int idExpediente, String folio, String fechaExpedicion, String fechaActualizacion) {
        this.idExpediente = idExpediente;
        this.folio = folio;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Expediente(String folio, String fechaExpedicion, String fechaActualizacion, int idPaciente) {
        super(idPaciente);
        this.folio = folio;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Expediente(int idPaciente, String ocupacion, String fechaNacimeinto, int idContacto, String domicilio, Long telefono, Long telefono2, String email, String facebook, int idSexo, String sexo, String nombre, String apellidoPaterno, String apellidoMaterno) {
        super(idPaciente, ocupacion, fechaNacimeinto, idContacto, domicilio, telefono, telefono2, email, facebook, idSexo, sexo, nombre, apellidoPaterno, apellidoMaterno);
    }

    public Expediente(int idExpediente, String folio, String fechaExpedicion, String fechaActualizacion, int idPaciente, String ocupacion, String fechaNacimeinto, int idContacto, String domicilio, Long telefono, Long telefono2, String email, String facebook, int idSexo, String sexo, String nombre, String apellidoPaterno, String apellidoMaterno) {
        super(idPaciente, ocupacion, fechaNacimeinto, idContacto, domicilio, telefono, telefono2, email, facebook, idSexo, sexo, nombre, apellidoPaterno, apellidoMaterno);
        this.idExpediente = idExpediente;
        this.folio = folio;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Expediente(String folio, String fechaExpedicion, String fechaActualizacion, int idPaciente, String ocupacion, String fechaNacimeinto, int idContacto, String domicilio, Long telefono, Long telefono2, String email, String facebook, int idSexo, String sexo, String nombre, String apellidoPaterno, String apellidoMaterno) {
        super(idPaciente, ocupacion, fechaNacimeinto, idContacto, domicilio, telefono, telefono2, email, facebook, idSexo, sexo, nombre, apellidoPaterno, apellidoMaterno);
        this.folio = folio;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Expediente(int idExpediente, String folio, String fechaExpedicion, String fechaActualizacion, int idPaciente, String ocupacion, String fechaNacimeinto, int idContacto, String domicilio, Long telefono, Long telefono2, String email, String facebook, int idSexo, String sexo) {
        super(idPaciente, ocupacion, fechaNacimeinto, idContacto, domicilio, telefono, telefono2, email, facebook, idSexo, sexo);
        this.idExpediente = idExpediente;
        this.folio = folio;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaActualizacion = fechaActualizacion;
    }
    
    
    public String obtenerFolio() {
        Calendar cal = new GregorianCalendar();
        String dia = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(cal.get(Calendar.MONTH)+1);
        String anio = Integer.toString(cal.get(Calendar.YEAR));
        String hora = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minutos = Integer.toString(cal.get(Calendar.MINUTE));
        
        String folioPaciente = anio + mes + dia + hora + minutos;
        
        return folioPaciente;
    }
    

    /**
     * @return the idExpediente
     */
    public int getIdExpediente() {
        return idExpediente;
    }

    /**
     * @param idExpediente the idExpediente to set
     */
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    /**
     * @return the folio
     */
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio the folio to set
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * @return the fechaExpedicion
     */
    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    /**
     * @param fechaExpedicion the fechaExpedicion to set
     */
    public void setFechaExpedicion(String fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    /**
     * @return the fechaActualizacion
     */
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the idFechasExpediente
     */
    public int getIdFechasExpediente() {
        return idFechasExpediente;
    }

    /**
     * @param idFechasExpediente the idFechasExpediente to set
     */
    public void setIdFechasExpediente(int idFechasExpediente) {
        this.idFechasExpediente = idFechasExpediente;
    }
    
    
    
    
           
    
}
