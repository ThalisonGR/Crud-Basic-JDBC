package br.thalison.model.dao;

import java.util.List;

public interface VendedorDao {

    void iserir(VendedorDao vendedor);
    void update(VendedorDao vendedor);
    void deleteById(Integer id);
    VendedorDao findByID(Integer id);
    List<VendedorDao> findAll();
}
