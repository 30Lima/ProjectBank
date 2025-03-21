package com.example.bancodigital.model;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class Conta {

    private static int contadorId = 0;
    private int id;
    private String numero;
    private String agencia;

    @NotNull(message = "O nome do titular é obrigatório.")
    private String nomeTitular;

    @NotNull(message = "O CPF do titular é obrigatório.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido. O formato deve ser XXX.XXX.XXX-XX.")
    private String cpfTitular;

    @NotNull(message = "A data de abertura é obrigatória.")
    @PastOrPresent(message = "A data de abertura não pode ser no futuro.")
    private LocalDate dataAbertura;

    @DecimalMin(value = "0.0", inclusive = false, message = "O saldo inicial não pode ser negativo.")
    private Double saldoInicial;

    @NotNull(message = "O tipo de conta é obrigatório.")
    @Pattern(regexp = "corrente|poupança|salário", message = "Tipo de conta inválido. Os tipos válidos são: corrente, poupança ou salário.")
    private String tipo;

    private boolean ativa; // Campo para verificar se a conta está ativa

    // Construtor para inicializar a conta com ID
    public Conta() {
        this.id = ++contadorId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
