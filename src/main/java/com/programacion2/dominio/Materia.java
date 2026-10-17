package com.programacion2.dominio;

import java.util.ArrayList;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Materia {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private int id;
    private String nombre;
    @Column(name = "PROFESOR_ID")
    private Profesor profesor;

    
    @Persistent(table = "MATERIA_ALUMNOS")
    @Join(column = "MATERIA_ID")
    @Element(column = "ALUMNO_ID")
    private ArrayList<Alumno> alumnos;

    public Materia() {
        alumnos = new ArrayList<>();

    }

    public Materia(String nombre, Profesor profesor) {
        this.nombre = nombre;
        this.profesor = profesor;
        this.alumnos = new ArrayList<>();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public void agregarAlumno(Alumno alumno) {
          /*
         * Evitamos agregar dos veces el mismo objeto
         * a esta lista.
         */
        if (!alumnos.contains(alumno)) {

            alumnos.add(alumno);

            /*
             * Sincronizamos el otro lado:
             * Alumno también conoce a esta Materia.
             */
            alumno.agregarMateria(this);
        }

    }

    

    @Override
    public String toString() {
        return "Materia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidadAlumnos=" + alumnos.size() +
                '}';
    }

}
