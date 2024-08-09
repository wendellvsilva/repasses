package com.example.projeto.dto;

import com.example.projeto.model.enums.FormaPagamento;
import com.example.projeto.model.enums.SistemaOrigem;
import com.example.projeto.model.enums.TipoRepasse;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RepasseDTO {

    @NotNull
    private TipoRepasse tipoRepasse;

    @NotNull
    @Positive
    private BigDecimal valorRepasse;

    @NotNull
    @Future
    private LocalDateTime dataVencimento;

    @NotNull
    private FormaPagamento formaPagamento;

    @NotNull
    private SistemaOrigem sistemaOrigem;
}
