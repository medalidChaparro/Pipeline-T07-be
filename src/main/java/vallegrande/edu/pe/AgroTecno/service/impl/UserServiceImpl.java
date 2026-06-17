package vallegrande.edu.pe.AgroTecno.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vallegrande.edu.pe.AgroTecno.model.User;
import vallegrande.edu.pe.AgroTecno.repository.UserRepository;
import vallegrande.edu.pe.AgroTecno.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllActive() {
        log.info("Listando todos los usuarios activos (status = true)");
        return userRepository.findByStatusTrue();
    }

    @Override
    public Optional<User> findById(Integer id) {
        log.info("Buscando usuario por ID: {}", id);
        return userRepository.findById(id).filter(User::getStatus);
    }

    @Override
    public User save(User user) {
        log.info("Registrando nuevo usuario: {}", user.getUsername());
        user.setStatus(true); // Se asegura de que se cree como activo por defecto
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        log.info("Actualizando datos del usuario ID: {}", user.getUserId());
        return userRepository.save(user);
    }

    @Override
    public User deleteLogical(Integer id) {
        log.info("Deshabilitando lógicamente (PATCH) al usuario ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        user.setStatus(false); // Eliminado lógico
        return userRepository.save(user);
    }
}