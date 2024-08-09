package com.example.projeto.mappers;

import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.model.Repasse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RepasseMapper {
    public Repasse fromDTO(RepasseDTO dto) {
        return Repasse.builder()
                .tipoRepasse(dto.tipoRepasse())
                .valorRepasse(dto.valorRepasse())
                .dataVencimento(dto.dataVencimento())
                .formaPagamento(dto.formaPagamento())
                .sistemaOrigem(dto.sistemaOrigem())
                .dataCriacao(LocalDateTime.now())
                .build();
    }
}
