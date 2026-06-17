package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;
import java.util.Optional;

import vallegrande.edu.pe.AgroTecno.model.FarmPlot;

public interface FarmPlotService {

    List<FarmPlot> findAll();

    Optional<FarmPlot> findById(Integer id);

    List<FarmPlot> findByCustomerClientId(Integer clientId);

    List<FarmPlot> findByEstado(Boolean estado);

    FarmPlot save(FarmPlot farmPlot);

    FarmPlot update(FarmPlot farmPlot);

    FarmPlot delete(Integer id);

    FarmPlot restore(Integer id);

}
