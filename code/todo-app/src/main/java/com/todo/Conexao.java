package com.todo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    // URL do seu banco local
    private static final String URL = "jdbc:mysql://localhost:3306/todo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection abrir() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}