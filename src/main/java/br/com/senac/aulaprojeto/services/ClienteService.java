package br.com.senac.aulaprojeto.services;

import br.com.senac.aulaprojeto.db.ConexaoDataBase;
import br.com.senac.aulaprojeto.model.Cliente;
import br.com.senac.aulaprojeto.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteService  {

    private static ConexaoDataBase conexao = new ConexaoDataBase();

    public static List <Cliente> carregarClientes(){
        List<Cliente> out = new ArrayList<>();

        try {
            Connection conn = conexao.getConexao();

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("select * from clientes;");

            while (rs.next()){
                Cliente cli = new Cliente(rs.getInt("id"),
                    rs.getString("documento"),rs.getString("nome"),
                            rs.getInt("rg"),rs.getString("email"),
                            rs.getString("telefone"));
                    out.add(cli);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    public static void inserirClientes(Cliente cliente){
        try {
            Connection conn = conexao.getConexao();

            String sqlInsert = "insert into clientes(documento,nome,rg,email,telefone) values (?,?,?,?,?)";

            PreparedStatement pre= conn.prepareStatement(sqlInsert);
            pre.setString(1, cliente.getDocumento());
            pre.setString(2, cliente.getNome());
            pre.setInt(3, cliente.getRg());
            pre.setString(4,cliente.getEmail());
            pre.setString(5,cliente.getTelefone());

            pre.execute();

            pre.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static  boolean deletarCliente (int idCliente){
        try {
            Connection conn = conexao.getConexao();

            String deleteSql = "delete from clientes where id = ?";

            PreparedStatement del = conn.prepareStatement(deleteSql);
            del.setInt(1,idCliente);

            return del.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean atualizarCliente (int idCliente, Cliente cli){
        try {
            Connection conn = conexao.getConexao();

            String updateSql = "update clientes set documento = ?, nome = ?, rg = ?, email = ?, telefone = ? where id= ?";

            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setString(1,cli.getDocumento());
            ps.setString(2,cli.getNome());
            ps.setInt(3,cli.getRg());
            ps.setString(4,cli.getEmail());
            ps.setString(5, cli.getTelefone());
            ps.setInt(6,idCliente);

            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean buscarClienteByDocumento (String documento){
        try {
            Connection conn = conexao.getConexao();

            String selectSql = "select * from clientes where documento= '" + documento +"'";

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery(selectSql);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
