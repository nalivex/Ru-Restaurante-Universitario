
package ru;
import java.sql.*;

public class Ru {

   public static void main(String[] args) throws SQLException {
        BD banco = BD.getInstancia();
        Connection connection = banco.getConnection();
        Statement st = (Statement) connection.createStatement();
        try (ResultSet res = ((java.sql.Statement) st).executeQuery("select * from bd")) {
            System.out.println("Quantidade de colunas: " + res.getMetaData().getColumnCount());
            while (res.next()) {
                
            }
        } catch (SQLException e) {
        }
    }
}
