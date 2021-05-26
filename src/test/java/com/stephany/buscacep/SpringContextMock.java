package com.stephany.buscacep;

import com.stephany.buscacep.exception.CepInvalidoException;
import com.stephany.buscacep.response.Endereco;
import com.stephany.buscacep.service.BuscaCepService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SpringContextMock {

    protected static final String CEP_VALIDO = "60527011";
    protected static final String CEP_INVALIDO = "60527abc";
    protected static final String ENDERECO_JSON = "{\"rua\":\"Travessa Oriente\",\"bairro\":\"Autran Nunes\",\"cidade\":\"Fortaleza\",\"estado\":\"CE\"}";
    protected static final String ERRO_JSON = "{\"erro\":\"CEP inválido\"}";

    @MockBean
    protected BuscaCepService buscaCepService;

    protected void dadoCepValidoRetornaEndereco() throws CepInvalidoException {
        Endereco endereco = Endereco.builder()
                .rua("Travessa Oriente")
                .bairro("Autran Nunes")
                .cidade("Fortaleza")
                .estado("CE")
                .build();

        Mockito.when(this.buscaCepService.buscaCep(any()))
                .thenReturn(endereco);
    }

    protected void dadoCepInvalidoLancaEscessao() throws CepInvalidoException {
        Mockito.when(this.buscaCepService.buscaCep(any()))
                .thenThrow(CepInvalidoException.class);
    }

}
