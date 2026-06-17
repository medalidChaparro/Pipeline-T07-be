package vallegrande.edu.pe.AgroTecno.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import vallegrande.edu.pe.AgroTecno.dto.MeetingRequest;
import vallegrande.edu.pe.AgroTecno.dto.MeetingResponse;
import vallegrande.edu.pe.AgroTecno.model.Client;
import vallegrande.edu.pe.AgroTecno.model.Meeting;
import vallegrande.edu.pe.AgroTecno.repository.ClientRepository;
import vallegrande.edu.pe.AgroTecno.repository.MeetingRepository;
import vallegrande.edu.pe.AgroTecno.service.MeetingService;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final ClientRepository clientRepository;

    public MeetingServiceImpl(MeetingRepository meetingRepository,
                            ClientRepository clientRepository) {
        this.meetingRepository = meetingRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingResponse> findAll() {
        return meetingRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MeetingResponse findById(Integer id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reunión no encontrada con ID: " + id));
        return toResponse(meeting);
    }

    @Override
    @Transactional
    public MeetingResponse save(MeetingRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente no encontrado con ID: " + request.getClientId()));

        Meeting meeting = new Meeting();
        meeting.setClient(client);
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setMeetingTime(request.getMeetingTime());
        meeting.setFruitQuality(request.getFruitQuality());
        meeting.setObservation(request.getObservation());
        meeting.setVisitStatus(resolveVisitStatus(request.getVisitStatus()));
        meeting.setProductionOrderId(request.getProductionOrderId());
        meeting.setFarmId(request.getFarmId());

        Meeting savedMeeting = meetingRepository.save(meeting);
        return toResponse(savedMeeting);
    }

    private String resolveVisitStatus(String requestedStatus) {
        String visitStatus = requestedStatus == null || requestedStatus.isBlank()
                ? "P"
                : requestedStatus.trim().toUpperCase();

        if (visitStatus.length() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "visitStatus debe tener un único carácter");
        }

        return visitStatus;
    }

    private MeetingResponse toResponse(Meeting meeting) {
        return new MeetingResponse(
                meeting.getMeetingId(),
                meeting.getClient().getClientId(),
                meeting.getClient().getName(),
                meeting.getClient().getLastName(),
                meeting.getMeetingDate(),
                meeting.getMeetingTime(),
                meeting.getFruitQuality(),
                meeting.getObservation(),
                meeting.getVisitStatus(),
                meeting.getProductionOrderId(),
                meeting.getFarmId());
    }
}
