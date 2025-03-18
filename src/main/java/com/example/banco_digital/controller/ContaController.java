package com.example.bancodigital.controller;

import com.example.bancodigital.model.Conta;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contas")  
public class ContaController {

    private List<Conta> contas = new ArrayList<>();  

    @PostMapping
    public String cadastrarConta(@RequestBody Conta conta) {
        contas.add(conta);  
        return "Conta cadastrada com sucesso!";
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contas;
    }
}
