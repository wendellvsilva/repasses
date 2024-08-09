package com.example.projeto.controller;

import com.example.projeto.dto.RepasseDTO;
import com.example.projeto.model.Repasse;
import com.example.projeto.model.enums.TipoRepasse;
import com.example.projeto.service.RepasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repasses")
@RequiredArgsConstructor
public class RepasseController {

    private final RepasseService repasseService;

    @PostMapping
    public ResponseEntity<Repasse> criarRepasse(@RequestBody @Validated RepasseDTO repasseDTO) {
        Repasse repasse = repasseService.salvarRepasse(repasseDTO);
        return new ResponseEntity<>(repasse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Repasse>> listarRepasses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Repasse> repasses = repasseService.listarRepasses(pageable);
        return ResponseEntity.ok(repasses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repasse> obterRepassePorId(@PathVariable Long id) {
        Repasse repasse = repasseService.obterRepassePorId(id);
        return ResponseEntity.ok(repasse);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Repasse>> filtrarRepassesPorTipo(@RequestParam TipoRepasse tipoRepasse) {
        List<Repasse> repasses = repasseService.filtrarRepassesPorTipo(tipoRepasse);
        return ResponseEntity.ok(repasses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repasse> atualizarRepasse(@PathVariable Long id, @RequestBody @Validated RepasseDTO repasseDTO) {
        Repasse repasseAtualizado = repasseService.atualizarRepasse(id, repasseDTO);
        return ResponseEntity.ok(repasseAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerRepasse(@PathVariable Long id) {
        repasseService.removerRepasse(id);
        return ResponseEntity.noContent().build();
    }
}
