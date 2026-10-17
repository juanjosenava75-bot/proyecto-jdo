package com.programacion2.app;

import com.programacion2.dominio.Alumno;
import com.programacion2.dominio.Profesor;
import com.programacion2.repositorio.IRepositorio;
import com.programacion2.repositorio.Repositorio;

public class MainEjemploHerencia {

    public static void main(String[] args) {

        /*
         * Primero ejecutar los métodos guardar.
         * Después revisar los ID generados en la consola o en la
         * base de datos.
         *
         * Para consultar, comentar los métodos guardar y ejecutar
         * las consultas con los ID correspondientes.
         */

        // guardarAlumno();
        // guardarProfesor();

        consultarAlumno(1);
        consultarProfesor(2);
    }

    /*
     * HERENCIA CON NEW_TABLE
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
     * @Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
     * public class Persona {
     *     ...
     * }
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
     * NEW_TABLE indica que cada clase persistente de la jerarquía
     * utiliza su propia tabla.
     *
     */

    /*
     * GUARDAR UN ALUMNO
     *
     * Alumno hereda nombre, apellido e ID de Persona y agrega
     * su atributo propio legajo.
     *
     * Al persistir el alumno, DataNucleus guarda:
     *
     * - Los atributos heredados en la tabla PERSONA.
     * - Los atributos propios en la tabla ALUMNO.
     *
     * Los registros quedan relacionados mediante el mismo ID.
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
     * Profesor hereda nombre, apellido e ID de Persona y agrega
     * su atributo propio especialidad.
     *
     * Al persistir el profesor, DataNucleus guarda:
     *
     * - Los atributos heredados en la tabla PERSONA.
     * - Los atributos propios en la tabla PROFESOR.
     *
     * Los registros quedan relacionados mediante el mismo ID.
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
     * Recuperamos un objeto Alumno mediante su ID.
     *
     * DataNucleus obtiene los atributos heredados desde PERSONA
     * y los atributos propios desde ALUMNO.
     *
     * Finalmente construye un único objeto Alumno con todos
     * sus datos.
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
     * Recuperamos un objeto Profesor mediante su ID.
     *
     * DataNucleus obtiene los atributos heredados desde PERSONA
     * y los atributos propios desde PROFESOR.
     *
     * Finalmente construye un único objeto Profesor con todos
     * sus datos.
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