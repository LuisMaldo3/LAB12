package com.tecsup.petclinic.webs;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.services.VetService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController
@RequestMapping("/vets")
public class VetController {
    private final VetService service;
    public VetController(VetService service) { this.service = service; }
    @GetMapping public List<VetDTO> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public VetDTO findById(@PathVariable Integer id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public VetDTO create(@RequestBody VetDTO dto) { return service.create(dto); }
    @PutMapping("/{id}") public VetDTO update(@PathVariable Integer id, @RequestBody VetDTO dto) { return service.update(id, dto); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}
