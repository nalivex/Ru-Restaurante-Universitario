package ru;

import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Usuario {
    private int cod_usuario;
    private String Nome;
    private String Email;
    private Boolean Sexo;
    private int Senha;
    private int Telefone;
    private Date data_de_nascimento;
    private double Saldo_credito;
    
    public boolean Existe(int Codigo) throws SQLException{
        BD banco = BD.getInstancia();
        Connection connection = banco.getConnection();
        String ConsultaSQL = "select * from usuario where cod_usuario = ?";
        ResultSet rs;
        
        try {
            PreparedStatement Query1 = connection.prepareStatement(ConsultaSQL);
            Query1.setInt(1, Codigo);
            rs = Query1.executeQuery();
            if (rs.next()) {
                this.cod_usuario = rs.getInt("cod_usuario");
                this.Nome = rs.getString("nome");
                this.Email = rs.getString("email");
                this.Sexo = rs.getBoolean("sexo");
                this.Telefone = rs.getInt("telefone");
                this.data_de_nascimento = rs.getDate("data_de_nascimento");
                this.Saldo_credito = rs.getDouble("Saldo_credito");
            }
            else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public int getcod_usuario() {
        return cod_usuario;
    }

    public void setcod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }
    
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getTelefone() {
        return Telefone;
    }

    public void setTelefone(int Telefone) {
        this.Telefone = Telefone;
    }
    
    public Boolean getSexo() {
        return Sexo;
    }

    public void setTelefone(Boolean Sexo) {
        this.Sexo = Sexo;
    }
    
    public int getSenha() {
        return Senha;
    }

    public void setSenha(int Senha) {
        this.Senha = Senha;
    }

    public Date getdata_de_nascimento() {
        return data_de_nascimento;
    }

    public void setdata_de_nascimento(Date data_de_nascimento) {
        this.data_de_nascimento = data_de_nascimento;
    }
    
    public double getSaldo_credito() {
        return Saldo_credito;
    }

    public void setSaldo_credito(double Saldo_credito) {
        this.Saldo_credito = Saldo_credito;
    }
    
  public int verificarMatricula(int matricula) {
    int codigoUsuario = 0;

    try {
        Connection c = BD.getInstancia().getConnection();
        String consultaSQL = "SELECT cod_usuario FROM Alunos WHERE Matricula = ?";
        PreparedStatement query = c.prepareStatement(consultaSQL);
        query.setInt(1, matricula);

        ResultSet resultado = query.executeQuery();

        if (resultado.next()) {
            codigoUsuario = resultado.getInt("cod_usuario");
        } else {
            consultaSQL = "SELECT cod_usuario FROM Funcionarios WHERE MASP = ?";
            query = c.prepareStatement(consultaSQL);
            query.setInt(1, matricula);

            resultado = query.executeQuery();

            if (resultado.next()) {
                codigoUsuario = resultado.getInt("cod_usuario");
            } else {
                // Não há necessidade de procurar na tabela 'Usuario' se não encontrar nas outras
                // Remova esta parte caso não seja necessário
                consultaSQL = "SELECT Cod_Usuario FROM Usuario WHERE Cod_Usuario = ?";
                query = c.prepareStatement(consultaSQL);
                query.setInt(1, matricula);

                resultado = query.executeQuery();

                if (resultado.next()) {
                    codigoUsuario = resultado.getInt("Cod_Usuario");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return codigoUsuario;
}

    
    public boolean atualizarSaldo(int codigoUsuario, double novoSaldo) {
    try {
        Connection c = BD.getInstancia().getConnection();

        String consultaSaldoAtual = "SELECT Saldo_credito FROM Usuario WHERE Cod_Usuario = ?";
        PreparedStatement querySaldoAtual = c.prepareStatement(consultaSaldoAtual);
        querySaldoAtual.setInt(1, codigoUsuario);

        ResultSet resultadoSaldoAtual = querySaldoAtual.executeQuery();
        double saldoAtual = 0;

        if (resultadoSaldoAtual.next()) {
            saldoAtual = resultadoSaldoAtual.getDouble("Saldo_credito");
        }

        double saldoAtualizado = saldoAtual + novoSaldo;

        String consultaSQL = "UPDATE Usuario SET Saldo_credito = ? WHERE Cod_Usuario = ?";
        PreparedStatement query = c.prepareStatement(consultaSQL);
        query.setDouble(1, saldoAtualizado);
        query.setInt(2, codigoUsuario);

        int linhasAfetadas = query.executeUpdate();
        return linhasAfetadas > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public String verificarTipoUsuario(int matricula) {
        String tipoUsuario = "";

        try {
            Connection c = BD.getInstancia().getConnection();

            String consultaAlunos = "SELECT cod_usuario FROM Alunos WHERE Matricula = ?";
            PreparedStatement queryAlunos = c.prepareStatement(consultaAlunos);
            queryAlunos.setInt(1, matricula);

            ResultSet resultadoAlunos = queryAlunos.executeQuery();

            if (resultadoAlunos.next()) {
                tipoUsuario = "Aluno";
            } else {
                String consultaFuncionarios = "SELECT cod_usuario FROM Funcionarios WHERE MASP = ?";
                PreparedStatement queryFuncionarios = c.prepareStatement(consultaFuncionarios);
                queryFuncionarios.setInt(1, matricula);

                ResultSet resultadoFuncionarios = queryFuncionarios.executeQuery();

                if (resultadoFuncionarios.next()) {
                    tipoUsuario = "Funcionário";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoUsuario;
    }

    public DefaultComboBoxModel<String> obterRefeicoesPorTipoUsuario(String tipoUsuario) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        try {
            Connection connection = BD.getInstancia().getConnection();
            String query = "";

            if (tipoUsuario.equals("Aluno")) {
                query = "SELECT Cod_refeicao, Nome_refeicao, Valor FROM Refeicao WHERE Cod_refeicao BETWEEN 1 AND 3";
            } else if (tipoUsuario.equals("Funcionário")) {
                query = "SELECT Cod_refeicao, Nome_refeicao, Valor FROM Refeicao WHERE Cod_refeicao BETWEEN 4 AND 6";
            } else {
                // Se o tipo de usuário não for reconhecido, retornar um modelo vazio
                return model;
            }

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double ValorRefeicao = resultSet.getDouble("Valor");
                String nomeRefeicao = resultSet.getString("Nome_refeicao");
                model.addElement(nomeRefeicao + ", R$ " + ValorRefeicao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    
    
    public double obterSaldo(int codUsuario) {
        double saldo = 0;

        try {
            Connection c = BD.getInstancia().getConnection();

            String consultaSQL = "SELECT Saldo_credito FROM Usuario WHERE Cod_Usuario = ?";
            PreparedStatement query = c.prepareStatement(consultaSQL);
            query.setInt(1, codUsuario);

            ResultSet resultado = query.executeQuery();

            if (resultado.next()) {
                saldo = resultado.getDouble("Saldo_credito");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return saldo;
    }

    public double obterValorRefeicao(int codigoRefeicao) {
        double valorRefeicao = 0;

        try {
            Connection c = BD.getInstancia().getConnection();

            String consultaSQL = "SELECT Valor FROM Refeicao WHERE Cod_refeicao = ?";
            PreparedStatement query = c.prepareStatement(consultaSQL);
            query.setInt(1, codigoRefeicao);

            ResultSet resultado = query.executeQuery();

            if (resultado.next()) {
                valorRefeicao = resultado.getDouble("Valor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valorRefeicao;
    }
    
    public boolean atualizarSaldoCompra(int codUsuario, double novoSaldo) {
        try {
            Connection c = BD.getInstancia().getConnection();

            String consultaSQL = "UPDATE Usuario SET Saldo_credito = ? WHERE Cod_Usuario = ?";
            PreparedStatement query = c.prepareStatement(consultaSQL);
            query.setDouble(1, novoSaldo);
            query.setInt(2, codUsuario);

            int linhasAfetadas = query.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}




