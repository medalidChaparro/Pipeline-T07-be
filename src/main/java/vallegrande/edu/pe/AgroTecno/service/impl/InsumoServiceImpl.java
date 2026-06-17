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

import vallegrande.edu.pe.AgroTecno.model.Insumo;
import vallegrande.edu.pe.AgroTecno.repository.InsumoRepository;
import vallegrande.edu.pe.AgroTecno.service.InsumoService;

@Service
public class InsumoServiceImpl implements InsumoService {

    private static final Logger log = LoggerFactory.getLogger(InsumoServiceImpl.class);
    private final InsumoRepository insumoRepository;

    @Autowired
    public InsumoServiceImpl(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
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
    public List<Insumo> findAll() {
        log.info("Listando Todos los Insumos");
        List<Insumo> insumos = insumoRepository.findAll();
        
        // Opcional: Convertir fechas a Lima para mostrar
        insumos.forEach(insumo -> {
            log.info("Insumo: {} - Creado UTC: {}, Creado Lima: {}", 
                insumo.getNombre(), 
                insumo.getCreatedAt(),
                convertUTCToLima(insumo.getCreatedAt()));
        });
        
        return insumos;
    }

    @Override
    public List<Insumo> findByEstado(Boolean estado) {
        log.info("Listando Insumos por Estado: " + estado);
        return insumoRepository.findByEstado(estado);
    }

    @Override
    public Optional<Insumo> findById(Integer id) {
        log.info("Listando Insumo por ID: " + id);
        return insumoRepository.findById(id);
    }

    @Override
    public Insumo save(Insumo insumo) {
        LocalDateTime nowInLima = getCurrentLimaTime();
        LocalDateTime nowInUTC = convertLimaToUTC(nowInLima);
        
        log.info("Registrando Insumo - Hora Lima: " + nowInLima.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("Registrando Insumo - Hora UTC: " + nowInUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        // Guardar en UTC
        insumo.setCreatedAt(nowInUTC);
        insumo.setUpdatedAt(nowInUTC);
        insumo.setEstado(true);
        insumo.setDeletedAt(null);
        insumo.setRestoredAt(null);
        
        Insumo saved = insumoRepository.save(insumo);
        log.info("Insumo guardado - Fecha UTC en BD: " + saved.getCreatedAt());
        
        return saved;
    }

    @Override
    public Insumo update(Insumo insumo) {
        log.info("Editando Insumo: " + insumo);

        Insumo existing = insumoRepository.findById(insumo.getIdInsumo())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + insumo.getIdInsumo()));

        // Preservar la fecha de creación original (ya está en UTC)
        insumo.setCreatedAt(existing.getCreatedAt());
        
        // Actualizar fecha en UTC
        LocalDateTime nowInUTC = convertLimaToUTC(getCurrentLimaTime());
        insumo.setUpdatedAt(nowInUTC);
        insumo.setEstado(true);
        
        // Preservar fechas de auditoría anteriores
        insumo.setDeletedAt(existing.getDeletedAt());
        insumo.setRestoredAt(existing.getRestoredAt());

        return insumoRepository.save(insumo);
    }

    @Override
    public Insumo delete(Integer id) {
        log.info("Eliminando Insumo: " + id);

        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + id));

        insumo.setEstado(false);
        insumo.setDeletedAt(convertLimaToUTC(getCurrentLimaTime()));
        insumo.setUpdatedAt(convertLimaToUTC(getCurrentLimaTime()));

        return insumoRepository.save(insumo);
    }

    @Override
    public Insumo restore(Integer id) {
        log.info("Restaurando Insumo: " + id);

        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + id));

        insumo.setEstado(true);
        insumo.setRestoredAt(convertLimaToUTC(getCurrentLimaTime()));
        insumo.setUpdatedAt(convertLimaToUTC(getCurrentLimaTime()));

        return insumoRepository.save(insumo);
    }
}