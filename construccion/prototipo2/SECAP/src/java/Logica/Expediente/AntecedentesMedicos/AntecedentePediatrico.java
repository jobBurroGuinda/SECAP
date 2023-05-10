/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Expediente.AntecedentesMedicos;

/**
 *
 * @author Job
 */
public class AntecedentePediatrico extends AntecedentesMedicos {
    
    private int idAntecedentePediatrico;
    private String tiempoGestacion;
    private String complicacion;
    private String incubadora;
    private int idTipoParto;
    private String tipoParto;

    public AntecedentePediatrico() {
    }

    public AntecedentePediatrico(int idAntecedentePediatrico, String tiempoGestacion, String complicacion, String incubadora, int idTipoParto, String tipoParto) {
        this.idAntecedentePediatrico = idAntecedentePediatrico;
        this.tiempoGestacion = tiempoGestacion;
        this.complicacion = complicacion;
        this.incubadora = incubadora;
        this.idTipoParto = idTipoParto;
        this.tipoParto = tipoParto;
    }

    public AntecedentePediatrico(String tiempoGestacion, String complicacion, String incubadora, int idTipoParto, String tipoParto) {
        this.tiempoGestacion = tiempoGestacion;
        this.complicacion = complicacion;
        this.incubadora = incubadora;
        this.idTipoParto = idTipoParto;
        this.tipoParto = tipoParto;
    }

    public AntecedentePediatrico(int idAntecedentePediatrico, String tiempoGestacion, String complicacion, String incubadora, int idTipoParto, String tipoParto, int idExpediente, String folio, String fechaExpedicion, String fechaActualizacion) {
        super(idExpediente, folio, fechaExpedicion, fechaActualizacion);
        this.idAntecedentePediatrico = idAntecedentePediatrico;
        this.tiempoGestacion = tiempoGestacion;
        this.complicacion = complicacion;
        this.incubadora = incubadora;
        this.idTipoParto = idTipoParto;
        this.tipoParto = tipoParto;
    }

    public AntecedentePediatrico(int idAntecedentePediatrico, String tiempoGestacion, String complicacion, String incubadora, int idTipoParto, String tipoParto, String enfermedad, String medicamento, String alergia) {
        super(enfermedad, medicamento, alergia);
        this.idAntecedentePediatrico = idAntecedentePediatrico;
        this.tiempoGestacion = tiempoGestacion;
        this.complicacion = complicacion;
        this.incubadora = incubadora;
        this.idTipoParto = idTipoParto;
        this.tipoParto = tipoParto;
    }

    /**
     * @return the idAntecedentePediatrico
     */
    public int getIdAntecedentePediatrico() {
        return idAntecedentePediatrico;
    }

    /**
     * @param idAntecedentePediatrico the idAntecedentePediatrico to set
     */
    public void setIdAntecedentePediatrico(int idAntecedentePediatrico) {
        this.idAntecedentePediatrico = idAntecedentePediatrico;
    }

    /**
     * @return the tiempoGestacion
     */
    public String getTiempoGestacion() {
        return tiempoGestacion;
    }

    /**
     * @param tiempoGestacion the tiempoGestacion to set
     */
    public void setTiempoGestacion(String tiempoGestacion) {
        this.tiempoGestacion = tiempoGestacion;
    }

    /**
     * @return the complicacion
     */
    public String getComplicacion() {
        return complicacion;
    }

    /**
     * @param complicacion the complicacion to set
     */
    public void setComplicacion(String complicacion) {
        this.complicacion = complicacion;
    }

    /**
     * @return the incubadora
     */
    public String getIncubadora() {
        return incubadora;
    }

    /**
     * @param incubadora the incubadora to set
     */
    public void setIncubadora(String incubadora) {
        this.incubadora = incubadora;
    }

    /**
     * @return the idTipoParto
     */
    public int getIdTipoParto() {
        return idTipoParto;
    }

    /**
     * @param idTipoParto the idTipoParto to set
     */
    public void setIdTipoParto(int idTipoParto) {
        this.idTipoParto = idTipoParto;
    }

    /**
     * @return the tipoParto
     */
    public String getTipoParto() {
        return tipoParto;
    }

    /**
     * @param tipoParto the tipoParto to set
     */
    public void setTipoParto(String tipoParto) {
        this.tipoParto = tipoParto;
    }
    
    
    
    
    
}
