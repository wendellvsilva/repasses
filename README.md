# Projeto de Gerenciamento de Repasses Financeiros

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot 3**: Framework para criar a API REST e gerenciar a aplicação.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Spring Security**: Configuração de segurança para proteger as rotas da API.
- **Lombok**: Biblioteca para reduzir o boilerplate de código.
- **JaCoCo**: Ferramenta para análise de cobertura de testes.

## Como Acessar o Swagger

O Swagger é integrado ao projeto para fornecer uma interface de documentação interativa para a API. Para acessá-lo:

1. Inicie a aplicação.
2. Abra um navegador e acesse a URL: `http://localhost:8080/swagger-ui.html`

Isso exibirá a interface do Swagger, onde você pode explorar e testar as rotas da API.

## Rotas com Spring Security

A aplicação está protegida por Spring Security. As rotas são configuradas para permitir apenas usuários autenticados e autorizados. A configuração de segurança está definida em uma classe `SecurityConfig`.

### Exemplos de Configuração de Rotas

- **Public Routes**: Algumas rotas podem ser acessadas sem autenticação.
- **Protected Routes**: Outras rotas exigem autenticação e autorização. As permissões são configuradas com base em roles e outras regras de segurança definidas.

A configuração exata pode ser encontrada na classe `SecurityConfig` em `src/main/java/com/example/projeto/config/SecurityConfig.java`.

## Como Rodar o Código

Para rodar a aplicação localmente:

1. Certifique-se de que você tem o Java 17 instalado.
2. Navegue até o diretório raiz do projeto.
3. Execute o comando Maven para construir e iniciar a aplicação:

   ```sh
   mvn clean install
   mvn spring-boot:run
Utilização de Paginação e Filtragem
Endpoints Disponíveis
Listar Repasses com Paginação e Ordenação

    URL: GET /api/repasses
    Parâmetros de Consulta:
        page: Número da página (baseado em zero, por exemplo, 0 para a primeira página).
        size: Número de itens por página (por exemplo, 10).
        sort: Campo e direção de ordenação no formato campo,direcao (por exemplo, dataVencimento,asc).

Exemplo de Requisição:

http

GET http://localhost:8080/api/repasses?page=0&size=10&sort=dataVencimento,asc

Filtrar Repasses

    URL: GET /api/repasses/filtrar
    Parâmetros de Consulta:
        tipoRepasse: Tipo de repasse (por exemplo, SELLER).
        sistemaOrigem: Sistema de origem (por exemplo, ECOM).
        page: Número da página.
        size: Número de itens por página.
        sort: Campo e direção de ordenação.

Exemplo de Requisição com Filtro e Paginação:

http

GET http://localhost:8080/api/repasses/filtrar?tipoRepasse=SELLER&sistemaOrigem=ECOM&page=0&size=5&sort=dataVencimento,asc

Exemplo de Resposta

A resposta incluirá informações de paginação e os repasses correspondentes:

json

{
"totalPages": 1,
"totalElements": 1,
"size": 10,
"content": [
{
"id": 1,
"tipoRepasse": "SELLER",
"valorRepasse": 1000.00,
"dataVencimento": "2024-09-01T00:00:00",
"formaPagamento": "TRANSFERENCIA_BANCARIA",
"sistemaOrigem": "ECOM",
"dataCriacao": "2024-08-09T02:47:17.45645",
"dataAtualizacao": null
}
],
"number": 0,
"sort": {
"empty": false,
"sorted": true,
"unsorted": false
},
"pageable": {
"pageNumber": 0,
"pageSize": 10,
"sort": {
"empty": false,
"sorted": true,
"unsorted": false
},
"offset": 0,
"paged": true,
"unpaged": false
},
"first": true,
"last": true,
"numberOfElements": 1,
"empty": false
}