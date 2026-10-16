package com.programacion2.app;

import com.programacion2.dominio.Alumno;
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

        // guardarUnoAUnoUnidireccional();
        // consultarUnoAUnoUnidireccional(1);

        // guardarUnoAUnoBidireccional();
        // consultarDesdeMateriaUnoAUnoBidireccional(1);
        // consultarDesdeProfesorUnoAUnoBidireccional(1);

        // guardarUnoAMuchosUnidireccional();
        // consultarUnoAMuchosUnidireccional(1);

        //guardarUnoAMuchosBidireccional();
        consultarDesdeMateriaUnoAMuchosBidireccional(1);
        //consultarDesdeAlumnoUnoAMuchosBidireccional(1);

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

        IRepositorio<Materia> repositorio = new Repositorio<>("Programacion2");

        Profesor profesor = new Profesor("Carlos", "Perez");

        Materia materia = new Materia("Programacion 2", profesor);

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

        IRepositorio<Materia> repositorio = new Repositorio<>("Programacion2");

        Materia materia = repositorio.consultar(Materia.class, id);

        System.out.println("Materia: " + materia.getNombre());

        System.out.println(
                "Profesor: "
                        + materia.getProfesor().getNombre()
                        + " "
                        + materia.getProfesor().getApellido());
    }

    /*
     * RELACIÓN UNO A UNO BIDIRECCIONAL
     *
     * En esta relación, ambas clases se conocen:
     *
     * Materia -> Profesor
     * Profesor -> Materia
     *
     * En la clase Materia ya tenemos:
     *
     * @Column(name = "PROFESOR_ID")
     * private Profesor profesor;
     *
     * Materia continúa siendo la clase propietaria de la relación porque
     * contiene la columna PROFESOR_ID, donde se almacena la clave foránea.
     *
     * En la clase Profesor agregamos:
     *
     * @Persistent(mappedBy = "profesor")
     * private Materia materia;
     *
     * mappedBy = "profesor" indica que la relación se encuentra mapeada
     * por el atributo llamado profesor de la clase Materia.
     *
     * Profesor representa el lado inverso de la relación. Por eso, no se
     * crea otra clave foránea para el atributo materia.
     */

    private static void guardarUnoAUnoBidireccional() {

        Profesor profesor = new Profesor("Carlos", "Pérez");

        Materia materia = new Materia("Programación II", profesor);

        /*
         * El constructor de Materia configura la dirección:
         *
         * Materia -> Profesor
         *
         * Esta instrucción configura la dirección contraria:
         *
         * Profesor -> Materia
         *
         * En una relación bidireccional debemos mantener sincronizados
         * los dos extremos de la relación en memoria.
         */

        profesor.setMateria(materia);

        IRepositorio<Materia> repositorio = new Repositorio<>("Programacion2");

        /*
         * Guardamos la materia porque es la clase propietaria.
         * DataNucleus persiste también el profesor relacionado.
         */

        repositorio.guardar(materia);

        System.out.println("Materia guardada correctamente");
        System.out.println("ID de materia: " + materia.getId());
        System.out.println("ID de profesor: " + profesor.getId());
    }

    /*
     * CONSULTA DESDE MATERIA
     *
     * Recuperamos una materia mediante su ID y navegamos desde Materia
     * hacia Profesor usando materia.getProfesor().
     *
     * Esta dirección ya existía en la relación unidireccional:
     *
     * Materia -> Profesor
     */

    private static void consultarDesdeMateriaUnoAUnoBidireccional(int id) {

        IRepositorio<Materia> repositorio = new Repositorio<>("Programacion2");

        Materia materia = repositorio.consultar(Materia.class, id);

        System.out.println("Materia: " + materia.getNombre());

        System.out.println(
                "Profesor: "
                        + materia.getProfesor().getNombre()
                        + " "
                        + materia.getProfesor().getApellido());
    }

    /*
     * CONSULTA DESDE PROFESOR
     *
     * Recuperamos un profesor mediante su ID y navegamos desde Profesor
     * hacia Materia usando profesor.getMateria().
     *
     * Esta dirección es la que se agrega al convertir la relación
     * en bidireccional:
     *
     * Profesor -> Materia
     *
     * Es posible navegar en esta dirección porque la clase Profesor tiene:
     *
     * @Persistent(mappedBy = "profesor")
     * private Materia materia;
     */

    private static void consultarDesdeProfesorUnoAUnoBidireccional(int id) {

        IRepositorio<Profesor> repositorio = new Repositorio<>("Programacion2");

        Profesor profesor = repositorio.consultar(Profesor.class, id);

        System.out.println(
                "Profesor: "
                        + profesor.getNombre()
                        + " "
                        + profesor.getApellido());

        System.out.println(
                "Materia: "
                        + profesor.getMateria().getNombre());
    }

    /*
     * RELACIÓN UNO A MUCHOS UNIDIRECCIONAL
     *
     * Una Materia puede tener asociados varios alumnos.
     *
     * Materia -> varios alumnos
     *
     * La relación es unidireccional porque podemos navegar desde Materia
     * hacia sus alumnos, pero Alumno no tiene ningún atributo que permita
     * regresar a la Materia.
     *
     * En la clase Materia agregamos:
     *
     * @Persistent(table = "MATERIA_ALUMNOS")
     * 
     * @Join(column = "MATERIA_ID")
     * 
     * @Element(column = "ALUMNO_ID")
     * private ArrayList<Alumno> alumnos;
     *
     * @Persistent indica que la colección forma parte del mapeo persistente.
     *
     * table = "MATERIA_ALUMNOS" establece el nombre de la tabla intermedia
     * utilizada para guardar la relación.
     *
     * @Join(column = "MATERIA_ID") indica la columna que referencia
     * a la materia dentro de la tabla intermedia.
     *
     * @Element(column = "ALUMNO_ID") indica la columna que referencia
     * a cada alumno almacenado en la colección.
     *
     * En esta relación Alumno no debe tener un atributo de tipo Materia.
     */

    private static void guardarUnoAMuchosUnidireccional() {

        /*
         * La relación entre Materia y Profesor continúa siendo bidireccional.
         * Por eso configuramos sus dos direcciones:
         *
         * Materia -> Profesor
         * Profesor -> Materia
         */

        Profesor profesor = new Profesor("Carlos", "Pérez");

        Materia materia = new Materia("Programación II", profesor);

        profesor.setMateria(materia);

        /*
         * Creamos los alumnos que formarán parte de la colección
         * perteneciente a la materia.
         */

        Alumno alumno1 = new Alumno("Ana", "Gómez");

        Alumno alumno2 = new Alumno("Pedro", "López");

        Alumno alumno3 = new Alumno("Lucía", "Martínez");

        /*
         * Agregamos los alumnos a la colección de Materia.
         *
         * Como la relación es unidireccional, solamente configuramos:
         *
         * Materia -> alumnos
         *
         * Los objetos Alumno no guardan una referencia a Materia.
         */

        materia.agregarAlumno(alumno1);
        materia.agregarAlumno(alumno2);
        materia.agregarAlumno(alumno3);

        IRepositorio<Materia> repositorio = new Repositorio<>("Programacion2");

        /*
         * Guardamos la materia, que es la clase propietaria de la relación.
         *
         * DataNucleus persiste la materia, los alumnos relacionados
         * y los registros correspondientes en la tabla intermedia
         * MATERIA_ALUMNOS.
         */

        repositorio.guardar(materia);

        System.out.println("Materia guardada correctamente");
        System.out.println("ID de la materia: " + materia.getId());
    }

    /*
     * CONSULTA DE UNA RELACIÓN UNO A MUCHOS UNIDIRECCIONAL
     *
     * Recuperamos una materia mediante su ID.
     *
     * Después recorremos la colección obtenida mediante getAlumnos().
     * La navegación solamente se realiza desde Materia hacia Alumno:
     *
     * Materia -> alumnos
     *
     * No podemos navegar desde un alumno hacia la materia porque
     * Alumno no tiene un atributo de tipo Materia.
     */

    private static void consultarUnoAMuchosUnidireccional(int id) {

        IRepositorio<Materia> repositorio = new Repositorio<>("Programacion2");

        Materia materia = repositorio.consultar(Materia.class, id);

        System.out.println("Materia: " + materia.getNombre());

        System.out.println("Alumnos:");

        for (Alumno alumno : materia.getAlumnos()) {

            System.out.println(
                    alumno.getNombre()
                            + " "
                            + alumno.getApellido());
        }
    }

