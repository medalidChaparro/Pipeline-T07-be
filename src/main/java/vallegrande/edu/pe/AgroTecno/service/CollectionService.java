package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;

import vallegrande.edu.pe.AgroTecno.dto.CollectionRequest;
import vallegrande.edu.pe.AgroTecno.dto.CollectionResponse;

public interface CollectionService {

    List<CollectionResponse> findAll();

    CollectionResponse findById(Integer id);

    CollectionResponse save(CollectionRequest request);
}
