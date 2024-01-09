package br.thalison.application;

import br.thalison.model.dao.FabricaDao;
import br.thalison.model.dao.VendedorDao;
import br.thalison.model.dao.implementacao.VendedorDaoJDBC;
import br.thalison.model.entities.Departamento;
import br.thalison.model.entities.Vendedor;

import java.util.List;

public class Programa {
    public static void main(String[] args){

        VendedorDao vendedorDao = FabricaDao.criarVendedorDao();

        Vendedor vendedor = vendedorDao.findByID(3);

        System.out.println(vendedor);



        Departamento departamento  = new Departamento(2,null);
        System.out.println("===========findByDepartamento1===========");
        List<Vendedor> list =  vendedorDao.findByDepartamento(departamento);

        for (Vendedor v : list){
            System.out.println(v);
        }


        System.out.println("===========findByAll===========");
        list =  vendedorDao.findAll();

        for (Vendedor v : list){
            System.out.println(v);
        }

    }
}