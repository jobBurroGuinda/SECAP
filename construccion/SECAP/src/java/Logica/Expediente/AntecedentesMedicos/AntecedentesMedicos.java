/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Expediente.AntecedentesMedicos;

import Logica.Expediente.Expediente;

/**
 *
 * @author Job
 */
public class AntecedentesMedicos extends Expediente {
    
    private int idAntecedenteMedico;
    private String enfermedad;
    private String medicamento;
    private String alergia;

    public AntecedentesMedicos() {
    }

    public AntecedentesMedicos(int idExpediente, String folio, String fechaExpedicion, String fechaActualizacion) {
        super(idExpediente, folio, fechaExpedicion, fechaActualizacion);
    }

    public AntecedentesMedicos(String enfermedad, String medicamento, String alergia) {
        this.enfermedad = enfermedad;
        this.medicamento = medicamento;
        this.alergia = alergia;
    }

    /**
     * @return the idAntecedenteMedico
     */
    public int getIdAntecedenteMedico() {
        return idAntecedenteMedico;
    }

    /**
     * @param idAntecedenteMedico the idAntecedenteMedico to set
     */
    public void setIdAntecedenteMedico(int idAntecedenteMedico) {
        this.idAntecedenteMedico = idAntecedenteMedico;
    }

    /**
     * @return the enfermedad
     */
    public String getEnfermedad() {
        return enfermedad;
    }

    /**
     * @param enfermedad the enfermedad to set
     */
    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    /**
     * @return the medicamento
     */
    public String getMedicamento() {
        return medicamento;
    }

    /**
     * @param medicamento the medicamento to set
     */
    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    /**
     * @return the alergia
     */
    public String getAlergia() {
        return alergia;
    }

    /**
     * @param alergia the alergia to set
     */
    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }
    
    
    
    
}
