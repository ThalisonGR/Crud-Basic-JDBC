package br.thalison.model.dao;

import br.thalison.model.dao.implementacao.VendedorDaoJDBC;

public class FabricaDao {

    public static VendedorDao criarVendedorDao(){
        return new VendedorDaoJDBC();
    }
}
