package br.thalison.model.dao;

import br.thalison.model.entities.Departamento;
import br.thalison.model.entities.Vendedor;

import java.util.List;

public interface VendedorDao {

    void iserir(VendedorDao vendedor);
    void update(VendedorDao vendedor);
    void deleteById(Integer id);
    Vendedor findByID(Integer id);
    List<Vendedor> findAll();
    List<Vendedor> findByDepartamento(Departamento departamento);
}
