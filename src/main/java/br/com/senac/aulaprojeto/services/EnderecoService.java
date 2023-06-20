package br.com.senac.aulaprojeto.services;

import br.com.senac.aulaprojeto.db.ConexaoDataBase;
import br.com.senac.aulaprojeto.model.Cliente;
import br.com.senac.aulaprojeto.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoService {
    private static ConexaoDataBase conexao = new ConexaoDataBase();
    public static List <Endereco> carregarEnderecos(){
        List<Endereco> aut = new ArrayList<>();

        try {
            Connection cann = conexao.getConexao();

            Statement ste = cann.createStatement();
            ResultSet end = ste.executeQuery("select * from enderecos");

            while (end.next()){
                Endereco ende = new Endereco(end.getInt("id"),end.getString("cep"),
                        end.getString("bairro"),end.getString("numero"),
                        end.getString("cidade"),end.getString("estado"));
                aut.add(ende);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aut;
    }

   public static void inserirEndereco(Endereco endereco){
        try {
            Connection conn = conexao.getConexao();

            String sqlInsert = "insert into enderecos(id_cliente,cep,bairro,numero,cidade,estado) values (?,?,?,?,?,?)";

            PreparedStatement der= conn.prepareStatement(sqlInsert);
            der.setInt(1,endereco.getId_cliente());
            der.setString(2, endereco.getCep());
            der.setString(3, endereco.getBairro());
            der.setString(4,endereco.getNumero());
            der.setString(5,endereco.getCidade());
            der.setString(6,endereco.getEstado());

            der.execute();

            der.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static  boolean deletarEndereco (int idEndereco){
        try {
            Connection conn = conexao.getConexao();

            String deleteSql = "delete from enderecos where id = ?";

            PreparedStatement delend = conn.prepareStatement(deleteSql);
            delend.setInt(1,idEndereco);

            return delend.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean atualizarEndereco (int idEndereco, Endereco end){
        try {
            Connection conn = conexao.getConexao();

            String updateSql = "update enderecos set id_cliente = ?, cep = ?, bairro = ?, numero = ?, cidade = ?, estado = ? where id= ?";

            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setInt(1,end.getId_cliente());
            ps.setString(2,end.getCep());
            ps.setString(3, end.getBairro());
            ps.setString(4,end.getNumero());
            ps.setString(5, end.getCidade());
            ps.setString(6, end.getEstado());
            ps.setInt(7,idEndereco);

            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean buscarEnderecoByCep (String cep){
        try {
            Connection conn = conexao.getConexao();

            String selectSql = "select * from enderecos where cep= '" + cep +"'";

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery(selectSql);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
