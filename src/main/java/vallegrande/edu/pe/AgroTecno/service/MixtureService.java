package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;

import vallegrande.edu.pe.AgroTecno.dto.MixtureRequest;
import vallegrande.edu.pe.AgroTecno.dto.MixtureResponse;

public interface MixtureService {

    List<MixtureResponse> findAll();

    MixtureResponse findById(Integer id);

    MixtureResponse save(MixtureRequest request);
}
