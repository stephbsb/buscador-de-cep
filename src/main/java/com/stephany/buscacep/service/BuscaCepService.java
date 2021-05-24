package com.stephany.buscacep.service;

import com.stephany.buscacep.exception.CepInvalidoException;
import com.stephany.buscacep.response.Endereco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.stephany.buscacep.mock.EnderecoMock.enderecosMocados;

@Service
@Slf4j
public class BuscaCepService {

    private static final int MENOR_CEP_VALIDO = 1000000;
    private static final int TAMANHO_MAX = 8;

    public Endereco buscaCep(String cep) throws CepInvalidoException {
        StringBuilder novoCep = new StringBuilder(cep);
        Integer count = 1;
        Endereco endereco;

        while (count <= TAMANHO_MAX) {

            log.info("m=buscaCep, stage=tentativa, cep={}", novoCep, count);
            isCepValido(novoCep);
            endereco = enderecosMocados.get(novoCep.toString());

            if(endereco != null){
                log.info("m=buscaCep, stage=success, cep-novo={}, cep-original={}, endereco={}", novoCep, cep, endereco);
                return endereco;
            }

            count = adicionaZeroNoFinal(novoCep, count);

        }

        throw new CepInvalidoException("CEP não existe");
    }

    public void isCepValido(StringBuilder cep) throws CepInvalidoException {
        if (cep.isEmpty()) {
            throw new CepInvalidoException("CEP vazio ou nulo");
        } else if (!cep.toString().matches("\\d{8}")) {
            throw new CepInvalidoException("Formato de CEP inválido");
        } else if (Integer.parseInt(cep.toString()) < MENOR_CEP_VALIDO) {
            throw new CepInvalidoException("CEP não existe");
        }
    }

    public int adicionaZeroNoFinal(StringBuilder cep, Integer count) {
        while(cep.charAt(TAMANHO_MAX - count) == '0'){
            if(count < TAMANHO_MAX){
                count++;
            } else {
                return count;
            }
        }
        cep.deleteCharAt(TAMANHO_MAX - count).append("0");
        count++;
        return count;
    }
}
