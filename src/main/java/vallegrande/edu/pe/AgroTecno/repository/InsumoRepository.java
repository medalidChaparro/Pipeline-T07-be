package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vallegrande.edu.pe.AgroTecno.model.Insumo;

public interface InsumoRepository extends JpaRepository<Insumo, Integer> {

    // Método para listar por estado
    List<Insumo> findByEstado(Boolean estado);

}