# Buscador de CEP

Este é um projeto que tem por objetivo expor um serviço de busca de CEP.
Ao informar o CEP a aplicação irá retornar ao usuario o nome da RUA, BAIRRO, CIDADE e ESTADO no formado JSON

Um cep válido é um CEP que contenha apenas números e 8 dígitos, qualquer outro formato será considerado inválido.
 
Ex CEP Válido: 99999999 

Resposta no caso de sucesso:

```` json
{
    "rua": "Nome da Rua",
    "bairro": "Nome do Bairro",
    "cidade": "Nome da Cidade",
    "estado": "Nome do Estado"
}
````  

Demais respostas podem ser vistas na Documentação do Swagger: http://localhost:8080/swagger-ui.html


## Tecnologias Utilizadas

**Java** - Devido a maior familiaridade e experiência com a linguagem foi a linguagem escolhida para o desenvolvimento. 

**SpringBoot**: Usado pela facilidade de configuração de projeto e por ter um servidor de aplicações embutido que facilita 
a utilização para criação de serviço do tipo REST. Inicialização do projeto feito com o Spring Inicializr.

**Swagger**: Para documentação da API
Endpoint: http://localhost:8080/swagger-ui.html

**Lombok**: Anotações para criação de metodos getters e setters, builders e logger de forma mais limpa e objetiva.

**Actuator**: Para metricas e health check - endpoints expostos heath, metrics e env. 
Endpoint: http://localhost:8080/actuator

## Lógica



## Testes



## Como rodar a aplicação