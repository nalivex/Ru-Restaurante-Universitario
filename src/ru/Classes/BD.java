package ru.Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class BD {
    private Connection conexao;
    private static BD instancia;

    private BD() throws SQLException {
        conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
    }

    public Connection getConnection() {
        return conexao;
    }

    public static BD getInstancia() throws SQLException {
        if (instancia == null)
            instancia = new BD();
        return instancia;
    }
}
