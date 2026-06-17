package vallegrande.edu.pe.AgroTecno.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vallegrande.edu.pe.AgroTecno.model.FarmPlot;
import vallegrande.edu.pe.AgroTecno.repository.FarmPlotRepository;
import vallegrande.edu.pe.AgroTecno.service.FarmPlotService;

@Service
public class FarmPlotServiceImpl implements FarmPlotService {

    private static final Logger log = LoggerFactory.getLogger(FarmPlotServiceImpl.class);
    private final FarmPlotRepository farmPlotRepository;

    @Autowired
    public FarmPlotServiceImpl(FarmPlotRepository farmPlotRepository) {
        this.farmPlotRepository = farmPlotRepository;
    }

    private LocalDateTime getCurrentLimaTime() {
        return LocalDateTime.now(ZoneId.of("America/Lima"));
    }
    
    private LocalDateTime convertLimaToUTC(LocalDateTime limaTime) {
        if (limaTime == null) return null;
        ZonedDateTime limaZoned = limaTime.atZone(ZoneId.of("America/Lima"));
        ZonedDateTime utcZoned = limaZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDateTime();
    }
    
    private LocalDateTime convertUTCToLima(LocalDateTime utcTime) {
        if (utcTime == null) return null;
        ZonedDateTime utcZoned = utcTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime limaZoned = utcZoned.withZoneSameInstant(ZoneId.of("America/Lima"));
        return limaZoned.toLocalDateTime();
    }

    @Override
    public List<FarmPlot> findAll() {
        log.info("Listando todos los FarmPlots");
        return farmPlotRepository.findAll();
    }

    @Override
    public Optional<FarmPlot> findById(Integer id) {
        log.info("Buscando FarmPlot por ID: " + id);
        return farmPlotRepository.findById(id);
    }

    @Override
    public List<FarmPlot> findByCustomerClientId(Integer clientId) {
        log.info("Listando FarmPlots por Client ID: " + clientId);
        return farmPlotRepository.findByCustomerClientId(clientId);
    }

    @Override
    public List<FarmPlot> findByEstado(Boolean estado) {
        log.info("Listando FarmPlots por Estado: " + estado);
        return farmPlotRepository.findByEstado(estado);
    }

    @Override
    public FarmPlot save(FarmPlot farmPlot) {
        LocalDateTime nowInLima = getCurrentLimaTime();
        LocalDateTime nowInUTC = convertLimaToUTC(nowInLima);
        
        log.info("Registrando FarmPlot - Hora Lima: " + nowInLima.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("Registrando FarmPlot - Hora UTC: " + nowInUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        // Guardar en UTC
        farmPlot.setCreatedAt(nowInUTC);
        farmPlot.setUpdatedAt(nowInUTC);
        farmPlot.setEstado(true);
        farmPlot.setDeletedAt(null);
        farmPlot.setRestoredAt(null);
        
        FarmPlot saved = farmPlotRepository.save(farmPlot);
        log.info("FarmPlot guardado - Fecha UTC en BD: " + saved.getCreatedAt());
        
        return saved;
    }

    @Override
    public FarmPlot update(FarmPlot farmPlot) {
        log.info("Editando FarmPlot: " + farmPlot.getFarmId());

        FarmPlot existing = farmPlotRepository.findById(farmPlot.getFarmId())
                .orElseThrow(() -> new RuntimeException("FarmPlot no encontrado con ID: " + farmPlot.getFarmId()));

        // Preservar la fecha de creación original (ya está en UTC)
        farmPlot.setCreatedAt(existing.getCreatedAt());
        
        // Actualizar fecha en UTC
        LocalDateTime nowInUTC = convertLimaToUTC(getCurrentLimaTime());
        farmPlot.setUpdatedAt(nowInUTC);
        farmPlot.setEstado(true);
        
        // Preservar fechas de auditoría anteriores
        farmPlot.setDeletedAt(existing.getDeletedAt());
        farmPlot.setRestoredAt(existing.getRestoredAt());

        return farmPlotRepository.save(farmPlot);
    }

    @Override
    public FarmPlot delete(Integer id) {
        log.info("Eliminando FarmPlot: " + id);

        FarmPlot farmPlot = farmPlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FarmPlot no encontrado con ID: " + id));

        farmPlot.setEstado(false);
        farmPlot.setDeletedAt(convertLimaToUTC(getCurrentLimaTime()));
        farmPlot.setUpdatedAt(convertLimaToUTC(getCurrentLimaTime()));

        return farmPlotRepository.save(farmPlot);
    }

    @Override
    public FarmPlot restore(Integer id) {
        log.info("Restaurando FarmPlot: " + id);

        FarmPlot farmPlot = farmPlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FarmPlot no encontrado con ID: " + id));

        farmPlot.setEstado(true);
        farmPlot.setRestoredAt(convertLimaToUTC(getCurrentLimaTime()));
        farmPlot.setUpdatedAt(convertLimaToUTC(getCurrentLimaTime()));

        return farmPlotRepository.save(farmPlot);
    }

}
