package vallegrande.edu.pe.AgroTecno.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vallegrande.edu.pe.AgroTecno.model.Formula;
import vallegrande.edu.pe.AgroTecno.repository.FormulaRepository;
import vallegrande.edu.pe.AgroTecno.service.FormulaService;

@Service
public class FormulaServiceImpl implements FormulaService {

    private static final Logger log = LoggerFactory.getLogger(FormulaServiceImpl.class);
    private final FormulaRepository formulaRepository;

    @Autowired
    public FormulaServiceImpl(FormulaRepository formulaRepository) {
        this.formulaRepository = formulaRepository;
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
    public List<Formula> findAll() {
        log.info("Listando Todas las Fórmulas");
        List<Formula> formulas = formulaRepository.findAll();
        
        formulas.forEach(formula -> {
            log.info("Fórmula: {} - Creada UTC: {}, Creada Lima: {}", 
                formula.getName(), 
                formula.getCreatedAt(),
                convertUTCToLima(formula.getCreatedAt()));
        });
        
        return formulas;
    }

    @Override
    public List<Formula> findByEstado(Boolean estado) {
        log.info("Listando Fórmulas por Estado: " + estado);
        return formulaRepository.findByEstado(estado);
    }

    @Override
    public Formula findById(Integer id) {
        log.info("Listando Fórmula por ID: " + id);
        return formulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fórmula no encontrada con ID: " + id));
    }

    // 1. CREAR: Solo registra createdAt, los demás van nulos.
    @Override
    public Formula save(Formula formula) {
        LocalDateTime nowInLima = getCurrentLimaTime();
        LocalDateTime nowInUTC = convertLimaToUTC(nowInLima);
        
        log.info("Registrando Fórmula - Hora Lima: " + nowInLima.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("Registrando Fórmula - Hora UTC: " + nowInUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        formula.setCreatedAt(nowInUTC); // Solo se asigna la creación
        formula.setEstado(true);
        
        // Garantizamos que los demás campos de auditoría inicien limpios
        formula.setUpdatedAt(null);
        formula.setDeletedAt(null);
        formula.setRestoredAt(null);
        
        Formula saved = formulaRepository.save(formula);
        log.info("Fórmula guardada - Fecha UTC en BD: " + saved.getCreatedAt());
        
        return saved;
    }

    // 2. ACTUALIZAR: Mantiene el createdAt intacto y solo pisa el updatedAt.
    @Override
    public Formula update(Formula formula) {
        log.info("Editando Fórmula: " + formula);

        Formula existing = formulaRepository.findById(formula.getFormulaId())
                .orElseThrow(() -> new RuntimeException("Fórmula no encontrada con ID: " + formula.getFormulaId()));

        // Preservar la fecha de creación original e ignorar intentos de alterarla
        formula.setCreatedAt(existing.getCreatedAt());
        
        // Solo actualizamos la fecha de modificación
        LocalDateTime nowInUTC = convertLimaToUTC(getCurrentLimaTime());
        formula.setUpdatedAt(nowInUTC);
        
        // Mantenemos el estado actual del registro (o forzamos true si solo se actualizan activas)
        formula.setEstado(existing.getEstado());
        
        // Preservar las fechas pasadas de borrado o restauración sin alterarlas aquí
        formula.setDeletedAt(existing.getDeletedAt());
        formula.setRestoredAt(existing.getRestoredAt());

        return formulaRepository.save(formula);
    }

    // 3. ELIMINAR LÓGICO: Pasa de true a false y asigna ÚNICAMENTE deletedAt.
    @Override
    public Formula delete(Integer id) {
        log.info("Eliminando de forma lógica la Fórmula: " + id);

        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fórmula no encontrada con ID: " + id));

        formula.setEstado(false); // Cambia el estado a inactivo
        formula.setDeletedAt(convertLimaToUTC(getCurrentLimaTime())); // ÚNICA fecha que se mueve

        return formulaRepository.save(formula);
    }

    // 4. RESTAURAR: Pasa de false a true y asigna ÚNICAMENTE restoredAt.
    @Override
    public Formula restore(Integer id) {
        log.info("Restaurando Fórmula: " + id);

        Formula formula = formulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fórmula no encontrada con ID: " + id));

        formula.setEstado(true); // Regresa a estar activo
        formula.setRestoredAt(convertLimaToUTC(getCurrentLimaTime())); // ÚNICA fecha que se mueve

        return formulaRepository.save(formula);
    }
}