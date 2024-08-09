package com.example.projeto.domain;

import org.springframework.data.jpa.domain.Specification;
import com.example.projeto.model.Repasse;
import com.example.projeto.model.enums.TipoRepasse;
import com.example.projeto.model.enums.SistemaOrigem;

public class RepasseSpecifications {

    public static Specification<Repasse> hasId(Long id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }

    public static Specification<Repasse> hasTipoRepasse(TipoRepasse tipoRepasse) {
        return (root, query, builder) -> builder.equal(root.get("tipoRepasse"), tipoRepasse);
    }

    public static Specification<Repasse> hasSistemaOrigem(SistemaOrigem sistemaOrigem) {
        return (root, query, builder) -> builder.equal(root.get("sistemaOrigem"), sistemaOrigem);
    }
}
