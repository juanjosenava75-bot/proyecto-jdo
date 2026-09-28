package com.programacion2.repositorio;

public interface IRepositorio <T> {

    void guardar (T objeto);
    T consultar (Class <T> clase , int id);
}
