package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;
import java.util.Optional;
import vallegrande.edu.pe.AgroTecno.model.User;

public interface UserService {
    List<User> findAllActive();
    Optional<User> findById(Integer id);
    User save(User user);
    User update(User user);
    User deleteLogical(Integer id); // Retorna el usuario ya modificado (status = false)
}