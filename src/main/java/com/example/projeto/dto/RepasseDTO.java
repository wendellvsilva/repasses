package com.example.projeto.dto;

import com.example.projeto.model.enums.FormaPagamento;
import com.example.projeto.model.enums.SistemaOrigem;
import com.example.projeto.model.enums.TipoRepasse;
import jakarta.validation.constraints.*;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record RepasseDTO(
        @NotNull
        TipoRepasse tipoRepasse,
        @NotNull
        @Positive
        BigDecimal valorRepasse,
        @NotNull
        @Future
        LocalDateTime dataVencimento,
        @NotNull
        FormaPagamento formaPagamento,
        @NotNull
        SistemaOrigem sistemaOrigem
) {
}
