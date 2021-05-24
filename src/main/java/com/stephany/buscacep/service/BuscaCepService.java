package com.stephany.buscacep.service;

import com.stephany.buscacep.exception.CepInvalidoException;
import com.stephany.buscacep.response.Endereco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuscaCepService {

    private static final int MENOR_CEP_VALIDO = 1000000;

    public Endereco buscaCep(String cep) throws CepInvalidoException{

        isCepValido(cep);

        // lógica para busca de cep

        return Endereco.builder()
                .rua("Rua das Flores")
                .bairro("Algum Bairro")
                .cidade("Recife")
                .estado("Pernambuco")
                .build();
    }

    public void isCepValido(String cep) throws CepInvalidoException{
        if(cep.isEmpty()){
            throw new CepInvalidoException("CEP vazio ou nulo");
        } else if(!cep.matches("\\d{8}")){
            throw new CepInvalidoException("Formato de CEP inválido");
        } else if (Integer.parseInt(cep) < MENOR_CEP_VALIDO) {
            throw new CepInvalidoException("CEP não existe");
        }
    }
}
