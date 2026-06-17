package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vallegrande.edu.pe.AgroTecno.model.FarmPlot;

@Repository
public interface FarmPlotRepository extends JpaRepository<FarmPlot, Integer> {
    
    List<FarmPlot> findByCustomerClientId(Integer clientId);

    List<FarmPlot> findByEstado(Boolean estado);
    
}
