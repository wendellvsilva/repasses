package com.example.projeto.controller;

import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.model.Repasse;
import com.example.projeto.model.enums.TipoRepasse;
import com.example.projeto.repository.RepasseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.projeto.model.enums.FormaPagamento.TRANSFERENCIA_BANCARIA;
import static com.example.projeto.model.enums.SistemaOrigem.ECOM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RepasseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepasseRepository repasseRepository;

    private RepasseDTO repasseDTO;

    @BeforeEach
    void setUp() {
        repasseDTO = new RepasseDTO(TipoRepasse.SELLER, new BigDecimal("1000.00"), LocalDateTime.now().plusDays(1),
                TRANSFERENCIA_BANCARIA, ECOM);
    }

    @Test
    @DisplayName("Criar repasse com sucesso")
    void criarRepasse_sucesso() throws Exception {
        mockMvc.perform(post("/api/repasses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"tipoRepasse\": \"SELLER\",\n" +
                                "  \"valorRepasse\": 1000.00,\n" +
                                "  \"dataVencimento\": \"2024-09-01T00:00:00\",\n" +
                                "  \"formaPagamento\": \"TRANSFERENCIA_BANCARIA\",\n" +
                                "  \"sistemaOrigem\": \"ECOM\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valorRepasse").value(1000.00));
    }

    @Test
    @DisplayName("Listar repasses com sucesso")
    void listarRepasses_sucesso() throws Exception {
        Repasse repasse = Repasse.builder()
                .tipoRepasse(repasseDTO.tipoRepasse())
                .valorRepasse(repasseDTO.valorRepasse())
                .dataVencimento(repasseDTO.dataVencimento())
                .formaPagamento(repasseDTO.formaPagamento())
                .sistemaOrigem(repasseDTO.sistemaOrigem())
                .dataCriacao(LocalDateTime.now())
                .build();

        repasseRepository.save(repasse);

        mockMvc.perform(get("/api/repasses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].valorRepasse").value(1000.00));
    }

    @Test
    @DisplayName("Obter repasse por ID com sucesso")
    void obterRepassePorId_sucesso() throws Exception {
        Repasse repasse = Repasse.builder()
                .tipoRepasse(repasseDTO.tipoRepasse())
                .valorRepasse(repasseDTO.valorRepasse())
                .dataVencimento(repasseDTO.dataVencimento())
                .formaPagamento(repasseDTO.formaPagamento())
                .sistemaOrigem(repasseDTO.sistemaOrigem())
                .dataCriacao(LocalDateTime.now())
                .build();

        repasseRepository.save(repasse);

        mockMvc.perform(get("/api/repasses/{id}", repasse.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valorRepasse").value(1000.00));
    }

    @Test
    @DisplayName("Atualizar repasse com sucesso")
    void atualizarRepasse_sucesso() throws Exception {
        Repasse repasse = Repasse.builder()
                .tipoRepasse(repasseDTO.tipoRepasse())
                .valorRepasse(repasseDTO.valorRepasse())
                .dataVencimento(repasseDTO.dataVencimento())
                .formaPagamento(repasseDTO.formaPagamento())
                .sistemaOrigem(repasseDTO.sistemaOrigem())
                .dataCriacao(LocalDateTime.now())
                .build();

        repasseRepository.save(repasse);

        mockMvc.perform(put("/api/repasses/{id}", repasse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"tipoRepasse\": \"SELLER\",\n" +
                                "  \"valorRepasse\": 2000.00,\n" +
                                "  \"dataVencimento\": \"2024-09-01T00:00:00\",\n" +
                                "  \"formaPagamento\": \"TRANSFERENCIA_BANCARIA\",\n" +
                                "  \"sistemaOrigem\": \"ECOM\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valorRepasse").value(2000.00));
    }

    @Test
    @DisplayName("Remover repasse com sucesso")
    void removerRepasse_sucesso() throws Exception {
        Repasse repasse = Repasse.builder()
                .tipoRepasse(repasseDTO.tipoRepasse())
                .valorRepasse(repasseDTO.valorRepasse())
                .dataVencimento(repasseDTO.dataVencimento())
                .formaPagamento(repasseDTO.formaPagamento())
                .sistemaOrigem(repasseDTO.sistemaOrigem())
                .dataCriacao(LocalDateTime.now())
                .build();

        repasseRepository.save(repasse);

        mockMvc.perform(delete("/api/repasses/{id}", repasse.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Filtrar repasses por tipo com sucesso")
    void filtrarRepassesPorTipo_sucesso() throws Exception {
        Repasse repasse = Repasse.builder()
                .tipoRepasse(repasseDTO.tipoRepasse())
                .valorRepasse(repasseDTO.valorRepasse())
                .dataVencimento(repasseDTO.dataVencimento())
                .formaPagamento(repasseDTO.formaPagamento())
                .sistemaOrigem(repasseDTO.sistemaOrigem())
                .dataCriacao(LocalDateTime.now())
                .build();

        repasseRepository.save(repasse);

        mockMvc.perform(get("/api/repasses/filtrar")
                        .param("tipoRepasse", "SELLER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoRepasse").value("SELLER"));
    }
}
