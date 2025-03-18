package com.example.bancodigital.model;

public class Deposito {

    private int idConta;  // ID da conta a ser depositado
    private double valor; // Valor do depósito

    // Construtores
    public Deposito() {}

    public Deposito(int idConta, double valor) {
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
