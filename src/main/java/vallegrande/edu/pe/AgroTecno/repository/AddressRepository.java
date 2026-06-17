package vallegrande.edu.pe.AgroTecno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vallegrande.edu.pe.AgroTecno.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
