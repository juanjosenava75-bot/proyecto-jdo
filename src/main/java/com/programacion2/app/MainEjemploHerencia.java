package com.programacion2.app;

import java.util.List;

import javax.jdo.JDOHelper;

import com.programacion2.dominio.Alumno;
import com.programacion2.dominio.Materia;
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

        // guardarAlumno();
        // guardarProfesor();

        // consultarAlumno(1);
        //consultarProfesor(2);

        //consultarMateria(1);

        actualizarDirty();

        //listarAlumnos();
    }




    /*
 * CONSULTAR UNA MATERIA Y SUS RELACIONES DIRECTAS
 *
 * Este método permite observar la profundidad de recuperación
 * de los objetos relacionados.
 *
 * La consulta comienza desde una Materia:
 *
 * Profundidad 0:
 *
 * Materia
 *
 * Profundidad 1:
 *
 * Materia → Profesor
 * Materia → Alumnos
 *
 * En el repositorio utilizamos:
 *
 * pm.getFetchPlan().setMaxFetchDepth(1);
 * pm.retrieve(objeto, true);
 *
 * maxFetchDepth(1) permite recorrer una relación desde el
 * objeto principal.
 *
 * Por eso se pueden recuperar las relaciones directas de
 * Materia: profesor y alumnos.
 *
 * retrieve(objeto, true) fuerza la recuperación de los campos
 * incluidos en el FetchPlan antes de que el objeto quede
 * detached.
 *
 * En la clase Materia tenemos:
 *
 * @Persistent(defaultFetchGroup = "true")
 * @Column(name = "PROFESOR_ID")
 * private Profesor profesor;
 *
 * @Persistent(
 *     table = "MATERIA_ALUMNOS",
 *     defaultFetchGroup = "true"
 * )
 * @Join(column = "MATERIA_ID")
 * @Element(column = "ALUMNO_ID")
 * private ArrayList<Alumno> alumnos;
 *
 * defaultFetchGroup = "true" incorpora estas relaciones al
 * FetchPlan predeterminado.
 */

private static void consultarMateria(int id) {

    IRepositorio<Materia> repositorio =
            new Repositorio<>("Programacion2");

    /*
     * Recuperamos la Materia mediante su ID.
     *
     * El repositorio carga los campos incluidos en el FetchPlan
     * y devuelve un objeto detached.
     */
    Materia materia =
            repositorio.consultar(
                    Materia.class,
                    id
            );

    /*
     * nombre es un atributo simple de Materia.
     *
     * No es necesario recorrer ninguna relación para acceder
     * a este atributo.
     */
    System.out.println(
            "Materia: " +
            materia.getNombre()
    );

    /*
     * profesor es una relación directa de Materia.
     *
     * Para acceder a ella necesitamos recorrer una relación:
     *
     * Materia → Profesor
     *
     * Por eso alcanza con maxFetchDepth(1).
     */
    System.out.println(
            "Profesor: " +
            materia.getProfesor().getNombre() + " " +
            materia.getProfesor().getApellido()
    );

    /*
     * alumnos también es una relación directa de Materia:
     *
     * Materia → Alumnos
     *
     * La colección se encuentra en profundidad 1.
     */
    System.out.println("Alumnos:");

    for (Alumno alumno : materia.getAlumnos()) {

        /*
         * Los alumnos fueron recuperados antes de realizar
         * el detach, por lo que podemos acceder a sus atributos
         * después de cerrar el PersistenceManager.
         */
        System.out.println(
                alumno.getNombre() + " " +
                alumno.getApellido()
        );
    }
}

        /*
 * ACTUALIZAR UN OBJETO DETACHED
 *
 * Este ejemplo muestra cómo recuperar un objeto, modificarlo
 * fuera del PersistenceManager y volver a guardarlo.
 *
 * El recorrido esperado es:
 *
 * PERSISTENT_CLEAN
 *        ↓ commit y detach
 * DETACHED_CLEAN
 *        ↓ modificar un atributo
 * DETACHED_DIRTY
 *        ↓ makePersistent
 * PERSISTENT_DIRTY
 *        ↓ commit
 * Cambios confirmados en la base de datos
 */
private static void actualizarDirty() {

    IRepositorio<Materia> repositorio =
            new Repositorio<>("Programacion2");

    /*
     * Recuperamos la Materia desde la base de datos.
     *
     * Dentro del repositorio, mientras el PersistenceManager
     * está abierto, el objeto se encuentra administrado por JDO.
     *
     * Después del commit, como utilizamos:
     *
     * pm.setDetachAllOnCommit(true);
     *
     * la Materia se devuelve separada del PersistenceManager.
     *
     * Si todavía no fue modificada, su estado será:
     *
     * DETACHED_CLEAN
     */
    Materia materia =
            repositorio.consultar(
                    Materia.class,
                    1
            );

    /*
     * Podemos utilizar el objeto aunque el PersistenceManager
     * utilizado para consultarlo ya se encuentre cerrado,
     * porque Materia está configurada como detachable.
     */
    System.out.println(
            "Materia antes de modificar: " +
            materia.getNombre()
    );

    /*
     * Mostramos el estado del objeto recuperado.
     *
     * La salida esperada es:
     *
     * DETACHED_CLEAN
     
     * Modificamos un atributo del objeto detached.
     *
     * Al cambiar el nombre, la Materia contiene una modificación
     * que todavía no fue enviada a la base de datos.
     *
     * El objeto pasa de:
     *
     * DETACHED_CLEAN → DETACHED_DIRTY
     */
    materia.setNombre("Programación Avanzada");

    /*
     * La salida esperada es:
     *
     * DETACHED_DIRTY
     
     * Volvemos a enviar el objeto al repositorio.
     *
     * Como Materia ya tiene un ID persistente, DataNucleus
     * reconoce que representa un registro existente.
     *
     * makePersistent() vuelve a asociar sus datos con un objeto
     * administrado y, al ejecutar commit(), se realiza un UPDATE.
     *
     * No se inserta una nueva Materia.
     */
    repositorio.guardar(materia);

    System.out.println(
            "Materia actualizada correctamente"
    );
}
    private static void listarAlumnos() {
        IRepositorio <Alumno> repositorio = new Repositorio<>("Programacion2");

        List <Alumno> alumnos = repositorio.listar(Alumno.class);

        for (Alumno alumno : alumnos) {

            System.out.println( alumno );
            
        }

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