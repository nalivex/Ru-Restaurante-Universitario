package ru.Classes;

public class Aluno {
    private int matricula;
    private int cod_usuario;

    // Construtor
    public Aluno(int matricula, int cod_usuario) {
        this.matricula = matricula;
        this.cod_usuario = cod_usuario;
    }

    // Getters e setters
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getCodUsuario() {
        return cod_usuario;
    }

    public void setCodUsuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

}
