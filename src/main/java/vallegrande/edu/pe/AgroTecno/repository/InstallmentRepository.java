package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vallegrande.edu.pe.AgroTecno.model.Installment;

public interface InstallmentRepository extends JpaRepository<Installment, Integer> {

    List<Installment> findByCollectionCollectionId(Integer collectionId);
}
