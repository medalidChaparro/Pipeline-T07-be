package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;

import vallegrande.edu.pe.AgroTecno.model.Address;

public interface AddressService {

    List<Address> findAll();

    Address findById(Integer id);

    Address save(Address address);

    Address update(Address address);

    void delete(Integer id);
}
