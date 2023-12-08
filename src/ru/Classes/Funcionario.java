package ru.Classes;

public class Funcionario {
    private int masp;
    private int cod_usuario;

    // Construtor
    public Funcionario(int masp, int cod_usuario) {
        this.masp = masp;
        this.cod_usuario = cod_usuario;
    }

    // Getters e setters
    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getCodUsuario() {
        return cod_usuario;
    }

    public void setCodUsuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }
}
