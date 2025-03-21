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

    @PutMapping("/deposito")
    public ResponseEntity<Conta> realizarDeposito(@RequestBody Deposito deposito) {
    // Busca a conta pelo ID
    Optional<Conta> contaOpt = contas.stream().filter(c -> c.getId() == deposito.getIdConta()).findFirst();
    
    if (contaOpt.isPresent()) {
        Conta conta = contaOpt.get();
        
        // Verifica se o valor do depósito é válido
        if (deposito.getValor() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Se o valor for inválido, retorna erro 400
            }
            
            // Realiza o depósito, somando o valor ao saldo da conta
            conta.setSaldoInicial(conta.getSaldoInicial() + deposito.getValor());
            
            // Retorna a conta atualizada com os dados do saldo
            return ResponseEntity.ok(conta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Retorna erro 404 se a conta não for encontrada
        }
    }

    @PutMapping("/saque")
    public ResponseEntity<String> realizarSaque(@RequestBody Saque saque) {
        // Busca a conta pelo ID
        Optional<Conta> contaOpt = contas.stream().filter(c -> c.getId() == saque.getIdConta()).findFirst();

        if (contaOpt.isPresent()) {
            Conta conta = contaOpt.get();

            // Verifica se o valor do saque é válido (não pode ser maior que o saldo da conta)
            if (saque.getValor() <= conta.getSaldoInicial() && saque.getValor() > 0) {
                conta.setSaldoInicial(conta.getSaldoInicial() - saque.getValor()); // Subtrai o valor do saldo
                return ResponseEntity.ok("Saque realizado com sucesso! Saldo atualizado: " + conta.getSaldoInicial());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente ou valor inválido.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada!");
        }
    }

        @PutMapping("/pix")
    public ResponseEntity<String> realizarPix(@RequestBody Pix pix) {
        // Busca a conta de origem pelo ID
        Optional<Conta> contaOrigemOpt = contas.stream().filter(c -> c.getId() == pix.getIdContaOrigem()).findFirst();
        // Busca a conta de destino pelo ID
        Optional<Conta> contaDestinoOpt = contas.stream().filter(c -> c.getId() == pix.getIdContaDestino()).findFirst();

        // Verifica se ambas as contas existem
        if (contaOrigemOpt.isPresent() && contaDestinoOpt.isPresent()) {
            Conta contaOrigem = contaOrigemOpt.get();
            Conta contaDestino = contaDestinoOpt.get();

            // Verifica se o valor do PIX é válido (não pode ser maior que o saldo da conta de origem)
            if (pix.getValor() <= contaOrigem.getSaldoInicial() && pix.getValor() > 0) {
                // Subtrai o valor da conta de origem
                contaOrigem.setSaldoInicial(contaOrigem.getSaldoInicial() - pix.getValor());
                // Adiciona o valor à conta de destino
                contaDestino.setSaldoInicial(contaDestino.getSaldoInicial() + pix.getValor());

                // Retorna os dados atualizados da conta de origem
                return ResponseEntity.ok("PIX realizado com sucesso! Saldo da conta de origem atualizado: " + contaOrigem.getSaldoInicial());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente ou valor inválido.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Uma ou ambas as contas não foram encontradas.");
        }
    }
}
