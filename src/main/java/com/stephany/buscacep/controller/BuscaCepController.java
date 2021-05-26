package com.stephany.buscacep.controller;

import com.stephany.buscacep.exception.CepInvalidoException;
import com.stephany.buscacep.response.ApiError;
import com.stephany.buscacep.response.Endereco;
import com.stephany.buscacep.service.BuscaCepService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BuscaCepController {

    private BuscaCepService buscaCepService;

    public BuscaCepController(BuscaCepService buscaCepService) {
        this.buscaCepService = buscaCepService;
    }

    @ApiOperation(value = "Retorna o endereço relacionado ao CEP inserido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o endereço correspondente ao CEP", response = Endereco.class),
            @ApiResponse(code = 422, message = "CEP inválido", response = ApiError.class)
    })
    @GetMapping(value = "/busca-cep/{cep}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> buscaCep(@PathVariable("cep") String cep) {
        try{
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(buscaCepService.buscaCep(cep));
        } catch (CepInvalidoException e){
            log.error("m=get-buscacep, stage=error, msg=cep inválido, cep={}, message={}", cep, e.getMessage());
            return ResponseEntity.unprocessableEntity()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiError("CEP inválido"));
        }
    }
}
