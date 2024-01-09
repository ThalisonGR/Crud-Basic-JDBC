package br.thalison.model.dao.implementacao;

import br.thalison.db.DB;
import br.thalison.db.DbException;
import br.thalison.model.dao.VendedorDao;
import br.thalison.model.entities.Departamento;
import br.thalison.model.entities.Vendedor;

import java.sql.*;
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
    public void iserir(Vendedor vendedor) {

        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1,vendedor.getNome());
            st.setString(2, vendedor.getEmail());
            st.setDate(3, new java.sql.Date(vendedor.getDataAnviersario().getTime()));
            st.setDouble(4,vendedor.getSalarioBase());
            st.setInt(5,vendedor.getDepartamento().getId());

            int linhasAlteradas = st.executeUpdate();

            if(linhasAlteradas > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    vendedor.setId(id);
                }
                DB.closeResultSet(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Vendedor vendedor) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?");

            st.setString(1, vendedor.getNome());
            st.setString(2, vendedor.getEmail());
            st.setDate(3, new Date(vendedor.getDataAnviersario().getTime()));
            st.setDouble(4, vendedor.getSalarioBase());
            st.setInt(5, vendedor.getDepartamento().getId());
            st.setInt(6, vendedor.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("DELETE FROM seller WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }


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

}



