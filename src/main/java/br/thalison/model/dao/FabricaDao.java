package br.thalison.model.dao;

import br.thalison.db.DB;
import br.thalison.model.dao.implementacao.VendedorDaoJDBC;

public class FabricaDao {

    public static VendedorDao criarVendedorDao(){
        return new VendedorDaoJDBC(DB.getConnection());
    }
}
