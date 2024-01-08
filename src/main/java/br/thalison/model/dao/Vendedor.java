package br.thalison.model.dao;

import java.util.List;

public interface Vendedor {

    void iserir(Vendedor vendedor);
    void update(Vendedor vendedor);
    void deleteById(Integer id);
    Vendedor findByID(Integer id);
    List<Vendedor> findAll();
}
