package com.todo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class EstatisticaService {
    public void mostrar() throws Exception {
        String sql = "{CALL sp_estatisticas()}";
        try (Connection con = Conexao.abrir();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            System.out.println("\n--- ESTATÍSTICAS DO BANCO ---");
            while (rs.next()) {
                System.out.printf("%-12s : %d%n", rs.getString("metrica"), rs.getInt("valor"));
            }
        }
    }
}