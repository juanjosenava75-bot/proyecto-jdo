package com.programacion2.repositorio;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class Repositorio <T> implements IRepositorio <T> {


    private  final PersistenceManagerFactory pmf;


    

    public Repositorio(String persistenceUnit) {

        this.pmf = JDOHelper.getPersistenceManagerFactory(persistenceUnit);
    }

    @Override
    public void guardar(Object objeto) {
        PersistenceManager pm = pmf.getPersistenceManager();
        pm.makePersistent(objeto);
        pm.close();
    }

    @Override
    public T consultar(Class <T> clase, int id) {
        PersistenceManager pm = pmf.getPersistenceManager();
       return  pm.getObjectById(clase,id);
    }

}
