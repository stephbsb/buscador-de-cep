package com.stephany.buscacep.controller;

import com.stephany.buscacep.response.BuscaCepResponse;
import com.stephany.buscacep.response.ErroResponse;
import com.stephany.buscacep.service.BuscaCepService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiModel("Busca CEP")
@Slf4j
public class BuscaCepController {

    private BuscaCepService buscaCepService;

    public BuscaCepController(BuscaCepService buscaCepService) {
        this.buscaCepService = buscaCepService;
    }

    @ApiOperation(value = "Retorna o endereço relacionado ao CEP inserido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o endereço correspondente ao CEP", response = BuscaCepResponse.class),
            @ApiResponse(code = 400, message = "CEP inválido", response = ErroResponse.class),
            @ApiResponse(code = 500, message = "Excessão durante busca")
    })
    @GetMapping("/busca-cep")
    public ResponseEntity<Object> buscaCep(@RequestParam(value = "cep") String cep) {
        try{
            if (buscaCepService.isCepValido(cep)) {
                BuscaCepResponse buscaCepResponse = buscaCepService.buscaCep(cep);
                return ResponseEntity.status(HttpStatus.OK).body(buscaCepResponse);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse("CEP invalido"));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
