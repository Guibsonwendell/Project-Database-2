package com.todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listar() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM categoria ORDER BY id";
        try (Connection con = Conexao.abrir();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("id"), rs.getString("nome")));
            }
        }
        return lista;
    }

    public void inserir(String nome) throws Exception {
        String sql = "INSERT INTO categoria (nome) VALUES (?)";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.executeUpdate();
        }
    }

    public void editar(int id, String novoNome) throws Exception {
        String sql = "UPDATE categoria SET nome = ? WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, novoNome);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws Exception {
        if (id == 1) throw new Exception("A categoria 'Geral' é padrão e não pode ser excluída.");

        String sql = "DELETE FROM categoria WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new Exception("Não é possível excluir: existem tarefas usando esta categoria.");
        }
    }
}