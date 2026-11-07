package com.tecsup.petclinic.services;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service
@Transactional
public class VetService {
    private final VetRepository repository;
    
    public VetService(VetRepository repository) {
        this.repository = repository;
        
    }
    private VetDTO dto(Vet e) { return new VetDTO(e.getId(), e.getFirstName(), e.getLastName()); }
    private Vet entity(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public List<VetDTO> findAll() { return repository.findAll().stream().map(this::dto).toList(); }
    public VetDTO findById(Integer id) { return dto(entity(id)); }
    private void apply(Vet e, VetDTO dto) {
        if (dto.firstName() == null || dto.firstName().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "firstName requerido");
        if (dto.lastName() == null || dto.lastName().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lastName requerido");
        e.setFirstName(dto.firstName());
        e.setLastName(dto.lastName());

    }
    public VetDTO create(VetDTO dto) {
        Vet e = new Vet(); apply(e, dto); return dto(repository.saveAndFlush(e));
    }
    public VetDTO update(Integer id, VetDTO dto) {
        Vet e = entity(id); apply(e, dto); return dto(repository.saveAndFlush(e));
    }
    public void delete(Integer id) { repository.delete(entity(id)); repository.flush(); }
}
