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
public class AntecedenteAG extends AntecedentesMedicos {
    
    private int idAntecedenteAG;
    private String ta;
    private String ng;

    public AntecedenteAG() {
    }

    public AntecedenteAG(int idAntecedenteAG, String ta, String ng) {
        this.idAntecedenteAG = idAntecedenteAG;
        this.ta = ta;
        this.ng = ng;
    }

    public AntecedenteAG(int idAntecedenteAG, String ta, String ng, int idExpediente, String folio, String fechaExpedicion, String fechaActualizacion) {
        super(idExpediente, folio, fechaExpedicion, fechaActualizacion);
        this.idAntecedenteAG = idAntecedenteAG;
        this.ta = ta;
        this.ng = ng;
    }

    public AntecedenteAG(int idAntecedenteAG, String ta, String ng, String enfermedad, String medicamento, String alergia) {
        super(enfermedad, medicamento, alergia);
        this.idAntecedenteAG = idAntecedenteAG;
        this.ta = ta;
        this.ng = ng;
    }

    /**
     * @return the idAntecedenteAG
     */
    public int getIdAntecedenteAG() {
        return idAntecedenteAG;
    }

    /**
     * @param idAntecedenteAG the idAntecedenteAG to set
     */
    public void setIdAntecedenteAG(int idAntecedenteAG) {
        this.idAntecedenteAG = idAntecedenteAG;
    }

    /**
     * @return the ta
     */
    public String getTa() {
        return ta;
    }

    /**
     * @param ta the ta to set
     */
    public void setTa(String ta) {
        this.ta = ta;
    }

    /**
     * @return the ng
     */
    public String getNg() {
        return ng;
    }

    /**
     * @param ng the ng to set
     */
    public void setNg(String ng) {
        this.ng = ng;
    }
    
    
    
    
}
