package com.example.projeto.service;

import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.exception.RepasseNotFoundException;
import com.example.projeto.mappers.RepasseMapper;
import com.example.projeto.model.Repasse;
import com.example.projeto.model.enums.TipoRepasse;
import com.example.projeto.repository.RepasseRepository;
import org.junit.jupiter.api.BeforeEach;
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
    }

    @Test
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
    }

    @Test
    void obterRepassePorId_naoEncontrado() {
        when(repasseRepository.findById(1L)).thenReturn(Optional.empty());

        RepasseNotFoundException exception = assertThrows(RepasseNotFoundException.class, () -> {
            repasseService.obterRepassePorId(1L);
        });

        assertEquals("Repasse com ID 1 não encontrado", exception.getMessage());
    }

    @Test
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
    }

    @Test
    void removerRepasse_sucesso() {
        when(repasseRepository.existsById(1L)).thenReturn(true);
        doNothing().when(repasseRepository).deleteById(1L);
        assertDoesNotThrow(() -> repasseService.removerRepasse(1L));
        verify(repasseRepository, times(1)).deleteById(1L);
    }
}
