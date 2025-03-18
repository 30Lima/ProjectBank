package com.example.bancodigital.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String exibirInformacoes() {
        return "Dream Bank - Integrantes: Pedro Henrique Lima Santos - RM558243, Vitor Gomes Martins RM558244";
    }
}
