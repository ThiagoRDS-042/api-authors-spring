<h1 align="center">API Authors Spring 💻</h1>

<br>

## ✨ Tecnologias

Esse projeto foi desenvolvido com as seguintes tecnologias:

- [OpenAPI3](https://springdoc.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [H2 Database](https://mvnrepository.com/artifact/com.h2database/h2)
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
- [Spring Testing](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testing-introduction)
- [Mockito](https://site.mockito.org)
- [MinIO](https://min.io/)
- [Docker](https://www.docker.com)
- [Jest](https://jestjs.io/pt-BR/)
- [REST]

## 🛠️ Configuração

É necessário criar as credenciais de acesso do MinIO:

- Utilize o comando `docker compose up -d` para subir os containers do banco de dados postgresql e o Minio;
- Acesse `http://localhost:9001`, e faça login com as credenciais especificadas no arquivo `docker-compose.yml`. Sendo as credenciais: `MINIO_ROOT_USER` e `MINIO_ROOT_PASSWORD`, para usuário e senha, respectivamente;
- Navegue até `Access Keys` e clique em `Create access key`;
- Preencha os campos opcionais como queira e clique em `Create`;
- Agora copie as credências geradas: `Access Key` e `Secret Key` e substitua os valores de `minio.access-key` e `minio.secret-key`, presentes nos arquivos `application.properties` e `application-test.properties`.

## 🚀 Testando e Construindo a aplicação

Execute o comando:

```sh
$ mvn clean install
```

## 🚀 Executar a aplicação

Execute o comando:

```sh
$ mvn clean spring-boot:run
```

Agora você já pode fazer suas requisições a partir de um client de sua preferencia.

## 📄 Documentação da aplicação

- Swagger

  - Para acessar a documentação da API basta iniciar a aplicação, abrir seu navegador e acessar `http://localhost:8080/swagger-ui/index.html`

- Insomnia
  - Há um arquivo do insomnia em `./docs/Insomnia.json` importe em seu aplicativo e teste as requisições.

### Autor

---

Feito por 💻 Thiago Rodrigues 👋🏽
