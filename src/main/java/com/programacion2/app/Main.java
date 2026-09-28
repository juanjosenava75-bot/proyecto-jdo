package com.programacion2.app;


import com.programacion2.dominio.Materia;
import com.programacion2.dominio.Persona;
import com.programacion2.dominio.Profesor;
import com.programacion2.repositorio.IRepositorio;
import com.programacion2.repositorio.Repositorio;


public class Main {
    public static void main(String[] args) {

        IRepositorio <Materia> repositorio = new Repositorio<>("Programacion2");

       // Profesor profesor = new Profesor("Carlos", "Perez");

        //Materia materia = new Materia("Programacion 2", profesor);

        //repositorio.guardar(materia);
Materia materia= repositorio.consultar(Materia.class, 1);
System.out.println("Materia: " + materia.getNombre());
System.out.println("Profesor: " + materia.getProfesor().getNombre() + " "+ materia.getProfesor().getApellido());
       
        
    }
}