package com.stephany.buscacep.controller;

import com.stephany.buscacep.exception.CepInvalidoException;
import com.stephany.buscacep.response.ApiError;
import com.stephany.buscacep.response.Endereco;
import com.stephany.buscacep.service.BuscaCepService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @ApiResponse(code = 422, message = "CEP inválido")
    })
    @GetMapping(value = "/busca-cep")
    public ResponseEntity<Object> buscaCep(@RequestParam(value = "cep") String cep) {
        try{
            return ResponseEntity.ok().body(buscaCepService.buscaCep(cep));
        } catch (CepInvalidoException e){
            log.error("m=get-buscacep, stage=error, msg=cep inválido, cep={}, message={}", cep, e.getMessage());
            return ResponseEntity.unprocessableEntity().body(new ApiError("CEP inválido"));
        }
    }
}
