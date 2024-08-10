# Projeto de Gerenciamento de Repasses Financeiros

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot 3**: Framework para criar a API REST e gerenciar a aplicação.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Spring Security**: Configuração de segurança para proteger as rotas da API.
- **Lombok**: Biblioteca para reduzir o boilerplate de código.
- **JaCoCo**: Ferramenta para análise de cobertura de testes.


## Pré-requisitos 
**Certifique-se de ter os seguintes itens instalados:**
- Java 17
- Maven

## Configuração do Banco de Dados H2

O H2 está pré-configurado para desenvolvimento e testes. A configuração padrão usa o perfil test e o banco de dados é inicializado em memória. Para ajustar ou verificar a configuração, edite o arquivo src/main/resources/application.properties:

## Iniciar a aplicação 
Para iniciar a aplicação, execute os seguintes comandos Maven:
```sh
mvn clean install
mvn spring-boot:run
```

## Acessar o Console H2
Para visualizar o banco de dados H2:
- Abra um navegador e acesse a URL:` http://localhost:8080/h2-console `
- Insira o username: `fera`
- Insira a senha: `fera`
- **Configure os parâmetros de conexão:**
- JDBC URL: jdbc:h2:mem:testdb
- Username: `sa`
- Password: `password`
- ## Como Acessar o Swagger

O Swagger é integrado ao projeto para fornecer uma interface de documentação interativa para a API. Para acessá-lo:

1. Inicie a aplicação.
2. Abra um navegador e acesse a URL: `http://localhost:8080/swagger-ui.html`

Isso exibirá a interface do Swagger, onde você pode explorar e testar as rotas da API.

## Rotas com Spring Security

A aplicação está protegida por Spring Security. As rotas são configuradas para permitir apenas usuários autenticados e autorizados. A configuração de segurança está definida em uma classe `SecurityConfig`.

### Exemplos de Configuração de Rotas

- **Public Routes**: Algumas rotas podem ser acessadas sem autenticação.
- **Protected Routes**: Outras rotas exigem autenticação e autorização. As permissões são configuradas com base em roles e outras regras de segurança definidas.

A configuração exata pode ser encontrada na classe `SecurityConfig` em <br>
`src/main/java/com/example/projeto/config/SecurityConfig.java`.

}

### Método POST
Cadastrar um repasse
- URL: `http://localhost:8080/api/repasses`
Exemplo Json:
```sh
{
  "tipoRepasse": "SELLER",
  "valorRepasse": 1240.00,
  "dataVencimento": "2024-09-01T00:00",
  "formaPagamento": "TRANSFERENCIA_BANCARIA",
  "sistemaOrigem": "ECOM"
}
```

### Método GET
Listar repasses com paginação<br>
Exemplos de URL
- URL: `http://localhost:8080/api/repasses`
- URL: `http://localhost:8080/api/repasses?page=0&size=10&sortBy=id&sortDir=asc` (Página 0, tamanho 10, ordenado por id em ordem crescente)
- URL: `http://localhost:8080/api/repasses?page=1&size=20&sortBy=nome&sortDir=desc` (Página 1, tamanho 20, ordenado por nome em ordem decrescente)

### Método GET
Listar pelo ID
- URL:`http://localhost:8080/api/repasses/1`
### Método GET
Listar repasses com filtragem
Exemplos de URL
- URL:  `http://localhost:8080/api/repasses/filtrar?tipoRepasse=SELLER`
- URL:  `http://localhost:8080/api/repasses/filtrar?tipoRepasse=CONTABIL`
### Método PUT
Atualizar repasses(Precisamos do ID como parâmetro)
- URL: `http://localhost:8080/api/repasses/1`
Exemplo JSON:
```sh
{
  "tipoRepasse": "CONCILIACAO",
  "valorRepasse": 1400.00,
  "dataVencimento": "2024-10-01T00:00:00.000Z",
  "formaPagamento": "BOLETO",
  "sistemaOrigem": "LOGISTICA"
}
```

### Método DELETE
Deletar um repasse(ID como parâmetro)
- URL: `http://localhost:8080/api/repasses/1`

### Rotas autenticadas
Criei algumas rotas autenticadas pra validar meu conhecimento no security
<br> 
- Para acessar as rotas de admin, você deve por o usuário `fera` com a senha `fera`
- Para acessar as rotas de usuario, você deve por o usuário `magalu` com a senha `magalu`
### Método GET(Admin)
Acesso ao dashboard e ferramentas pelo admin
- URL:`http://localhost:8080/api/admin/dashboard`
- URL: `http://localhost:8080/api/admin/ferramentas`

### Método GET(Usuário)
Acesso ao perfil e dados do usuário
- URL: `http://localhost:8080/api/user/perfil`
- URL: `http://localhost:8080/api/user/dados`