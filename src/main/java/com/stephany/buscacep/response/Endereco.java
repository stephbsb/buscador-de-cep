package com.stephany.buscacep.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
@ApiModel("Endereço")
public class Endereco {

    @ApiModelProperty(value = "Nome da Rua", example = "Rua das Palmeiras")
    @JsonProperty("rua")
    private String rua;

    @ApiModelProperty(value = "Nome do Bairro", example = "Jardim das Flores")
    @JsonProperty("bairro")
    private String bairro;

    @ApiModelProperty(value = "Nome da Cidade", example = "Recife")
    @JsonProperty("cidade")
    private String cidade;

    @ApiModelProperty(value = "Nome do Estado", example = "Pernambuco")
    @JsonProperty("estado")
    private String estado;

}
