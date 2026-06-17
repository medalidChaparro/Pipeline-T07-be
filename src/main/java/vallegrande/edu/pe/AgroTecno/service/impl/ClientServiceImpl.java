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

import vallegrande.edu.pe.AgroTecno.model.Client;
import vallegrande.edu.pe.AgroTecno.repository.ClientRepository;
import vallegrande.edu.pe.AgroTecno.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
    public List<Client> findAll() {
        log.info("Listando Todos los Clientes");
        List<Client> clients = clientRepository.findAll();
        
        // Opcional: Convertir fechas a Lima para mostrar
        clients.forEach(client -> {
            log.info("Cliente: {} - Creado UTC: {}, Creado Lima: {}", 
                client.getName(), 
                client.getCreatedAt(),
                convertUTCToLima(client.getCreatedAt()));
        });
        
        return clients;
    }

    @Override
    public List<Client> findByEstado(Boolean estado) {
        log.info("Listando Clientes por Estado: " + estado);
        return clientRepository.findByEstado(estado);
    }

    @Override
    public Client findById(Integer id) {
        log.info("Listando Cliente por ID: " + id);
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public Client save(Client client) {
        LocalDateTime nowInLima = getCurrentLimaTime();
        LocalDateTime nowInUTC = convertLimaToUTC(nowInLima);
        
        log.info("Registrando Cliente - Hora Lima: " + nowInLima.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("Registrando Cliente - Hora UTC: " + nowInUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        // Guardar en UTC
        client.setCreatedAt(nowInUTC);
        client.setUpdatedAt(nowInUTC);
        client.setEstado(true);
        client.setDeletedAt(null);
        client.setRestoredAt(null);
        
        Client saved = clientRepository.save(client);
        log.info("Cliente guardado - Fecha UTC en BD: " + saved.getCreatedAt());
        
        return saved;
    }

    @Override
    public Client update(Client client) {
        log.info("Editando Cliente: " + client);

        Client existing = clientRepository.findById(client.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + client.getClientId()));

        // Preservar la fecha de creación original (ya está en UTC)
        client.setCreatedAt(existing.getCreatedAt());
        
        // Actualizar fecha en UTC
        LocalDateTime nowInUTC = convertLimaToUTC(getCurrentLimaTime());
        client.setUpdatedAt(nowInUTC);
        client.setEstado(true);
        
        // Preservar fechas de auditoría anteriores
        client.setDeletedAt(existing.getDeletedAt());
        client.setRestoredAt(existing.getRestoredAt());

        return clientRepository.save(client);
    }

    @Override
    public Client delete(Integer id) {
        log.info("Eliminando Cliente: " + id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        client.setEstado(false);
        client.setDeletedAt(convertLimaToUTC(getCurrentLimaTime()));
        client.setUpdatedAt(convertLimaToUTC(getCurrentLimaTime()));

        return clientRepository.save(client);
    }

    @Override
    public Client restore(Integer id) {
        log.info("Restaurando Cliente: " + id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        client.setEstado(true);
        client.setRestoredAt(convertLimaToUTC(getCurrentLimaTime()));
        client.setUpdatedAt(convertLimaToUTC(getCurrentLimaTime()));

        return clientRepository.save(client);
    }
}
