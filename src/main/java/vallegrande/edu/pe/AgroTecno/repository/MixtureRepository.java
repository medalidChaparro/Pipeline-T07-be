package vallegrande.edu.pe.AgroTecno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vallegrande.edu.pe.AgroTecno.model.Mixture;

@Repository
public interface MixtureRepository extends JpaRepository<Mixture, Integer> {

}
