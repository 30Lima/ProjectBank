package com.example.bancodigital.model;

public class Saque {

    private int idConta;  // ID da conta a ser sacada
    private double valor; // Valor do saque

    // Construtores
    public Saque() {}

    public Saque(int idConta, double valor) {
        this.idConta = idConta;
        this.valor = valor;
    }

    // Getters e Setters
    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
