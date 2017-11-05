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
public class Materia {
    private int id;
    private String nombre;
    private Profesor profesor;
    private Curso curso;
    private int intHoraria;

    public Materia(int id, String nombre, Profesor profesor, Curso curso, int intHoraria) {
        this.id = id;
        this.nombre = nombre;
        this.profesor = profesor;
        this.curso = curso;
        this.intHoraria = intHoraria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getIntHoraria() {
        return intHoraria;
    }

    public void setIntHoraria(int intHoraria) {
        this.intHoraria = intHoraria;
    }

    
}
