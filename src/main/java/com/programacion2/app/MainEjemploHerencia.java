package com.programacion2.app;

import com.programacion2.dominio.Alumno;
import com.programacion2.dominio.Profesor;
import com.programacion2.repositorio.IRepositorio;
import com.programacion2.repositorio.Repositorio;

public class MainEjemploHerencia {

    public static void main(String[] args) {

        /*
         * ESTRATEGIA SUBCLASS_TABLE
         *
         * Primero ejecutar los métodos guardar.
         * Después revisar los ID generados en la consola
         * o directamente en la base de datos.
         *
         * Para consultar, comentar los métodos guardar y ejecutar
         * las consultas con los ID correspondientes.
         */

        // guardarAlumno();
        // guardarProfesor();

        // consultarAlumno(1);
        consultarProfesor(1);
    }

    /*
     * HERENCIA CON SUBCLASS_TABLE
     *
     * Tenemos la siguiente jerarquía:
     *
     *              Persona
     *              /     \
     *          Alumno   Profesor
     *
     * En la clase Persona agregamos:
     *
     * @PersistenceCapable
     * @Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
     * public abstract class Persona {
     *     ...
     * }
     *
     * SUBCLASS_TABLE indica que los atributos declarados en Persona
     * se almacenarán en las tablas de sus subclases.
     *
     * Persona no necesita tener una tabla propia.
     *
     * En la clase Alumno agregamos:
     *
     * @PersistenceCapable
     * @Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
     * public class Alumno extends Persona {
     *     ...
     * }
     *
     * En la clase Profesor agregamos:
     *
     * @PersistenceCapable
     * @Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
     * public class Profesor extends Persona {
     *     ...
     * }
     *
     * DataNucleus crea una tabla ALUMNO que contiene:
     *
     * id, nombre, apellido y legajo.
     *
     * También crea una tabla PROFESOR que contiene:
     *
     * id, nombre, apellido y especialidad.
     *
     * Los atributos nombre y apellido están declarados en Persona,
     * pero se almacenan en las tablas ALUMNO y PROFESOR.
     *
     * No se crea una tabla PERSONA.
     */

    /*
     * GUARDAR UN ALUMNO
     *
     * Alumno hereda id, nombre y apellido de Persona y agrega
     * su atributo propio legajo.
     *
     * Como Persona utiliza SUBCLASS_TABLE, todos los datos del
     * objeto se almacenan en la tabla ALUMNO:
     *
     * id, nombre, apellido y legajo.
     *
     * No se realiza una inserción en una tabla PERSONA porque
     * esa tabla no existe en esta estrategia.
     */

    private static void guardarAlumno() {

        Alumno alumno = new Alumno(
                "Ana",
                "Gómez",
                "A100"
        );

        IRepositorio<Alumno> repositorio =
                new Repositorio<>("Programacion2");

        repositorio.guardar(alumno);

        System.out.println("Alumno guardado correctamente");
        System.out.println("ID generado: " + alumno.getId());
    }

    /*
     * GUARDAR UN PROFESOR
     *
     * Profesor hereda id, nombre y apellido de Persona y agrega
     * su atributo propio especialidad.
     *
     * Como Persona utiliza SUBCLASS_TABLE, todos los datos del
     * objeto se almacenan en la tabla PROFESOR:
     *
     * id, nombre, apellido y especialidad.
     *
     * No se realiza una inserción en una tabla PERSONA porque
     * esa tabla no existe en esta estrategia.
     */

    private static void guardarProfesor() {

        Profesor profesor = new Profesor(
                "Carlos",
                "Pérez",
                "Programación"
        );

        IRepositorio<Profesor> repositorio =
                new Repositorio<>("Programacion2");

        repositorio.guardar(profesor);

        System.out.println("Profesor guardado correctamente");
        System.out.println("ID generado: " + profesor.getId());
    }

    /*
     * CONSULTAR UN ALUMNO
     *
     * Recuperamos un Alumno mediante su ID.
     *
     * DataNucleus obtiene todos sus datos directamente desde
     * la tabla ALUMNO:
     *
     * id, nombre, apellido y legajo.
     *
     * No necesita combinar ALUMNO con una tabla PERSONA porque
     * PERSONA no tiene una tabla propia.
     *
     * Finalmente construye un objeto Alumno con los atributos
     * heredados y los atributos propios.
     */

    private static void consultarAlumno(int id) {

        IRepositorio<Alumno> repositorioAlumno =
                new Repositorio<>("Programacion2");

        Alumno alumno =
                repositorioAlumno.consultar(
                        Alumno.class,
                        id
                );

        System.out.println("Alumno recuperado:");
        System.out.println(alumno);
    }

    /*
     * CONSULTAR UN PROFESOR
     *
     * Recuperamos un Profesor mediante su ID.
     *
     * DataNucleus obtiene todos sus datos directamente desde
     * la tabla PROFESOR:
     *
     * id, nombre, apellido y especialidad.
     *
     * No necesita combinar PROFESOR con una tabla PERSONA porque
     * PERSONA no tiene una tabla propia.
     *
     * Finalmente construye un objeto Profesor con los atributos
     * heredados y los atributos propios.
     */

    private static void consultarProfesor(int id) {

        IRepositorio<Profesor> repositorioProfesor =
                new Repositorio<>("Programacion2");

        Profesor profesor =
                repositorioProfesor.consultar(
                        Profesor.class,
                        id
                );

        System.out.println("Profesor recuperado:");
        System.out.println(profesor);
    }
}