/*
 * RELACIÓN UNO A MUCHOS BIDIRECCIONAL
 *
 * Una Materia puede tener varios alumnos y cada Alumno tiene
 * asociada una sola Materia.
 *
 * Ahora podemos navegar en ambas direcciones:
 *
 * Materia -> alumnos
 * Alumno  -> materia
 *
 * En la clase Materia agregamos:
 *
 * @Persistent(mappedBy = "materia")
 * @Join
 * private ArrayList<Alumno> alumnos;
 *
 * mappedBy = "materia" indica que el otro extremo de la relación
 * se encuentra en el atributo llamado materia de la clase Alumno.
 *
 * @Join indica que DataNucleus utiliza una tabla intermedia para
 * almacenar la relación entre las materias y los alumnos.
 *
 * En la clase Alumno agregamos:
 *
 * @Persistent
 * private Materia materia;
 *
 * Este atributo permite navegar desde un Alumno hacia su Materia.
 */

private static void guardarUnoAMuchosBidireccional() {

    /*
     * Primero creamos la relación bidireccional que ya existe
     * entre Materia y Profesor:
     *
     * Materia  -> Profesor
     * Profesor -> Materia
     */

    Profesor profesor =
            new Profesor("Carlos", "Pérez");

    Materia materia =
            new Materia("Programación II", profesor);

    profesor.setMateria(materia);

    /*
     * Creamos los alumnos que estarán asociados a la materia.
     */

    Alumno alumno1 =
            new Alumno("Ana", "Gómez");

    Alumno alumno2 =
            new Alumno("Pedro", "López");

    Alumno alumno3 =
            new Alumno("Lucía", "Martínez");

    /*
     * agregarAlumno() debe mantener sincronizadas las dos
     * direcciones de la relación:
     *
     * Materia -> alumnos
     * Alumno  -> materia
     */

    materia.agregarAlumno(alumno1);
    materia.agregarAlumno(alumno2);
    materia.agregarAlumno(alumno3);

    IRepositorio<Materia> repositorio =
            new Repositorio<>("Programacion2");

    /*
     * Guardamos la materia junto con los objetos relacionados.
     */

    repositorio.guardar(materia);

    System.out.println("Materia guardada correctamente");
    System.out.println("ID de la materia: " + materia.getId());
}

