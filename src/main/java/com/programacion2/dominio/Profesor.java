package com.programacion2.dominio;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Profesor extends Persona{

    @Persistent(mappedBy = "profesor")
    private Materia materia;
    String especialidad;

    public Profesor() {
        super();
    }

    public Profesor(String nombre, String apellido, String especialidad) {
       super(nombre,apellido);
       this.especialidad = especialidad;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

        @Override
    public String toString() {
        return "Profesor{" +
                "id=" + getId() +
                ", nombre='" +getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", materia=" +
                (materia != null ? materia.getNombre() : "sin materia") +
                '}';
    }

}
