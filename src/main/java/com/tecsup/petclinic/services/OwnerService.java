package com.tecsup.petclinic.services;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service
@Transactional
public class OwnerService {
    private final OwnerRepository repository;
    
    public OwnerService(OwnerRepository repository) {
        this.repository = repository;
        
    }
    private OwnerDTO dto(Owner e) { return new OwnerDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getAddress(), e.getCity(), e.getTelephone()); }
    private Owner entity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public List<OwnerDTO> findAll() { return repository.findAll().stream().map(this::dto).toList(); }
    public OwnerDTO findById(Long id) { return dto(entity(id)); }
    private void apply(Owner e, OwnerDTO dto) {
        if (dto.firstName() == null || dto.firstName().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "firstName requerido");
        if (dto.lastName() == null || dto.lastName().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lastName requerido");
        if (dto.address() == null || dto.address().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "address requerido");
        if (dto.city() == null || dto.city().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "city requerido");
        if (dto.telephone() == null || dto.telephone().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "telephone requerido");
        e.setFirstName(dto.firstName());
        e.setLastName(dto.lastName());
        e.setAddress(dto.address());
        e.setCity(dto.city());
        e.setTelephone(dto.telephone());

    }
    public OwnerDTO create(OwnerDTO dto) {
        Owner e = new Owner(); apply(e, dto); return dto(repository.saveAndFlush(e));
    }
    public OwnerDTO update(Long id, OwnerDTO dto) {
        Owner e = entity(id); apply(e, dto); return dto(repository.saveAndFlush(e));
    }
    public void delete(Long id) { repository.delete(entity(id)); repository.flush(); }
}
