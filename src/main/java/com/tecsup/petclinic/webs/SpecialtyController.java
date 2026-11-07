package com.tecsup.petclinic.webs;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.services.SpecialtyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController
@RequestMapping("/specialties")
public class SpecialtyController {
    private final SpecialtyService service;
    public SpecialtyController(SpecialtyService service) { this.service = service; }
    @GetMapping public List<SpecialtyDTO> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public SpecialtyDTO findById(@PathVariable Integer id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public SpecialtyDTO create(@RequestBody SpecialtyDTO dto) { return service.create(dto); }
    @PutMapping("/{id}") public SpecialtyDTO update(@PathVariable Integer id, @RequestBody SpecialtyDTO dto) { return service.update(id, dto); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}
