package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.AgroTecno.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Filtra solo los usuarios activos (status = true)
    List<User> findByStatusTrue();
    
    // Busca por username y que esté activo (esencial para tu login posterior)
    Optional<User> findByUsernameAndStatusTrue(String username);
}