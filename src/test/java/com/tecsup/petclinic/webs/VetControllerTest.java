package com.tecsup.petclinic.webs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
@Transactional
class VetControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired VetRepository repository;
    private final String body = "{\"firstName\": \"Luis\", \"lastName\": \"Maldonado\"}";
    private Integer createRecord() throws Exception {
        String json = mvc.perform(post("/vets").contentType("application/json").content(body))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.firstName").value("Luis"))
            .andReturn().getResponse().getContentAsString();
        return mapper.readTree(json).get("id").intValue();
    }
    @Test void listar() throws Exception {
        mvc.perform(get("/vets")).andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(java.util.List.class)));
    }
    @Test void buscarExistente() throws Exception {
        Integer id = createRecord();
        mvc.perform(get("/vets/" + id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Luis"));
    }
    @Test void buscarInexistente() throws Exception {
        mvc.perform(get("/vets/999999")).andExpect(status().isNotFound());
    }
    @Test void crearPersisteEnBaseDeDatos() throws Exception {
        Integer id = createRecord();
        assertTrue(repository.existsById(id));
        assertEquals("Luis", repository.findById(id).orElseThrow().getFirstName());
    }
    @Test void actualizarPersisteCambios() throws Exception {
        Integer id = createRecord();
        mvc.perform(put("/vets/" + id).contentType("application/json").content("{\"firstName\": \"Actualizado\", \"lastName\": \"Maldonado\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Actualizado"));
        mvc.perform(get("/vets/" + id)).andExpect(jsonPath("$.firstName").value("Actualizado"));
        assertEquals("Actualizado", repository.findById(id).orElseThrow().getFirstName());
    }
    @Test void actualizarInexistente() throws Exception {
        mvc.perform(put("/vets/999999").contentType("application/json").content(body)).andExpect(status().isNotFound());
    }
    @Test void eliminarPersisteEnBaseDeDatos() throws Exception {
        Integer id = createRecord();
        mvc.perform(delete("/vets/" + id)).andExpect(status().isNoContent());
        assertFalse(repository.existsById(id));
        mvc.perform(get("/vets/" + id)).andExpect(status().isNotFound());
    }
    @Test void eliminarInexistente() throws Exception {
        mvc.perform(delete("/vets/999999")).andExpect(status().isNotFound());
    }
    @Test void rechazarDatosIncompletos() throws Exception {
        long before = repository.count();
        mvc.perform(post("/vets").contentType("application/json").content("{}")).andExpect(status().isBadRequest());
        assertEquals(before, repository.count());
    }
}
