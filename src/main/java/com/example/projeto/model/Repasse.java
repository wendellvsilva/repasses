package com.example.projeto.model;

import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.model.enums.FormaPagamento;
import com.example.projeto.model.enums.SistemaOrigem;
import com.example.projeto.model.enums.TipoRepasse;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoRepasse tipoRepasse;

    private BigDecimal valorRepasse;

    private LocalDateTime dataVencimento;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private SistemaOrigem sistemaOrigem;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public static Repasse fromDTO(RepasseDTO dto) {
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