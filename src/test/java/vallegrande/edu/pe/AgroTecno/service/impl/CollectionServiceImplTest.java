package vallegrande.edu.pe.AgroTecno.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import vallegrande.edu.pe.AgroTecno.dto.CollectionDetailRequest;
import vallegrande.edu.pe.AgroTecno.dto.CollectionRequest;
import vallegrande.edu.pe.AgroTecno.dto.CollectionResponse;
import vallegrande.edu.pe.AgroTecno.model.Collection;
import vallegrande.edu.pe.AgroTecno.model.CollectionDetail;
import vallegrande.edu.pe.AgroTecno.repository.CollectionDetailRepository;
import vallegrande.edu.pe.AgroTecno.repository.CollectionRepository;

@ExtendWith(MockitoExtension.class)
class CollectionServiceImplTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private CollectionDetailRepository collectionDetailRepository;

    @InjectMocks
    private CollectionServiceImpl collectionService;

    @Test
    void save_shouldApplyDefaultsAndPersistDetails() {
        CollectionRequest request = new CollectionRequest();
        request.setOrderOrderId(7);

        CollectionDetailRequest detailRequest = new CollectionDetailRequest();
        detailRequest.setLineNumber(1);
        detailRequest.setDescription("Semilla maíz");
        detailRequest.setQuantity(new BigDecimal("2"));
        detailRequest.setUnitPrice(new BigDecimal("75.25"));
        request.setDetails(List.of(detailRequest));

        when(collectionRepository.save(any(Collection.class))).thenAnswer(invocation -> {
            Collection saved = invocation.getArgument(0);
            saved.setCollectionId(1);
            return saved;
        });

        when(collectionDetailRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CollectionResponse response = collectionService.save(request);

        assertEquals(1, response.getCollectionId());
        assertEquals(0, response.getTotalAmount().compareTo(new BigDecimal("150.50000000")));
        assertEquals(1, response.getInstallmentsCount());
        assertEquals("P", response.getPaymentStatus());
        assertNotNull(response.getRegistrationDate());
        assertEquals(7, response.getOrderOrderId());
        assertEquals(1, response.getDetails().size());
        assertEquals("Semilla maíz", response.getDetails().get(0).getDescription());
        assertEquals(0, response.getDetails().get(0).getSubtotal().compareTo(new BigDecimal("150.50000000")));
        verify(collectionRepository).save(any(Collection.class));
        verify(collectionDetailRepository).saveAll(any());
    }
}
