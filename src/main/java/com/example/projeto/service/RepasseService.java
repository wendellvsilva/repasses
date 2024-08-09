package com.example.projeto.service;

import com.example.projeto.domain.RepasseSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.exception.RepasseNotFoundException;
import com.example.projeto.model.Repasse;
import com.example.projeto.model.enums.SistemaOrigem;
import com.example.projeto.model.enums.TipoRepasse;
import com.example.projeto.repository.RepasseRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RepasseService {

    private final RepasseRepository repasseRepository;

    public Repasse salvarRepasse(RepasseDTO repasseDTO) {
        Repasse repasse = Repasse.fromDTO(repasseDTO);
        return repasseRepository.save(repasse);
    }

    public Page<Repasse> listarRepasses(Long id, TipoRepasse tipoRepasse, SistemaOrigem sistemaOrigem, Pageable pageable) {
        Specification<Repasse> spec = Specification.where(null);

        if (id != null) {
            spec = spec.and(RepasseSpecifications.hasId(id));
        }
        if (tipoRepasse != null) {
            spec = spec.and(RepasseSpecifications.hasTipoRepasse(tipoRepasse));
        }
        if (sistemaOrigem != null) {
            spec = spec.and(RepasseSpecifications.hasSistemaOrigem(sistemaOrigem));
        }

        return repasseRepository.findAll(spec, pageable);
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
}