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
import java.util.Optional;

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

     // Endpoint para retornar uma conta por ID
     @GetMapping("/{id}")
     public ResponseEntity<Conta> getContaPorId(@PathVariable int id) {
         Optional<Conta> conta = contas.stream().filter(c -> c.getId() == id).findFirst();
         if (conta.isPresent()) {
             return ResponseEntity.ok(conta.get());
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 se não encontrar
         }
     }
 
     // Endpoint para retornar uma conta por CPF do titular
     @GetMapping("/cpf/{cpf}")
     public ResponseEntity<Conta> getContaPorCpf(@PathVariable String cpf) {
         Optional<Conta> conta = contas.stream().filter(c -> c.getCpfTitular().equals(cpf)).findFirst();
         if (conta.isPresent()) {
             return ResponseEntity.ok(conta.get());
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 se não encontrar
         }
     }

      // Endpoint para encerrar uma conta (marcar como inativa)
    @PutMapping("/encerrar/{id}")
    public ResponseEntity<String> encerrarConta(@PathVariable int id) {
        Optional<Conta> contaOpt = contas.stream().filter(c -> c.getId() == id).findFirst();
        
        if (contaOpt.isPresent()) {
            Conta conta = contaOpt.get();
            conta.setAtiva(false); // Marca a conta como inativa
            return ResponseEntity.ok("Conta encerrada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada!");
        }
    }
}
