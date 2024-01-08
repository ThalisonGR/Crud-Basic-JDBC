package br.thalison.application;

import br.thalison.model.entities.Departamento;
import br.thalison.model.entities.Vendedor;

public class Programa {
    public static void main(String[] args){


        Departamento departamento = new Departamento(2,"Livro");
        System.out.println(departamento);

        Vendedor vendedor = new Vendedor(2,"Thalison","@",null,3000.00, departamento);
        System.out.println(vendedor);


    }
}