package br.thalison.model.dao.implementacao;

import br.thalison.db.DB;
import br.thalison.db.DbException;
import br.thalison.model.dao.VendedorDao;
import br.thalison.model.entities.Departamento;
import br.thalison.model.entities.Vendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendedorDaoJDBC implements VendedorDao {

    private Connection connection;

    public VendedorDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void iserir(VendedorDao vendedor) {

    }

    @Override
    public void update(VendedorDao vendedor) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Vendedor findByID(Integer id) {
        PreparedStatement st = null;
        ResultSet resultSet = null;

        try {
            st = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ? ");

            st.setInt(1,id);
            resultSet = st.executeQuery();
            if (resultSet.next()){
                //Metodos instanciados abaixo
                Departamento departamento = instanciaDepartamento(resultSet);
                Vendedor vendedor = instanciaVendedor(resultSet , departamento);
                return vendedor;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }

    }

    private Vendedor instanciaVendedor(ResultSet resultSet , Departamento departamento)throws SQLException{

       Vendedor vendedor =  new Vendedor();
        vendedor.setId(resultSet.getInt("Id"));
        vendedor.setNome(resultSet.getString("Name"));
        vendedor.setEmail(resultSet.getString("Email"));
        vendedor.setSalarioBase(resultSet.getDouble("BaseSalary"));
        vendedor.setDataAnviersario(resultSet.getDate("BirthDate"));
        vendedor.setDepartamento(departamento);

        return vendedor;
    }

    private Departamento instanciaDepartamento(ResultSet resultSet)throws SQLException {
       Departamento departamento = new Departamento();
        departamento.setId(resultSet.getInt("DepartmentId"));
        departamento.setNome(resultSet.getString("DepName"));
        return departamento;
    }

    @Override
    public List<Vendedor> findAll() {
        PreparedStatement st = null;
        ResultSet resultSet = null;

        try {
            st = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            resultSet = st.executeQuery();
            //Criação da lista para adicionar e apresentar futuramente
            List<Vendedor> vendedorList = new ArrayList<>();
            Map<Integer , Departamento> map = new HashMap<>();

            while (resultSet.next()){
                //Metodos instanciados abaixo

                Departamento dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instanciaDepartamento(resultSet);
                    map.put(resultSet.getInt("DepartmentId"),dep);
                }
                Vendedor vend = instanciaVendedor(resultSet , dep);
                vendedorList.add(vend);
            }
            return vendedorList;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Vendedor> findByDepartamento(Departamento departamento) {
        PreparedStatement st = null;
        ResultSet resultSet = null;

        try {
            st = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            st.setInt(1,departamento.getId());
            resultSet = st.executeQuery();


            //Criação da lista para adicionar e apresentar futuramente
            List<Vendedor> vendedorList = new ArrayList<>();
            Map<Integer , Departamento> map = new HashMap<>();

            while (resultSet.next()){
                //Metodos instanciados abaixo

                Departamento dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                     dep = instanciaDepartamento(resultSet);
                     map.put(resultSet.getInt("DepartmentId"),dep);
                }
                Vendedor vend = instanciaVendedor(resultSet , dep);
                vendedorList.add(vend);
            }
            return vendedorList;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }
    }
}
