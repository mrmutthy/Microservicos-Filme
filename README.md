# Microserviços-Filme

Este projeto tem como objetivo criar microsserviços para um trabalho de TE29S (Tópicos Especiais em Ciência da Computação).

Ele conta com 5 CRUDs, sendo eles:

- Api-Filmes
- Api-Avaliacao
- Api-Recomendacao
- Api-Autenticacao
- Api-Usuario

Acessados por um gateway e de persistencia em um banco de dados PostgreSQL.

**Para rodar o projeto, você precisa ter em sua máquina uma versão 17 ou superior do Java.**

### Funcionalidades:

**Api-Filmes:**

- Buscar todos os filmes
- Buscar filmes por gênero
- Adicionar filmes
- Alterar filmes
- Deletar filmes

**Api-Avaliacao:**

- Buscar todas as avaliações
- Buscar as avaliações de um filme específico
- Ver a média de notas de um filme avaliado
- Buscar as avaliações de um usuário específico
- Fazer uma avaliação
- Editar uma avaliação
- Excluir uma avaliação

**Api-Usuario:**

- Buscar todos os usuários
- Adicionar usuário
- Alterar usuário
- Deletar usuário

**Api-Recomendacao:**

- Obter recomendações de filmes.
- Filtrar recomendações por nota mínima.
- Integrar avaliações e filmes.

**Api-Autenticacao:**

- **Funcionalidades a serem implementadas...**

**PostgreSQL:**

- **Ainda não está sendo utilizado o PostgreSQL para a persistência. Nessa fase inicial foi optado por utilizar o H2.**

## Requisitos

Antes de iniciar a compilação e execução, certifique-se de ter:

1. **Java JDK 17** ou superior instalado.
   - Verifique a instalação executando `java -version` no terminal.
2. **Maven** instalado (para gestão de dependências e build).
   - Verifique a instalação executando `mvn -version` no terminal.

## Compilação do Projeto

Cada microsserviço está em um módulo separado. Para compilar o projeto inteiro, siga os passos:

1. Clone o repositório:
   ```bash
   git clone https://github.com/mrmutthy/Microservicos-Filme.git
   cd Microservicos-Filme
   ```

2. Compile o projeto usando o Maven:
   ```bash
   mvn clean install
   ```
   Isso gerará os arquivos **JAR** na pasta `target/` de cada microsserviço.

## Execução dos Microsserviços

Para executar os microsserviços individualmente, navegue até o diretório de cada um e execute o JAR correspondente:

### Exemplo: Executando a Api-Filmes
```bash
cd api-filmes/target
java -jar api-filmes.jar
```

### Executando os demais microsserviços
Repita o mesmo comando para os outros microsserviços:

```bash
cd ../api-avaliacao/target
java -jar api-avaliacao.jar

cd ../api-usuario/target
java -jar api-usuario.jar

cd ../api-recomendacao/target
java -jar api-recomendacao.jar
```

## Gateway

O **gateway** é responsável por unificar as rotas de acesso aos microsserviços. Para executá-lo:

```bash
cd api-gateway/target
java -jar api-gateway.jar
```

Por padrão, o gateway estará acessível em **http://localhost:8080**.

## Testando o Projeto

Os testes são realizados utilizando **hurl**, e os arquivos de teste estão localizados na pasta `hurl`.

### Executando os Testes

1. Certifique-se de que o **hurl** está instalado em sua máquina.
   - Para instalação, consulte a documentação oficial: [https://hurl.dev](https://hurl.dev)

2. Execute os testes utilizando o seguinte comando:
   ```bash
   hurl hurl
   ```

   Exemplo:
   ```bash
   hurl hurl/01_post_filmes.hurl
   ```

3. Os arquivos de teste validam endpoints de cada microsserviço conforme suas funcionalidades descritas.

## Observações:

- Este README ainda está em construção e será atualizado com mais informações e detalhes sobre o projeto.

  <img src="https://github.com/mrmutthy/Microservicos-Filme/blob/main/diag.png" alt="Diagrama do Projeto">
