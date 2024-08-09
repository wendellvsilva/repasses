package com.example.projeto.repository;

import com.example.projeto.model.Repasse;
import com.example.projeto.model.enums.TipoRepasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepasseRepository extends JpaRepository<Repasse, Long> {
    List<Repasse> findByTipoRepasse(TipoRepasse tipoRepasse);
}
