package com.example.projeto.exception;

public class RepasseNotFoundException extends RuntimeException {
    public RepasseNotFoundException(Long id) {
        super("Repasse com ID " + id + " não encontrado");
    }
}