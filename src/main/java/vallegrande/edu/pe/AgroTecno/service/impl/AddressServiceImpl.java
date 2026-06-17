package vallegrande.edu.pe.AgroTecno.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import vallegrande.edu.pe.AgroTecno.model.Address;
import vallegrande.edu.pe.AgroTecno.repository.AddressRepository;
import vallegrande.edu.pe.AgroTecno.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Address findById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address update(Address address) {
        if (address.getAddressId() == null || !addressRepository.existsById(address.getAddressId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address no encontrado para actualizar");
        }
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!addressRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address no encontrado para eliminar");
        }
        addressRepository.deleteById(id);
    }
}
