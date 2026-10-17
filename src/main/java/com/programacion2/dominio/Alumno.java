package com.programacion2.dominio;

import java.util.ArrayList;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Alumno {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private int id;

    private String nombre;

    private String apellido;

    @Persistent(mappedBy = "alumnos")
    private ArrayList<Materia> materias;

    public Alumno() {
         materias = new ArrayList<>();
    }

    public Alumno(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
         this.materias = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

   

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }


     public void agregarMateria(Materia materia) {

        /*
         * Evitamos agregar dos veces el mismo objeto.
         */
        if (!materias.contains(materia)) {

            materias.add(materia);

            /*
             * Sincronizamos el otro lado:
             * Materia también conoce a este Alumno.
             */
            materia.agregarAlumno(this);
        }
    }


    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' 
                +
                '}';
    }

}
