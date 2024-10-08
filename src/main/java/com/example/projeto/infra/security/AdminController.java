package com.example.projeto.infra.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Dashboard do administrador. Somente ADMIN pode ver isso.";
    }

    @GetMapping("/ferramentas")
    public String adminManagement() {
        return "Ferramentas de gestão disponíveis apenas para ADMIN.";
    }
}
