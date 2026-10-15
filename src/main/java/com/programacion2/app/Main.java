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

        // guardarUnoAUnoUnidireccional();
        // consultarUnoAUnoUnidireccional(1);

        // guardarUnoAUnoBidireccional();
         //consultarDesdeMateriaUnoAUnoBidireccional(1);
        // consultarDesdeProfesorUnoAUnoBidireccional(1);

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

    Profesor profesor =
            new Profesor("Carlos", "Pérez");

    Materia materia =
            new Materia("Programación II", profesor);

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

    IRepositorio<Materia> repositorio =
            new Repositorio<>("Programacion2");

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

    IRepositorio<Profesor> repositorio =
            new Repositorio<>("Programacion2");

    Profesor profesor =
            repositorio.consultar(Profesor.class, id);

    System.out.println(
            "Profesor: "
            + profesor.getNombre()
            + " "
            + profesor.getApellido()
    );

    System.out.println(
            "Materia: "
            + profesor.getMateria().getNombre()
    );
}
}
