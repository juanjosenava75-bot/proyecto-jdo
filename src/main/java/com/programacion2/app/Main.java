package com.programacion2.app;

import com.programacion2.dominio.Materia;
import com.programacion2.dominio.Persona;
import com.programacion2.dominio.Profesor;
import com.programacion2.repositorio.IRepositorio;
import com.programacion2.repositorio.Repositorio;

public class Main {
    public static void main(String[] args) {

           /*
         * Ejecutar primero guardarUnoAUnoUnidireccional().
         * Después verificar el ID generado en la consola o en la base de datos.
         *
         * Para realizar la consulta, comentar el método guardar
         * y descomentar el método consultar.
         */

        guardarUnoAUnoUnidireccional();
        //consultarUnoAUnoUnidireccional(1);

    }
 /*
     * RELACIÓN UNO A UNO UNIDIRECCIONAL
     *
     * Materia tiene un atributo de tipo Profesor, pero Profesor no tiene
     * ningún atributo de tipo Materia.
     *
     * Materia es la clase propietaria de la relación.
     *
     * En la clase Materia agregamos:
     *
     * @Column(name = "PROFESOR_ID")
     * private Profesor profesor;
     *
     * La anotación @Column indica que la clave foránea del profesor
     * se almacenará en una columna llamada PROFESOR_ID dentro
     * de la tabla MATERIA.
     */

    private static void guardarUnoAUnoUnidireccional() {

        IRepositorio<Materia> repositorio =
                new Repositorio<>("Programacion2");

        Profesor profesor =
                new Profesor("Carlos", "Perez");

        Materia materia =
                new Materia("Programacion 2", profesor);

        repositorio.guardar(materia);

        System.out.println("Materia guardada correctamente");
        System.out.println("ID generado: " + materia.getId());
    }

    /*
     * Uno a uno: una materia tiene asociado un profesor.
     *
     * Unidireccional: solamente podemos navegar desde Materia
     * hacia Profesor.
     *
     * Recuperamos la materia mediante su ID y después accedemos
     * al profesor relacionado mediante materia.getProfesor().
     */

    private static void consultarUnoAUnoUnidireccional(int id) {

        IRepositorio<Materia> repositorio =
                new Repositorio<>("Programacion2");

        Materia materia =
                repositorio.consultar(Materia.class, id);

        System.out.println("Materia: " + materia.getNombre());

        System.out.println(
                "Profesor: "
                + materia.getProfesor().getNombre()
                + " "
                + materia.getProfesor().getApellido()
        );
    }

}