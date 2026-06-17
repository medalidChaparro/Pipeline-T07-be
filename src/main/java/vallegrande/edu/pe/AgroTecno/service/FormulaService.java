package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;

import vallegrande.edu.pe.AgroTecno.model.Formula;

public interface FormulaService {
    List<Formula> findAll();
    List<Formula> findByEstado(Boolean estado);
    Formula findById(Integer id);
    Formula save(Formula formula);
    Formula update(Formula formula);
    Formula delete(Integer id);                // Nuevo - eliminar con retorno
    Formula restore(Integer id);               // Nuevo - restaurar con retorno
}