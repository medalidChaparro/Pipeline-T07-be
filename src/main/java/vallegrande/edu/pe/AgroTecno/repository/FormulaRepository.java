package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vallegrande.edu.pe.AgroTecno.model.Formula;

public interface FormulaRepository extends JpaRepository<Formula, Integer> {
    List<Formula> findByEstado(Boolean estado);
}