/*
 * CONSULTA DESDE MATERIA
 *
 * Recuperamos una materia mediante su ID y recorremos su colección
 * de alumnos.
 *
 * Esta consulta demuestra la navegación:
 *
 * Materia -> alumnos
 */

private static void consultarDesdeMateriaUnoAMuchosBidireccional(int id) {

    IRepositorio<Materia> repositorioMateria =
            new Repositorio<>("Programacion2");

    Materia materia =
            repositorioMateria.consultar(Materia.class, id);

    System.out.println("Materia: " + materia.getNombre());

    System.out.println("Alumnos:");

    for (Alumno alumno : materia.getAlumnos()) {

        System.out.println(
                alumno.getNombre()
                + " "
                + alumno.getApellido()
        );
    }
}

/*
 * CONSULTA DESDE ALUMNO
 *
 * Recuperamos un alumno mediante su ID y accedemos a la materia
 * asociada mediante alumno.getMateria().
 *
 * Esta consulta demuestra la nueva dirección:
 *
 * Alumno -> Materia
 *
 * Esta navegación es posible porque la clase Alumno tiene:
 *
 * @Persistent
 * private Materia materia;
 */

private static void consultarDesdeAlumnoUnoAMuchosBidireccional(int id) {

    IRepositorio<Alumno> repositorioAlumno =
            new Repositorio<>("Programacion2");

    Alumno alumno =
            repositorioAlumno.consultar(Alumno.class, id);

    System.out.println(
            "Alumno: "
            + alumno.getNombre()
            + " "
            + alumno.getApellido()
    );

    System.out.println(
            "Materia: "
            + alumno.getMateria().getNombre()
    );
}
}
