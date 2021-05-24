package com.stephany.buscacep.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("Erro")
public class ApiError {

    @ApiModelProperty(value = "Mensagem de erro", example = "CEP inválido")
    @JsonProperty("erro")
    private String erro;

}
