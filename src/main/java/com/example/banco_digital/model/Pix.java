package com.example.bancodigital.model;

public class Pix {

    private int idContaOrigem;  // ID da conta de origem
    private int idContaDestino; // ID da conta de destino
    private double valor;      // Valor do PIX

    // Construtores
    public Pix() {}

    public Pix(int idContaOrigem, int idContaDestino, double valor) {
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.valor = valor;
    }

    // Getters e Setters
    public int getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(int idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public int getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(int idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
