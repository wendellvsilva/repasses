package com.example.projeto.service;

import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.exception.RepasseNotFoundException;
import com.example.projeto.mappers.RepasseMapper;
import com.example.projeto.model.Repasse;
import com.example.projeto.model.enums.TipoRepasse;
import com.example.projeto.repository.RepasseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.projeto.model.enums.FormaPagamento.TRANSFERENCIA_BANCARIA;
import static com.example.projeto.model.enums.SistemaOrigem.ECOM;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RepasseServiceTest {

    @Mock
    private RepasseRepository repasseRepository;

    @Mock
    private RepasseMapper repasseMapper;

    @InjectMocks
    private RepasseService repasseService;

    private RepasseDTO repasseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repasseDTO = RepasseDTO.builder()
                .tipoRepasse(TipoRepasse.SELLER)
                .valorRepasse(BigDecimal.valueOf(1000.00))
                .dataVencimento(LocalDateTime.now().plusDays(1))
                .formaPagamento(TRANSFERENCIA_BANCARIA)
                .sistemaOrigem(ECOM)
                .build();
    }

    @Test
    @DisplayName("Salvar repasse com sucesso")
    void salvarRepasse_sucesso() {
        Repasse repasse = Repasse.builder()
                .tipoRepasse(repasseDTO.tipoRepasse())
                .valorRepasse(repasseDTO.valorRepasse())
                .dataVencimento(repasseDTO.dataVencimento())
                .formaPagamento(repasseDTO.formaPagamento())
                .sistemaOrigem(repasseDTO.sistemaOrigem())
                .build();

        when(repasseMapper.fromDTO(repasseDTO)).thenReturn(repasse);
        when(repasseRepository.save(any(Repasse.class))).thenReturn(repasse);

        Repasse savedRepasse = repasseService.salvarRepasse(repasseDTO);
        assertNotNull(savedRepasse);
        assertEquals(repasseDTO.valorRepasse(), savedRepasse.getValorRepasse());
        assertEquals(repasseDTO.tipoRepasse(), savedRepasse.getTipoRepasse());
        assertEquals(repasseDTO.dataVencimento(), savedRepasse.getDataVencimento());
        assertEquals(repasseDTO.formaPagamento(), savedRepasse.getFormaPagamento());
        assertEquals(repasseDTO.sistemaOrigem(), savedRepasse.getSistemaOrigem());
    }

    @Test
    @DisplayName("Obter repasse por ID com sucesso")
    void obterRepassePorId_sucesso() {
        Repasse repasse = Repasse.builder()
                .tipoRepasse(repasseDTO.tipoRepasse())
                .valorRepasse(repasseDTO.valorRepasse())
                .dataVencimento(repasseDTO.dataVencimento())
                .formaPagamento(repasseDTO.formaPagamento())
                .sistemaOrigem(repasseDTO.sistemaOrigem())
                .build();

        when(repasseRepository.findById(anyLong())).thenReturn(Optional.of(repasse));

        Repasse foundRepasse = repasseService.obterRepassePorId(1L);
        assertNotNull(foundRepasse);
        assertEquals(repasseDTO.tipoRepasse(), foundRepasse.getTipoRepasse());
        assertEquals(repasseDTO.valorRepasse(), foundRepasse.getValorRepasse());
        assertEquals(repasseDTO.dataVencimento(), foundRepasse.getDataVencimento());
        assertEquals(repasseDTO.formaPagamento(), foundRepasse.getFormaPagamento());
        assertEquals(repasseDTO.sistemaOrigem(), foundRepasse.getSistemaOrigem());
    }

    @Test
    @DisplayName("Lançar exceção quando repasse não for encontrado por ID")
    void obterRepassePorId_naoEncontrado() {
        when(repasseRepository.findById(1L)).thenReturn(Optional.empty());

        RepasseNotFoundException exception = assertThrows(RepasseNotFoundException.class, () -> {
            repasseService.obterRepassePorId(1L);
        });

        assertEquals("Repasse com ID 1 não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Atualizar repasse com sucesso")
    void atualizarRepasse_sucesso() {
        Repasse repasse = Repasse.builder()
                .tipoRepasse(repasseDTO.tipoRepasse())
                .valorRepasse(repasseDTO.valorRepasse())
                .dataVencimento(repasseDTO.dataVencimento())
                .formaPagamento(repasseDTO.formaPagamento())
                .sistemaOrigem(repasseDTO.sistemaOrigem())
                .build();

        when(repasseRepository.findById(anyLong())).thenReturn(Optional.of(repasse));
        when(repasseRepository.save(any(Repasse.class))).thenReturn(repasse);

        Repasse updatedRepasse = repasseService.atualizarRepasse(1L, repasseDTO);
        assertNotNull(updatedRepasse);
        assertEquals(repasseDTO.valorRepasse(), updatedRepasse.getValorRepasse());
        assertEquals(repasseDTO.tipoRepasse(), updatedRepasse.getTipoRepasse());
        assertEquals(repasseDTO.dataVencimento(), updatedRepasse.getDataVencimento());
        assertEquals(repasseDTO.formaPagamento(), updatedRepasse.getFormaPagamento());
        assertEquals(repasseDTO.sistemaOrigem(), updatedRepasse.getSistemaOrigem());
    }

    @Test
    @DisplayName("Remover repasse com sucesso")
    void removerRepasse_sucesso() {
        when(repasseRepository.existsById(1L)).thenReturn(true);
        doNothing().when(repasseRepository).deleteById(1L);
        assertDoesNotThrow(() -> repasseService.removerRepasse(1L));
        verify(repasseRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Lançar exceção ao tentar salvar repasse com valor nulo")
    void salvarRepasse_valorRepasseNulo() {
        repasseDTO = RepasseDTO.builder()
                .tipoRepasse(TipoRepasse.SELLER)
                .valorRepasse(null)
                .dataVencimento(LocalDateTime.now().plusDays(1))
                .formaPagamento(TRANSFERENCIA_BANCARIA)
                .sistemaOrigem(ECOM)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            repasseService.salvarRepasse(repasseDTO);
        });

        assertEquals("Valor do repasse não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("Lançar exceção ao tentar salvar repasse com data de vencimento no passado")
    void salvarRepasse_dataVencimentoNoPassado() {
        repasseDTO = RepasseDTO.builder()
                .tipoRepasse(TipoRepasse.SELLER)
                .valorRepasse(BigDecimal.valueOf(1000.00))
                .dataVencimento(LocalDateTime.now().minusDays(1)) // data no passado
                .formaPagamento(TRANSFERENCIA_BANCARIA)
                .sistemaOrigem(ECOM)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            repasseService.salvarRepasse(repasseDTO);
        });

        assertEquals("Data de vencimento deve ser no futuro", exception.getMessage());
    }

    @Test
    @DisplayName("Lançar exceção ao tentar salvar repasse com sistema de origem nulo")
    void salvarRepasse_sistemaOrigemNulo() {
        repasseDTO = RepasseDTO.builder()
                .tipoRepasse(TipoRepasse.SELLER)
                .valorRepasse(BigDecimal.valueOf(1000.00))
                .dataVencimento(LocalDateTime.now().plusDays(1))
                .formaPagamento(TRANSFERENCIA_BANCARIA)
                .sistemaOrigem(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            repasseService.salvarRepasse(repasseDTO);
        });

        assertEquals("Sistema de origem não pode ser nulo", exception.getMessage());
    }
}
