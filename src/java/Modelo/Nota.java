/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author willy
 */
public class Nota {

    private int id;
    private String materia;
    private int valor;
    private int periodo;
    private String observacion;
    private String id_est;

    public Nota(int id, String materia, int valor, int periodo, String observacion, String id_est) {
        this.id = id;
        this.materia = materia;
        this.valor = valor;
        this.periodo = periodo;
        this.observacion = observacion;
        this.id_est = id_est;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getId_est() {
        return id_est;
    }

    public void setId_est(String id_est) {
        this.id_est = id_est;
    }

    
    
}
