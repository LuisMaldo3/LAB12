package com.tecsup.petclinic.services;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service
@Transactional
public class SpecialtyService {
    private final SpecialtyRepository repository;
    
    public SpecialtyService(SpecialtyRepository repository) {
        this.repository = repository;
        
    }
    private SpecialtyDTO dto(Specialty e) { return new SpecialtyDTO(e.getId(), e.getName()); }
    private Specialty entity(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public List<SpecialtyDTO> findAll() { return repository.findAll().stream().map(this::dto).toList(); }
    public SpecialtyDTO findById(Integer id) { return dto(entity(id)); }
    private void apply(Specialty e, SpecialtyDTO dto) {
        if (dto.name() == null || dto.name().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name requerido");
        e.setName(dto.name());

    }
    public SpecialtyDTO create(SpecialtyDTO dto) {
        Specialty e = new Specialty(); apply(e, dto); return dto(repository.saveAndFlush(e));
    }
    public SpecialtyDTO update(Integer id, SpecialtyDTO dto) {
        Specialty e = entity(id); apply(e, dto); return dto(repository.saveAndFlush(e));
    }
    public void delete(Integer id) { repository.delete(entity(id)); repository.flush(); }
}
