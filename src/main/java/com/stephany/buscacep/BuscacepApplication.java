package com.stephany.buscacep;

import com.stephany.buscacep.mock.EnderecoMock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuscacepApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuscacepApplication.class, args);

		EnderecoMock.mocaEnderecos();
	}

}
