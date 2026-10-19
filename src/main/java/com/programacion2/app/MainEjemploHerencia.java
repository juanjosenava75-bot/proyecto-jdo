package com.programacion2.app;

import com.programacion2.dominio.Alumno;
import com.programacion2.dominio.Profesor;
import com.programacion2.repositorio.IRepositorio;
import com.programacion2.repositorio.Repositorio;

public class MainEjemploHerencia {

    public static void main(String[] args) {

        /*
         * ESTRATEGIA SUPERCLASS_TABLE
         *
         * Primero ejecutar los métodos guardar.
         * Después revisar los ID generados en la consola
         * o directamente en la base de datos.
         *
         * Para consultar, comentar los métodos guardar y ejecutar
         * las consultas con los ID correspondientes.
         */

         guardarAlumno();
        // guardarProfesor();

        // consultarAlumno(1);
        //consultarProfesor(2);
    }

    /*
     * HERENCIA CON SUPERCLASS_TABLE
     *
     * Tenemos la siguiente jerarquía:
     *
     * Persona
     * / \
     * Alumno Profesor
     *
     * En la clase Persona utilizamos:
     *
     * @PersistenceCapable
     * 
     * @Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
     * public class Persona {
     * ...
     * }
     *
     * NEW_TABLE indica que Persona tendrá su propia tabla.
     *
     * En la clase Alumno utilizamos:
     *
     * @PersistenceCapable
     * 
     * @Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
     * public class Alumno extends Persona {
     * ...
     * }
     *
     * En la clase Profesor utilizamos:
     *
     * @PersistenceCapable
     * 
     * @Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
     * public class Profesor extends Persona {
     * ...
     * }
     *
     * SUPERCLASS_TABLE indica que los atributos de las subclases
     * se almacenan en la tabla de la superclase.
     *
     * Por lo tanto, Alumno y Profesor no tienen tablas propias.
     *
     * Todos los objetos de la jerarquía se almacenan en la tabla
     * PERSONA.
     *
     * La tabla PERSONA puede contener las siguientes columnas:
     *
     * id
     * nombre
     * apellido
     * legajo
     * especialidad
     *
     * Como no todos los objetos utilizan las mismas columnas,
     * algunas pueden contener valores nulos.
     *
     * Por ejemplo:
     *
     * - En un registro correspondiente a un Alumno, especialidad
     * estará en null.
     *
     * - En un registro correspondiente a un Profesor, legajo
     * estará en null.
     *
     * /*
     * En la clase Persona también agregamos un discriminador:
     *
     * @Discriminator(
     * strategy = DiscriminatorStrategy.CLASS_NAME,
     * column = "TIPO_PERSONA"
     * )
     *
     * El discriminador agrega la columna TIPO_PERSONA en la tabla PERSONA.
     *
     * DataNucleus utiliza esta columna para identificar qué clase
     * representa cada registro.
     *
     * Por ejemplo, permite diferenciar si una fila corresponde
     * a un Alumno o a un Profesor.
     */

    /*
     * GUARDAR UN ALUMNO
     *
     * Alumno hereda id, nombre y apellido de Persona y agrega
     * su atributo propio legajo.
     *
     * Como Alumno utiliza SUPERCLASS_TABLE, no se crea ni se utiliza
     * una tabla ALUMNO.
     *
     * DataNucleus guarda todos los datos del objeto en PERSONA:
     *
     * id, nombre, apellido y legajo.
     *
     * La columna especialidad no corresponde a Alumno, por lo que
     * quedará con valor null.
     *
     * La información del discriminador permite identificar que
     * el registro representa un objeto Alumno.
     */

    private static void guardarAlumno() {

        Alumno alumno = new Alumno(
                "Analia",
                "Gómez",
                "A600");

        IRepositorio<Alumno> repositorio = new Repositorio<>("Programacion2");

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
     * Como Profesor utiliza SUPERCLASS_TABLE, no se crea ni se
     * utiliza una tabla PROFESOR.
     *
     * DataNucleus guarda todos los datos del objeto en PERSONA:
     *
     * id, nombre, apellido y especialidad.
     *
     * La columna legajo no corresponde a Profesor, por lo que
     * quedará con valor null.
     *
     * La información del discriminador permite identificar que
     * el registro representa un objeto Profesor.
     */

    private static void guardarProfesor() {

        Profesor profesor = new Profesor(
                "Carlos",
                "Pérez",
                "Programación");

        IRepositorio<Profesor> repositorio = new Repositorio<>("Programacion2");

        repositorio.guardar(profesor);

        System.out.println("Profesor guardado correctamente");
        System.out.println("ID generado: " + profesor.getId());
    }

    /*
     * CONSULTAR UN ALUMNO
     *
     * Recuperamos un Alumno mediante su ID.
     *
     * DataNucleus busca el registro en la tabla PERSONA.
     *
     * Mediante la información de la jerarquía y, cuando corresponde,
     * el valor del discriminador, determina que el registro representa
     * un objeto Alumno.
     *
     * Después construye el objeto utilizando:
     *
     * - Los atributos heredados: id, nombre y apellido.
     * - El atributo propio: legajo.
     *
     * No necesita combinar varias tablas porque todos los datos
     * se encuentran en PERSONA.
     */

    private static void consultarAlumno(int id) {

        IRepositorio<Alumno> repositorioAlumno = new Repositorio<>("Programacion2");

        Alumno alumno = repositorioAlumno.consultar(
                Alumno.class,
                id);

        System.out.println("Alumno recuperado:");
        System.out.println(alumno);
    }

    /*
     * CONSULTAR UN PROFESOR
     *
     * Recuperamos un Profesor mediante su ID.
     *
     * DataNucleus busca el registro en la tabla PERSONA.
     *
     * Mediante la información de la jerarquía y, cuando corresponde,
     * el valor del discriminador, determina que el registro representa
     * un objeto Profesor.
     *
     * Después construye el objeto utilizando:
     *
     * - Los atributos heredados: id, nombre y apellido.
     * - El atributo propio: especialidad.
     *
     * No necesita combinar varias tablas porque todos los datos
     * se encuentran en PERSONA.
     */

    private static void consultarProfesor(int id) {

        IRepositorio<Profesor> repositorioProfesor = new Repositorio<>("Programacion2");

        Profesor profesor = repositorioProfesor.consultar(
                Profesor.class,
                id);

        System.out.println("Profesor recuperado:");
        System.out.println(profesor);
    }
}