package com.stephany.buscacep.controller;

import com.stephany.buscacep.SpringContextMock;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuscaCepController.class)
public class BuscaCepControllerTest extends SpringContextMock {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DisplayName("Dado um CEP válido, deve retornar o endereço correspondente")
    public void cepValidoEEnderecoExiste() {

        dadoCepValidoRetornaEndereco();

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/busca-cep/{cep}",CEP_VALIDO)
                        .accept(MediaType.APPLICATION_JSON));


        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(ENDERECO_JSON));
    }

    @SneakyThrows
    @Test
    @DisplayName("Dado um CEP Inválido, deve retornar uma mensagem de erro")
    public void cepInvalidoERetornaErro() {

        dadoCepInvalidoLancaEscessao();

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/busca-cep/{cep}",CEP_INVALIDO)
                        .accept(MediaType.APPLICATION_JSON));


        response.andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(ERRO_JSON));
    }
}
