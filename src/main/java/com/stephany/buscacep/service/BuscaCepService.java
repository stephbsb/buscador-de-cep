package com.stephany.buscacep.service;

import com.stephany.buscacep.response.BuscaCepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuscaCepService {

    public BuscaCepResponse buscaCep(String cep) {

        log.info("m=buscaCep, msg=inicio, cep={}", cep);

        // lógica para busca de cep

        return BuscaCepResponse.builder()
                .rua("Rua das Flores")
                .bairro("Algum Bairro")
                .cidade("Recife")
                .estado("Pernambuco")
                .build();
    }

    public boolean isCepValido(String cep) {
        return cep.matches("\\d{8}");
    }

}
