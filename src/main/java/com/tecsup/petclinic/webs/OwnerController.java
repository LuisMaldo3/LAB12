package com.tecsup.petclinic.webs;
import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.services.OwnerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService service;
    public OwnerController(OwnerService service) { this.service = service; }
    @GetMapping public List<OwnerDTO> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public OwnerDTO findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public OwnerDTO create(@RequestBody OwnerDTO dto) { return service.create(dto); }
    @PutMapping("/{id}") public OwnerDTO update(@PathVariable Long id, @RequestBody OwnerDTO dto) { return service.update(id, dto); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
