package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;

import vallegrande.edu.pe.AgroTecno.dto.InstallmentRequest;
import vallegrande.edu.pe.AgroTecno.dto.InstallmentResponse;

public interface InstallmentService {

    List<InstallmentResponse> findAll();

    List<InstallmentResponse> findByCollectionId(Integer collectionId);

    InstallmentResponse findById(Integer id);

    InstallmentResponse save(InstallmentRequest request);

    InstallmentResponse update(Integer id, InstallmentRequest request);

    InstallmentResponse delete(Integer id);

    InstallmentResponse restore(Integer id);
}
