# 🖥️ Servidor i9
[![☕ Serviço de App Web Backend Java na Azure CI/CD](https://github.com/i9-tech/i9-server/actions/workflows/feature-integracao-azure-web-app_i9-server-backend.yml/badge.svg)](https://github.com/i9-tech/i9-server/actions/workflows/feature-integracao-azure-web-app_i9-server-backend.yml)
-----

### 🌟 Boas-vindas\!

Esse é o repositório da **i9 Tech** referente aos servidores de nosso sistema. Esse repositório tem como propósito **armazenar** toda nossa **regra de negócio** junto a **camadas de segurança** e **fluxos de funcionamento**, tudo em **um só servidor**.

Com a evolução para a `feature/aws`, o projeto agora utiliza uma arquitetura robusta pronta para a nuvem, incluindo Docker, balanceamento de carga e processamento assíncrono com RabbitMQ.

-----

## 🏛️ Arquitetura (feature/aws)

Esta branch introduz uma arquitetura de microsserviços e contêineres para garantir escalabilidade e resiliência:

  * **Servidor Principal (Spring Boot):** Continua sendo o cérebro da aplicação, lidando com a regra de negócio principal (CRUDs, segurança, etc.).
  * **Balanceamento de Carga:** O `docker-compose` inicia **três instâncias** do servidor principal (`app-1`, `app-2`, `app-3`) para distribuir o tráfego e garantir alta disponibilidade.
  * **Banco de Dados:** Um contêiner `MySQL` dedicado para persistência dos dados.
  * **Filas (RabbitMQ):** Utilizamos o RabbitMQ como *Message Broker* para processamento assíncrono, desacoplando serviços:
      * `rabbitmq-javamail`: Fila para o microsserviço de envio de e-mails (ex: recuperação de senha, notificações).
      * `rabbitmq-twilio`: Fila para o microsserviço de notificações via SMS (ex: status de pedido).
      * `rabbitmq-images`: Fila para o microsserviço de processamento e upload de imagens (ex: fotos de pratos/produtos).

-----

## 📋 Requisitos de Uso

Para rodar o ambiente de produção/desenvolvimento localmente, você precisará de:

  * [Docker](https://www.docker.com/get-started)
  * [Docker Compose](https://docs.docker.com/compose/install/)

Para desenvolvimento do código-fonte:

  * IntelliJ IDEA ou outra IDE de sua preferência
  * Java 21
  * SDK 21
  * Maven 3.9.9

-----

## 🧳 Dependências

Além das ferramentas, o servidor utiliza as seguintes dependências principais:

  - Springboot
  - Spring Data JPA (com MySQL)
  - Spring AMQP (RabbitMQ)
  - Validation
  - Lombok
  - Swagger (OpenAPI)
  - JSON Web Token (JWT)
  - Spring Security
  - H2 Database (para testes)

<br>

-----

## 🚀 Executando a Aplicação (Docker)

A forma mais simples de subir todo o ambiente é usando o Docker Compose.

1.  Clone o repositório:

    ```sh
    git clone https://github.com/i9-tech/i9-server.git
    ```

2.  Acesse o diretório do projeto:

    ```sh
    cd i9-server
    ```

3.  **Crie um arquivo `.env`** na raiz do projeto. Este arquivo é **essencial** para injetar as variáveis de ambiente no `docker-compose`. Preencha-o com base nas variáveis listadas na próxima seção.

4.  Suba os contêineres em modo "detached" (background):

    ```sh
    docker-compose up -d
    ```

5.  Pronto\! O ambiente estará rodando:

      * **Aplicação (Balanceada):**
          * `http://localhost:8080` (Instância 1)
          * `http://localhost:8081` (Instância 2)
          * `http://localhost:8082` (Instância 3)
      * **Banco de Dados (MySQL):** `localhost:3307` (acessível externamente)
      * **RabbitMQ (Javamail):** `http://localhost:15672` (Painel de Gestão)
      * **RabbitMQ (Twilio):** `http://localhost:15673` (Painel de Gestão)
      * **RabbitMQ (Images):** `http://localhost:15674` (Painel de Gestão)

<br>

-----

## 🔐 Variáveis de Ambiente

### Backend (Spring Boot)

Utilizamos o `application.properties` para configurar a aplicação. Para desenvolvimento local (fora do Docker), pode-se usar um `dev.properties` (ignorado pelo `.gitignore`) para sobrescrever valores.

```properties
# Exemplo de application.properties
spring.datasource.password=${DB_PASSWORD}
app.rabbitmq.primary.host=${RABBIT_JAVAMAIL_HOST}
```

### Docker Compose (`.env`)

Para que o `docker-compose.yml` funcione, crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```env
# Configuração do Banco de Dados MySQL
MYSQL_ROOT_PASSWORD=seu-password-root
MYSQL_DATABASE=nome-do-banco
MYSQL_USER=usuario-banco
MYSQL_PASSWORD=senha-banco

# Credenciais RabbitMQ (Javamail)
RABBIT_JAVAMAIL_USERNAME=user_javamail
RABBIT_JAVAMAIL_PASSWORD=pass_javamail

# Credenciais RabbitMQ (Twilio)
RABBIT_TWILIO_USERNAME=user_twilio
RABBIT_TWILIO_PASSWORD=pass_twilio

# Credenciais RabbitMQ (Images)
RABBIT_IMAGES_USERNAME=user_images
RABBIT_IMAGES_PASSWORD=pass_images
```

⚠️ **Atenção:** Nunca suba arquivos `.env` ou `dev.properties` com dados sensíveis para o repositório.

<br>

-----

## ✨ Funcionalidades Principais

Este servidor oferece um CRUD completo para gestão de restaurantes e estoques, além de funcionalidades avançadas:

  * **Autenticação e Autorização:** Controle de acesso granular por funcionário usando JWT e Spring Security.
  * **Processamento Assíncrono:** Uso de RabbitMQ para tarefas que não precisam bloquear o usuário, como:
      * Envio de e-mails (confirmação, recuperação de senha).
      * Envio de SMS via Twilio.
      * Upload e processamento de imagens.
  * **Paginação:** Os principais endpoints de listagem (ex: `/produtos`, `/pratos`) suportam paginação via query params (`?page=0&size=10&sort=nome,asc`) para melhor performance.
  * **Escalabilidade Horizontal:** A arquitetura está pronta para escalar. O Docker Compose já simula isso com 3 instâncias da aplicação rodando em balanceamento de carga.

<br>

-----

## 🔐 Camada de Segurança

A camada de segurança do sistema é baseada em **Spring Security** com autenticação via **JWT (JSON Web Token)**. Ao fazer login, o usuário recebe um `token JWT assinado`, que deve ser enviado em cada requisição no cabeçalho `Authorization`. O token é validado por um filtro personalizado, e, se for válido, o usuário é **autenticado automaticamente**. O controle de acesso aos endpoints é feito com base nos papéis definidos no token.

<br>

-----

## ⚠️ ATENÇÃO (Usuário de Teste)

Para utilizar qualquer função dentro de nosso servidor, será necessário informar um `token de segurança` a partir de um **login**. existe um usuário padrão que é criado para testes, é possível utilizar seu login com as credenciais:

  * 000.000.000-00
  * 00000000000@teste

Esse usuário pertence a uma empresa fictícia e não tem nenhuma ligação com clientes reais da aplicação.

<br>

-----

## 📚 Entidades

As entidades presentes no servidor são:

  - Categoria
  - Empresa
  - Funcionário
  - ItemCarrinho
  - Pedido
  - Prato
  - Setor
  - Venda

<br>

-----

## 📂 Pacotes

Os pacotes presentes em nossa aplicação são:

  - `Config`: Armazena configurações de segurança, Swagger e RabbitMQ.
  - `Controller`: Armazena os controladores (endpoints) e seus DTOs/Mappers.
  - `Entity`: Armazena as entidades JPA do sistema.
  - `Exception`: Armazena as classes de exceções customizadas.
  - `Repository`: Armazena as interfaces do Spring Data JPA.
  - `Service`: Armazena a camada de serviço (regra de negócio).
  - `Consumer`: (Novo) Classes responsáveis por consumir mensagens das filas RabbitMQ.

<br>

-----

## 🧪 Exemplos de Uso

Exemplos de respostas de sucesso da API:

1.  Cadastrar Empresa (`POST /empresas`)

    ```json
    {
    	"id": 4,
    	"nome": "Nome da Empresa",
    	"cnpj": "12345678901234",
    	"endereco": "Rua Exemplo, 123",
    	"dataCadastro": "2025-04-25",
    	"ativo": true
    }
    ```

2.  Realizar Login (`POST /colaboradores/login`)

    ```json
    {
    	"userId": 1,
    	"empresaId": 1,
    	"nome": "João Silva",
    	"token": "eyJhbGciOiJIUzUxMiJ9"
    }
    ```

3.  Remover Funcionário (`DELETE /colaboradores/1/1`)

    ```http
    204 No Content
    No body returned for response
    ```

<br>

-----

## 📖 Swagger

Ao executar a aplicação (via Docker ou IDE), acesse a documentação da API para ver todos os endpoints, modelos e testar as rotas:

`http://localhost:8080/swagger-ui/index.html#/`

*(Substitua `8080` por `8081` ou `8082` se estiver testando as outras instâncias)*

<br>

-----

## 🔗 Integração (Front-End)

A i9 oferece um repositório com a interface gráfica (Front-End) desenvolvida para consumir este servidor.

Acesse o link para clonar e rodar a aplicação:
[https://github.com/i9-tech/i9-application](https://github.com/i9-tech/i9-application)

<br>

-----

## 📜 Licença

Este projeto está licenciado sob a Licença MIT.
Consulte o arquivo [LICENSE](./LICENSE) para mais detalhes.
<br/>
i9 Tech 2025 © Todos os direitos reservados. <br/>
i9 Tech 2025 &copy; Todos os direitos reservados.
