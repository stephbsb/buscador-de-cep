# Buscador de CEP - Problema

Eu, como usuário, quero informar meu CEP e obter o nome da minha 
    RUA, BAIRRO, CIDADE e ESTADO para preencher meus dados de cadastro de forma automática.
    Os critérios de aceite dessa história são:
        · Dado um CEP válido, deve retornar o endereço correspondente
        · Dado um CEP válido, que não exista o endereço, deve substituir um dígito da direita para a esquerda por zero
          até que o endereço seja localizado (Exemplo: Dado 22333999 tentar com 22333990, 22333900 …)
        · Dado um CEP inválido, deve retornar uma mensagem reportando o erro: "CEP inválido"

Para a solução do problema foi criado um serviço rest que recebe requisições atraves de um endpoint exposto que irá ter como 
parâmetro o cep do usuario e irá retornar os dados mensionados anteriormente.

## ponto de entrada - BuscaCepController

A aplicação tem seu ponto de entrada da api no BuscaCepController. Foi escolhido o método GET para a api, já o objetivo é de busca
e foi usando path variable como forma de passar o cep do usuario. 

Para a implementação do controller foi utilizada o @RestController do springframework-web por facilidade de implementação e facilita bastante a
criação de apis de forma rapida e limpa. Retornando um ResponseEntity<Object> para viabilizar o retorno de jsons diferentes pelo mesmo endpoint. 

Endpoint de chamada: GET http://localhost:8080/busca-cep/{cep}
 
A api possui duas mensagens de retorno possíveis. Se o cep for válido e existente retorna um status 200 e um json com os dados de endereco
como o mostrado abaixo:
```` json
{
    "rua": "Nome da Rua",
    "bairro": "Nome do Bairro",
    "cidade": "Nome da Cidade",
    "estado": "Nome do Estado"
}
````  

No caso de um cep invalido ou inexistente ele retorna uma mensagem de erro também no formado JSON e um status 422 de 
UnprocessableEntity.
```` json
{
    "erro": "CEP inválido"
}
````

## logica - BuscaCepService

### dados

Para ter um banco de dados fake (mock) foi feita uma classe que possui uma Hashtable constante que é inicializada ao iniciar
a aplicação no main. Foi escolhido a hashtable pela velocidade de busca de dados, já que é feita por meio de endereçamento 
hash sem precisar pesquisar uma lista inteira de dados. No caso, não tem tanta diferença já que so foram adicionados 5 endereços, 
mas em um volume maior de dados faz diferença.

Alem disso, outro motivo de escolher essa forma foi por se assemelhar a chamado de um método de um JPA por exemplo.
Dentro do service ele essa busca é feita por um metodo chamado findByCep para simular uma chamada de banco.  

Endereços mocados e possuem endereço existente: 59432657 12345678 01234000 22333900 96506300

### validação

Para a validação do cep foram consideradas as seguintas condições nesta ordem:
- o cep nao pode ser uma string vazia
- deve ter 8 digitos numericos, ou seja nao deve ter pontos ou traços, apenas numeros.
- deve ser maior que o menos CEP possivel. O menor cep possivel no Brasil hoje é o cep 01000000
As condições foram adicionadas separadamente para ter logs mais ricos em infomação.
A validação irá lançar uma excessao do tipo CepInvalidoException que sera tratado no catch no controller e ira retornar
a mensagem de erro json para o uruario e logar o erro.

Como há a possibilidade de varios testes adicionando zeros ao final de um cep valido a validação foi colocada no inicio de um loop
para que a cada vez que um zero for adicionado deve passar pela validação, para nao ocorrer casos de o cep ficar menor que o valor 
minimo possivel.


### Logica do processamento

Abaixo temos o fluxograma que mostra como funciona a logica.

![Alt text](./doc/fluxograma.png?raw=true "Fluxograma")

Foi utilizado StringBuilder para a manipulação do cep, dessa forma passando o parametro por referencia para o 
metodo que faz a logica de adicionar zeros ao final ja altera o mesmo objeto em vez de criar outras strings,
alem disso os metodos fornecidos pelo StringBuilder facilita deletar e concatenar caracteres.

O loop é baseado na quantidade de caracteres maximo que podem ser substituidos antes de se tornar invalido.
Um contador indica a posição do final da string que será substituido por zero, a posição maxima que pode substituir é a posição 7
que indica a substituição do index de numero 1, para não zerar a string.

Caso ja exista um zero na posição a ser substituido, o contador é incrementado e testa novamente se é zero.
A substituição da string só é feita se ela é diferente de zero. Dessa forma pulamos zeros existentes no final da string
e retorna o contador atualizado com a posição correta relacionado a proxima vez caso venha a ter a necessidade de 
subtituir novamente por zero.
Dentro do metodo tambem há sempre um teste para nao zerar completamente a string, nesse caso apenas retorna a posição atual.

É adicionado zero ao final da string até que seja encontrado um endereço ou até que o cep se torne invalido (menor que o
menor valor possivel para um cep).


## Tecnologias Utilizadas

**Java** - Devido a maior familiaridade e experiência com a linguagem foi a linguagem escolhida para o desenvolvimento. 

**SpringBoot**: Usado pela facilidade de configuração de projeto e por ter um servidor de aplicações embutido que facilita 
a utilização para criação de serviço do tipo REST. Inicialização do projeto feito com o Spring Inicializr.

**Swagger**: Para documentação da API
Endpoint: http://localhost:8080/swagger-ui.html

**Lombok**: Anotações para criação de metodos getters e setters, builders e logger de forma mais limpa e objetiva.

**Actuator**: Para metricas e health check - endpoints expostos heath, metrics e env. 
Endpoint: http://localhost:8080/actuator


## Testes

Foram feitos varios testes unitarios, tanto para o controller usando o MockMvc para testar a reposta da api, quando para o 
Service para os metodos e a logica como um todo, usando JUnit e Mockito.


## Como executar o programa

```` shell script
clean:
$ ./gradlew clean

build: 
$ ./gradlew build --info

rodar os testes: 
$ ./gradlew test --info

executar a aplicação: 
$ ./gradlew bootRun
````

Ou execute a classe BuscacepApplication.java com a sua IDE favorita :)

## Possiveis melhorias a serem feitas

- Adicionar mais repostas ao cliente a depender da entrada
- Adicionar o um Dockerfile com configurações para criar um container com as dependencias para facilitar a execução do 
programa em qualquer ambiente
