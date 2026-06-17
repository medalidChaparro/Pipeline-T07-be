package vallegrande.edu.pe.AgroTecno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vallegrande.edu.pe.AgroTecno.model.MixtureDetail;

@Repository
public interface MixtureDetailRepository extends JpaRepository<MixtureDetail, Integer> {

}
