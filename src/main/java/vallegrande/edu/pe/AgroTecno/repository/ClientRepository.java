package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vallegrande.edu.pe.AgroTecno.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findByEstado(Boolean estado);
}