package com.stephany.buscacep.mock;

import com.stephany.buscacep.response.Endereco;
import lombok.Getter;

import java.util.Hashtable;

@Getter
public class EnderecoMock {

    public static final Hashtable<String, Endereco> enderecosMocados  = new Hashtable<>();

    public static void mocaEnderecos(){
        Endereco endereco1 = Endereco.builder()
                .rua("São Pedro")
                .bairro("Santo Antônio")
                .cidade("Patos")
                .estado("PB")
                .build();

        Endereco endereco2 = Endereco.builder()
                .rua("Beco Sete")
                .bairro("Condor")
                .cidade("Belém")
                .estado("PA")
                .build();

        Endereco endereco3 = Endereco.builder()
                .rua("Avenida Pacaembu")
                .bairro("Pacaembu")
                .cidade("São Paulo")
                .estado("SP")
                .build();

        Endereco endereco4 = Endereco.builder()
                .rua("Rua Inah César Rosas")
                .bairro("Tayamã Park")
                .cidade("Campo Grande")
                .estado("MS")
                .build();

        Endereco endereco5 = Endereco.builder()
                .rua("Rua Marechal Deodoro")
                .bairro("Centro")
                .cidade("Cachoeira do Sul")
                .estado("RS")
                .build();

        enderecosMocados.put("59432657", endereco1);
        enderecosMocados.put("12345678", endereco2);
        enderecosMocados.put("01234000", endereco3);
        enderecosMocados.put("22333900", endereco4);
        enderecosMocados.put("96506300", endereco5);
    }
}
