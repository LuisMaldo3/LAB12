package com.tecsup.petclinic.services;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service
@Transactional
public class VisitService {
    private final VisitRepository repository;
    private final PetRepository pets;
    public VisitService(VisitRepository repository, PetRepository pets) {
        this.repository = repository;
        this.pets = pets;
    }
    private VisitDTO dto(Visit e) { return new VisitDTO(e.getId(), e.getVisitDate(), e.getDescription(), e.getPet().getId()); }
    private Visit entity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public List<VisitDTO> findAll() { return repository.findAll().stream().map(this::dto).toList(); }
    public VisitDTO findById(Long id) { return dto(entity(id)); }
    private void apply(Visit e, VisitDTO dto) {
        if (dto.visitDate() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "visitDate requerido");
        if (dto.description() == null || dto.description().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description requerido");
        if (dto.petId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "petId requerido");
        e.setVisitDate(dto.visitDate());
        e.setDescription(dto.description());
        e.setPet(pets.findById(dto.petId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada")));
    }
    public VisitDTO create(VisitDTO dto) {
        Visit e = new Visit(); apply(e, dto); return dto(repository.saveAndFlush(e));
    }
    public VisitDTO update(Long id, VisitDTO dto) {
        Visit e = entity(id); apply(e, dto); return dto(repository.saveAndFlush(e));
    }
    public void delete(Long id) { repository.delete(entity(id)); repository.flush(); }
}
