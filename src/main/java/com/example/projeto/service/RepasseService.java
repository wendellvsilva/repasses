package com.example.projeto.service;

import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.model.Repasse;
import com.example.projeto.model.enums.TipoRepasse;
import com.example.projeto.repository.RepasseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<Repasse> listarRepasses() {
        return repasseRepository.findAll();
    }

    public Repasse obterRepassePorId(Long id) {
        return repasseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repasse não encontrado"));
    }

    public Repasse atualizarRepasse(Long id, RepasseDTO repasseDTO) {
        Repasse repasseExistente = obterRepassePorId(id);
        repasseExistente.setTipoRepasse(repasseDTO.getTipoRepasse());
        repasseExistente.setValorRepasse(repasseDTO.getValorRepasse());
        repasseExistente.setDataVencimento(repasseDTO.getDataVencimento());
        repasseExistente.setFormaPagamento(repasseDTO.getFormaPagamento());
        repasseExistente.setSistemaOrigem(repasseDTO.getSistemaOrigem());

        return repasseRepository.save(repasseExistente);
    }

    public void removerRepasse(Long id) {
        repasseRepository.deleteById(id);
    }

    public List<Repasse> filtrarRepassesPorTipo(TipoRepasse tipoRepasse) {
        return repasseRepository.findByTipoRepasse(tipoRepasse);
    }
}