package br.thalison.model.dao;

import br.thalison.model.entities.Departamento;

import java.util.List;

public interface DepartamentoDao {

    void iserir(Departamento departamento);
    void update(Departamento departamento);
    void deleteById(Integer id);
    Departamento findByID(Integer id);
    List<Departamento> findAll();
}
