package com.example.projeto.service;

import com.example.projeto.mappers.RepasseMapper;
import com.example.projeto.model.enums.SistemaOrigem;
import com.example.projeto.model.enums.TipoRepasse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.exception.RepasseNotFoundException;
import com.example.projeto.model.Repasse;
import com.example.projeto.repository.RepasseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RepasseService {

    private final RepasseRepository repasseRepository;
    private final RepasseMapper repasseMapper;

    public Repasse salvarRepasse(RepasseDTO repasseDTO) {
        validarValorRepasse(repasseDTO.valorRepasse());
        validarDataVencimento(repasseDTO.dataVencimento());
        validarSistemaOrigem(repasseDTO.sistemaOrigem());

        Repasse repasse = repasseMapper.fromDTO(repasseDTO);
        return repasseRepository.save(repasse);
    }


    public Page<Repasse> listarRepasses(Pageable pageable) {
        return repasseRepository.findAll(pageable);
    }

    public Repasse obterRepassePorId(Long id) {
        return repasseRepository.findById(id)
                .orElseThrow(() -> new RepasseNotFoundException(id));
    }

    public Repasse atualizarRepasse(Long id, RepasseDTO repasseDTO) {
        Repasse repasseExistente = obterRepassePorId(id);
        repasseExistente.setTipoRepasse(repasseDTO.tipoRepasse());
        repasseExistente.setValorRepasse(repasseDTO.valorRepasse());
        repasseExistente.setDataVencimento(repasseDTO.dataVencimento());
        repasseExistente.setFormaPagamento(repasseDTO.formaPagamento());
        repasseExistente.setSistemaOrigem(repasseDTO.sistemaOrigem());

        return repasseRepository.save(repasseExistente);
    }

    public void removerRepasse(Long id) {
        if (!repasseRepository.existsById(id)) {
            throw new RepasseNotFoundException(id);
        }
        repasseRepository.deleteById(id);
    }

    public List<Repasse> filtrarRepassesPorTipo(TipoRepasse tipoRepasse) {
        return repasseRepository.findByTipoRepasse(tipoRepasse);
    }
    private void validarValorRepasse(BigDecimal valorRepasse) {
        if (valorRepasse == null) {
            throw new IllegalArgumentException("Valor do repasse não pode ser nulo");
        }
    }

    private void validarDataVencimento(LocalDateTime dataVencimento) {
        if (dataVencimento.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data de vencimento deve ser no futuro");
        }
    }

    private void validarSistemaOrigem(SistemaOrigem sistemaOrigem) {
        if (sistemaOrigem == null) {
            throw new IllegalArgumentException("Sistema de origem não pode ser nulo");
        }
    }
}
