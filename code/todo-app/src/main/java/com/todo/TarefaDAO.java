package com.todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public List<Tarefa> listar() throws Exception {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao_cifrada, status, cat_nome FROM vw_tarefas_detalhadas";

        try (Connection con = Conexao.abrir();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Tarefa(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao_cifrada"),
                        rs.getString("status"),
                        rs.getString("cat_nome")
                ));
            }
        }
        return lista;
    }

    public List<Tarefa> listarPorStatus(String status) throws Exception {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, descricao_cifrada, status, cat_nome FROM vw_tarefas_detalhadas WHERE status = ?";

        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Tarefa(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("descricao_cifrada"),
                            rs.getString("status"),
                            rs.getString("cat_nome")
                    ));
                }
            }
        }
        return lista;
    }

    // AGORA COM CRIPTOGRAFIA E CATEGORIA PADRÃO
    public void inserir(String titulo, String descricao, int categoriaId) throws Exception {
        // Se o ID da categoria for 0 (não informado), usa o 1 (Geral)
        int catId = (categoriaId <= 0) ? 1 : categoriaId;

        // Criptografa a descrição antes de salvar
        String descCifrada = CriptoUtil.cifrar(descricao);

        String sql = "INSERT INTO tarefa (titulo, descricao_cifrada, status, categoria_id) VALUES (?, ?, 'NOVA', ?)";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, descCifrada);
            ps.setInt(3, catId);
            ps.executeUpdate();
        }
    }

    public void atualizarStatus(int id, String novoStatus) throws Exception {
        String sql = "UPDATE tarefa SET status = ?, concluida_em = IF(? = 'CONCLUIDA', CURRENT_TIMESTAMP, NULL) WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, novoStatus);
            ps.setString(2, novoStatus);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    public void editarTarefa(int id, String novoTitulo, String novaDescricao) throws Exception {
        // Criptografa a nova descrição antes de salvar
        String descCifrada = CriptoUtil.cifrar(novaDescricao);

        String sql = "UPDATE tarefa SET titulo = ?, descricao_cifrada = ? WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, novoTitulo);
            ps.setString(2, descCifrada);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws Exception {
        String sql = "DELETE FROM tarefa WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}