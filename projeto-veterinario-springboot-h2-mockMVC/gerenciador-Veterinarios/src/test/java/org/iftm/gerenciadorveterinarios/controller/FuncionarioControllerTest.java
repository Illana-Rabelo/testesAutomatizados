package org.iftm.gerenciadorveterinarios.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class FuncionarioControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void TestarControllerListarTodos() throws Exception {
                mockMvc.perform(get("/funcionarios"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andExpect(jsonPath("$").isArray());

        }

        @Test
        public void TestarControllerBuscarPorId() throws Exception {
                mockMvc.perform(get("/funcionarios/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.nome").value("Leonardo Andrade"));

        }

        @Test
        public void TestarControllerBuscarPorNome() throws Exception {
                mockMvc.perform(get("/funcionarios/nome/Leonardo"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].nome").value("Leonardo Andrade"))
                                .andReturn();
        }

        @Test
        public void TestarControllerSalvarNovoFuncionario() throws Exception {

                String json = """
                                {
                                 "nome":"João",
                                 "cargo":"Gerente",
                                 "salario":4800,
                                 "emFerias":false,
                                 "ativo":true
                                }
                                """;

                mockMvc.perform(post("/funcionarios")
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nome").value("João"))
                                .andExpect(jsonPath("$.cargo").value("Gerente"))
                                .andExpect(jsonPath("$.salario").value(4800))
                                .andExpect(jsonPath("$.emFerias").value(false))
                                .andExpect(jsonPath("$.ativo").value(true));

        }

        @Test
        public void TestarControllerBuscarPorIdInexistente() throws Exception {
                mockMvc.perform(get("/funcionarios/999")) 
                                .andExpect(status().isNotFound()) 
                                .andExpect(jsonPath("$.error").value("Resource not found")); 
        }
}
