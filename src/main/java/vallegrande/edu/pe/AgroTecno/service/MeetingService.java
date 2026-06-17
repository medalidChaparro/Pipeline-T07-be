package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;

import vallegrande.edu.pe.AgroTecno.dto.MeetingRequest;
import vallegrande.edu.pe.AgroTecno.dto.MeetingResponse;

public interface MeetingService {
    
    List<MeetingResponse> findAll();
    
    MeetingResponse findById(Integer id);
    
    MeetingResponse save(MeetingRequest request);
}
