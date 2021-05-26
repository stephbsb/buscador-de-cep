package com.stephany.buscacep.service;

import com.stephany.buscacep.exception.CepInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BuscaCepServiceTest {

    @Autowired
    private BuscaCepService buscaCepService;

    @ParameterizedTest
    @ValueSource(strings = {"12345678", "12345000", "99999999", "01000000"})
    @DisplayName("Validação de ceps válidos")
    public void cepValidoNaoLancaExcessao(String cep){
        StringBuilder novoCep = new StringBuilder(cep);
        assertDoesNotThrow(() -> buscaCepService.isCepValido(novoCep));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345-678", "12345", "123456789", "abcdefgh", "", "00000000", "00100000"})
    @DisplayName("Validação de ceps inválidos devem lançar excessão")
    public void cepInvalidoLancaExcessao(String cep) {
        StringBuilder novoCep = new StringBuilder(cep);
        assertThrows(CepInvalidoException.class, () -> buscaCepService.isCepValido(novoCep));
    }

    @Test
    @DisplayName("Adiciona zeros no final primeira vez")
    public void adicionaZerosAoFinal(){
        StringBuilder novoCep = new StringBuilder("12345678");
        int count = buscaCepService.adicionaZeroNoFinal(novoCep,1);
        assertEquals("12345670",novoCep.toString());
        assertEquals(2,count);
    }

    @Test
    @DisplayName("Adiciona zeros no final segunda vez")
    public void adicionaZerosAoFinalSegundaVez(){
        StringBuilder novoCep = new StringBuilder("12345670");
        int count = buscaCepService.adicionaZeroNoFinal(novoCep,2);
        assertEquals("12345600",novoCep.toString());
        assertEquals(3,count);
    }

    @Test
    @DisplayName("Adiciona zeros no final a partir de posição pulando zeros existentes")
    public void adicionaZerosAoFinalEPulaZerosExistentes(){
        StringBuilder novoCep = new StringBuilder("12345000");
        int count = buscaCepService.adicionaZeroNoFinal(novoCep,1);
        assertEquals("12340000",novoCep.toString());
        assertEquals(5,count);
    }

    @Test
    @DisplayName("Retorna Endereco para endereco valido")
    public void retornaEnderecoParaCepValido(){

    }

    @Test
    @DisplayName("Retorna Endereco para endereco valido apos adicionar zeros")
    public void retornaEnderecoParaCepValidoAposAdicionarZeros(){

    }

    @Test
    @DisplayName("Lanca excessao para CEP invalido")
    public void lancaExcessaoParaCepInvalido(){

    }

    @Test
    @DisplayName("Lanca excessao para CEP inexistente")
    public void lancaExcessaoParaCepInexistente(){

    }

}
