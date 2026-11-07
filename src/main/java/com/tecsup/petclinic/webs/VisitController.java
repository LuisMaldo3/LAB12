package com.tecsup.petclinic.webs;
import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.services.VisitService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController
@RequestMapping("/visits")
public class VisitController {
    private final VisitService service;
    public VisitController(VisitService service) { this.service = service; }
    @GetMapping public List<VisitDTO> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public VisitDTO findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public VisitDTO create(@RequestBody VisitDTO dto) { return service.create(dto); }
    @PutMapping("/{id}") public VisitDTO update(@PathVariable Long id, @RequestBody VisitDTO dto) { return service.update(id, dto); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
