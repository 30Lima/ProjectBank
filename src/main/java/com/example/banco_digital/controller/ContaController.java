package com.example.bancodigital.controller;

import com.example.bancodigital.model.Conta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contas")  
public class ContaController {

    // Lista para armazenar as contas em memória
    private List<Conta> contas = new ArrayList<>();

    @PostMapping
    public ResponseEntity<String> cadastrarConta(@Valid @RequestBody Conta conta, BindingResult result) {
        // Verificando se houve erro de validação
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString()); // Retorna o erro 400
        }

        contas.add(conta);  // Salva a conta na memória
        return ResponseEntity.status(HttpStatus.CREATED).body("Conta cadastrada com sucesso!"); // Retorna sucesso
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contas;
    }
}
