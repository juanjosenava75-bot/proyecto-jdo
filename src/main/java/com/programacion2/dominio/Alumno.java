package com.programacion2.dominio;

import java.util.ArrayList;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
public class Alumno extends  Persona{

     private String legajo;

    @Persistent(mappedBy = "alumnos")
    private ArrayList<Materia> materias;

    public Alumno() {
        super();
         materias = new ArrayList<>();
    }

    public Alumno(String nombre, String apellido , String legajo) {
        super(nombre, apellido);
        this.legajo = legajo;
         this.materias = new ArrayList<>();
    }

   

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

    

     public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
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
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' 
                +
                '}';
    }

}
