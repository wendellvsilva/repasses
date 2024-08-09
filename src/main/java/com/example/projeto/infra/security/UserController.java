package com.example.projeto.infra.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public String userProfile() {
        return "Perfil do usuário comum. Você está autenticado como USER.";
    }

    @GetMapping("/dados")
    public String userData() {
        return "Dados sensíveis visíveis para USER e ADMIN.";
    }
}