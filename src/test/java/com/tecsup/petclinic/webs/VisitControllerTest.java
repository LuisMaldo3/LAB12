package com.tecsup.petclinic.webs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
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
class VisitControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired VisitRepository repository;
    private final String body = "{\"visitDate\": \"2026-11-06\", \"description\": \"Control general\", \"petId\": 1}";
    private Long createRecord() throws Exception {
        String json = mvc.perform(post("/visits").contentType("application/json").content(body))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.description").value("Control general"))
            .andReturn().getResponse().getContentAsString();
        return mapper.readTree(json).get("id").longValue();
    }
    @Test void listar() throws Exception {
        mvc.perform(get("/visits")).andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(java.util.List.class)));
    }
    @Test void buscarExistente() throws Exception {
        Long id = createRecord();
        mvc.perform(get("/visits/" + id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Control general"));
    }
    @Test void buscarInexistente() throws Exception {
        mvc.perform(get("/visits/999999")).andExpect(status().isNotFound());
    }
    @Test void crearPersisteEnBaseDeDatos() throws Exception {
        Long id = createRecord();
        assertTrue(repository.existsById(id));
        assertEquals("Control general", repository.findById(id).orElseThrow().getDescription());
    }
    @Test void actualizarPersisteCambios() throws Exception {
        Long id = createRecord();
        mvc.perform(put("/visits/" + id).contentType("application/json").content("{\"visitDate\": \"2026-11-06\", \"description\": \"Actualizado\", \"petId\": 1}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.description").value("Actualizado"));
        mvc.perform(get("/visits/" + id)).andExpect(jsonPath("$.description").value("Actualizado"));
        assertEquals("Actualizado", repository.findById(id).orElseThrow().getDescription());
    }
    @Test void actualizarInexistente() throws Exception {
        mvc.perform(put("/visits/999999").contentType("application/json").content(body)).andExpect(status().isNotFound());
    }
    @Test void eliminarPersisteEnBaseDeDatos() throws Exception {
        Long id = createRecord();
        mvc.perform(delete("/visits/" + id)).andExpect(status().isNoContent());
        assertFalse(repository.existsById(id));
        mvc.perform(get("/visits/" + id)).andExpect(status().isNotFound());
    }
    @Test void eliminarInexistente() throws Exception {
        mvc.perform(delete("/visits/999999")).andExpect(status().isNotFound());
    }
    @Test void rechazarDatosIncompletos() throws Exception {
        long before = repository.count();
        mvc.perform(post("/visits").contentType("application/json").content("{}")).andExpect(status().isBadRequest());
        assertEquals(before, repository.count());
    }
}
