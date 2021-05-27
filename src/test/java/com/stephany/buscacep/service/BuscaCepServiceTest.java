package com.stephany.buscacep.service;

import com.stephany.buscacep.exception.CepInvalidoException;
import com.stephany.buscacep.response.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BuscaCepServiceTest {

    @Spy
    @InjectMocks
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
    @DisplayName("Retorna Endereco para cep valido sem adicionar zeros")
    public void retornaEnderecoParaCepValido() throws CepInvalidoException {
        mockFindByCepExiste("12345678");
        Endereco endereco = buscaCepService.buscaCep("12345678");
        assertNotNull(endereco);
        Mockito.verify(buscaCepService,Mockito.times(0)).adicionaZeroNoFinal(any(),anyInt());
    }

    @Test
    @DisplayName("Retorna Endereco para endereco valido apos adicionar zeros")
    public void retornaEnderecoParaCepValidoAposAdicionarZeros() throws CepInvalidoException{
        mockFindByCepExiste("12345000");
        Endereco endereco = buscaCepService.buscaCep("12345678");
        assertNotNull(endereco);
        Mockito.verify(buscaCepService,Mockito.times(3)).adicionaZeroNoFinal(any(),anyInt());
    }

    @Test
    @DisplayName("Retorna Endereco para endereco valido apos adicionar zeros pulando zeros existentes")
    public void retornaEnderecoParaCepValidoAposAdicionarZerosComZerosExistentes() throws CepInvalidoException{
        mockFindByCepExiste("12340000");
        Endereco endereco = buscaCepService.buscaCep("12345600");
        assertNotNull(endereco);
        Mockito.verify(buscaCepService,Mockito.times(2)).adicionaZeroNoFinal(any(),anyInt());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345-678", "12345", "123456789", "abcdefgh", "", "00000000", "00100000"})
    @DisplayName("Lanca excessao para CEP invalido")
    public void lancaExcessaoParaCepInvalido(String cep) {
        assertThrows(CepInvalidoException.class, () -> buscaCepService.buscaCep(cep));
    }

    @Test
    @DisplayName("Lanca excessao para CEP inexistente apos todos as tentativas de adicionar zeros")
    public void lancaExcessaoParaCepInexistente() {
        mockFindByCepNaoExiste();
        assertThrows(CepInvalidoException.class, () -> buscaCepService.buscaCep("12345600"));
        Mockito.verify(buscaCepService,Mockito.times(6)).adicionaZeroNoFinal(any(),anyInt());
    }

    private void mockFindByCepExiste(String cep){
        Endereco endereco = Endereco.builder()
                .rua("Travessa Oriente")
                .bairro("Autran Nunes")
                .cidade("Fortaleza")
                .estado("CE")
                .build();

        Mockito.when(buscaCepService.findByCep(cep))
                .thenReturn(endereco);
    }

    private void mockFindByCepNaoExiste(){
        Mockito.when(buscaCepService.findByCep(anyString()))
                .thenReturn(null);
    }

}
