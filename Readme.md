# 📝 Sistema TO-DO List — Java CLI

<div align="center">

**Gerenciador de tarefas com Java, MySQL, JDBC e segurança de dados.**

Uma aplicação de linha de comando desenvolvida para demonstrar conceitos de programação Java, persistência de dados relacionais, organização em camadas e recursos programáveis do MySQL.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![JDBC](https://img.shields.io/badge/JDBC-Database%20Access-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Concluído-success?style=for-the-badge)

</div>

---

## 📌 Índice

- [Sobre o projeto](#-sobre-o-projeto)
- [Objetivos acadêmicos](#-objetivos-acadêmicos)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do sistema](#-arquitetura-do-sistema)
- [Modelagem do banco de dados](#️-modelagem-do-banco-de-dados)
- [Segurança e criptografia](#-segurança-e-criptografia)
- [Recursos avançados do MySQL](#-recursos-avançados-do-mysql)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e configuração](#️-instalação-e-configuração)
- [Como executar](#️-como-executar)
- [Exemplo de utilização](#-exemplo-de-utilização)
- [Possíveis melhorias](#-possíveis-melhorias)
- [Desenvolvedor](#-desenvolvedor)

---

## 📖 Sobre o projeto

O **Sistema TO-DO List** é uma aplicação de linha de comando (*Command Line Interface — CLI*) desenvolvida como avaliação final da disciplina de Banco de Dados.

O sistema permite cadastrar, consultar, atualizar e excluir tarefas e categorias, mantendo os registros armazenados em um banco de dados relacional MySQL.

Além das operações convencionais de gerenciamento de tarefas, o projeto utiliza recursos que demonstram a integração entre Java e banco de dados, incluindo o padrão de projeto DAO, consultas SQL, uma View, uma Stored Procedure e criptografia das descrições das tarefas.

A aplicação foi desenvolvida com foco em organização, persistência de dados, integridade referencial e separação de responsabilidades.

### 🎯 Objetivo principal

Desenvolver uma aplicação funcional que integre conceitos de programação orientada a objetos, acesso a dados por JDBC, modelagem relacional e mecanismos de proteção de informações.

## 🎓 Objetivos acadêmicos

O desenvolvimento do projeto permite aplicar, na prática, os seguintes conceitos:

- **Modelagem relacional:** organização de entidades, atributos, chaves primárias e estrangeiras.
- **SQL:** operações de inserção, consulta, atualização e exclusão de registros.
- **JDBC:** comunicação entre a aplicação Java e o MySQL.
- **CRUD:** implementação das operações fundamentais de persistência.
- **POO:** representação de tarefas e categorias por meio de classes.
- **DAO:** separação entre regras de aplicação e acesso aos dados.
- **View e Stored Procedure:** utilização de objetos programáveis do banco de dados.
- **Criptografia:** proteção das descrições das tarefas por meio de AES/GCM.
- **Integridade dos dados:** utilização de restrições e relacionamentos no banco relacional.

---

## ✨ Funcionalidades

### 📋 Gerenciamento de tarefas

- Cadastro de tarefas com título, descrição e categoria.
- Listagem de tarefas armazenadas no banco de dados.
- Edição de título e descrição.
- Exclusão de tarefas.
- Consulta de tarefas por status.
- Registro de datas de criação, atualização e conclusão, conforme as regras implementadas no banco de dados.

### 🗂️ Gerenciamento de categorias

- Cadastro de categorias.
- Listagem das categorias disponíveis.
- Edição de nome e descrição.
- Exclusão de categorias, respeitando as regras de integridade referencial definidas no banco de dados.

### ⚙️ Controle de status

As tarefas podem assumir os seguintes estados:

| Status | Significado |
|---|---|
| `NOVA` | Tarefa recém-cadastrada. |
| `EM_ANDAMENTO` | Tarefa em execução. |
| `PENDENTE` | Tarefa que permanece aguardando alguma ação. |
| `CONCLUIDA` | Tarefa finalizada. |

### 📊 Estatísticas

O sistema disponibiliza um resumo quantitativo das tarefas, incluindo totais, tarefas abertas e tarefas concluídas, utilizando uma Stored Procedure do MySQL.

### 🔐 Proteção de informações

As descrições das tarefas são criptografadas antes de serem armazenadas no banco de dados. O conteúdo é descriptografado pela aplicação quando necessário para sua exibição.

---

## 🛠️ Tecnologias utilizadas

| Tecnologia | Aplicação no projeto |
|---|---|
| Java 17 | Linguagem de programação. |
| MySQL 8.0 | Banco de dados relacional. |
| JDBC | Comunicação entre Java e MySQL. |
| MySQL Connector/J | Driver JDBC para o MySQL. |
| Maven | Gerenciamento de dependências e build. |
| AES/GCM | Criptografia autenticada das descrições. |
| Base64 | Representação textual dos dados criptografados. |
| DAO | Organização das operações de acesso aos dados. |
| SQL | Consultas, alterações e objetos programáveis do banco. |

---

## 🏗️ Arquitetura do sistema

A aplicação organiza as responsabilidades em classes específicas, evitando concentrar toda a lógica em um único arquivo.

### Fluxo de funcionamento

```text
┌───────────────────────────────┐
│          Usuário              │
└──────────────┬────────────────┘
               │
               ▼
┌───────────────────────────────┐
│            Main.java          │
│       Interface de CLI        │
└──────────────┬────────────────┘
               │
               ▼
┌───────────────────────────────┐
│        Classes DAO            │
│ TarefaDAO / CategoriaDAO      │
└──────────────┬────────────────┘
               │ JDBC
               ▼
┌───────────────────────────────┐
│           MySQL 8.0           │
│ Tabelas, View e Procedure     │
└───────────────────────────────┘
```

A classe `Conexao.java` é responsável por centralizar o estabelecimento da conexão com o banco de dados. A classe `CriptoUtil.java` auxilia no processo de criptografia e descriptografia das descrições.

### Padrão DAO — Data Access Object

O padrão DAO encapsula as operações de persistência, mantendo os comandos SQL separados da interface de console.

As principais vantagens dessa abordagem são:

- Melhor separação de responsabilidades.
- Redução do acoplamento entre interface e persistência.
- Facilidade de manutenção das consultas SQL.
- Maior organização e legibilidade do código.

---

## 🗄️ Modelagem do banco de dados

O banco de dados utiliza duas entidades principais: `categoria` e `tarefa`.

### Dicionário de dados

#### Tabela `categoria`

| Coluna | Tipo | Restrição | Descrição |
|---|---|---|---|
| `id` | INT | PK, AUTO_INCREMENT | Identificador único. |
| `nome` | VARCHAR(50) | NOT NULL, UNIQUE | Nome da categoria. |
| `descricao` | VARCHAR(255) | NULL | Descrição da categoria. |
| `criada_em` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Data de criação. |

#### Tabela `tarefa`

| Coluna | Tipo | Restrição | Descrição |
|---|---|---|---|
| `id` | INT | PK, AUTO_INCREMENT | Identificador único. |
| `titulo` | VARCHAR(100) | NOT NULL | Título da tarefa. |
| `descricao_cifrada` | TEXT | NULL | Descrição criptografada. |
| `status` | VARCHAR(20) | Conforme definição do schema | Estado atual da tarefa. |
| `categoria_id` | INT | FK | Referência à categoria. |
| `criada_em` | DATETIME | DEFAULT CURRENT_TIMESTAMP | Data de criação. |
| `atualizada_em` | DATETIME | ON UPDATE CURRENT_TIMESTAMP | Data da última atualização. |
| `concluida_em` | DATETIME | NULL | Data de conclusão. |

*Observação: os tipos e as restrições acima representam a modelagem documentada do projeto. Para garantir correspondência exata, consulte o script SQL efetivamente utilizado na criação do banco.*

### 🔗 Relacionamento entre entidades

O relacionamento é do tipo **1:N (um para muitos)**:

- Uma categoria pode estar associada a várias tarefas.
- Cada tarefa referencia uma categoria por meio da coluna `categoria_id`.

### Diagrama Entidade-Relacionamento (DER)

![Diagrama Entidade-Relacionamento do banco de dados](Diagrama%20ER.png)

A chave estrangeira permite manter a integridade referencial entre as entidades, de acordo com as regras de exclusão e atualização definidas no banco de dados.

---

## 🔒 Segurança e criptografia

O projeto utiliza mecanismos de validação e criptografia para reduzir a exposição de informações armazenadas.

### 1. Validação de entradas

O método `lerTexto()`, localizado em `Main.java`, impede que determinados campos obrigatórios sejam preenchidos com valores vazios ou somente espaços em branco.

Essa validação ajuda a evitar cadastros incompletos por meio da interface de console.

### 2. Criptografia AES/GCM

A classe `CriptoUtil.java` utiliza o algoritmo AES no modo GCM, com preenchimento `NoPadding`, conforme a implementação documentada do projeto.

O fluxo de processamento é:

1. O usuário informa a descrição da tarefa.
2. A aplicação executa a criptografia da descrição.
3. O resultado criptografado é representado em Base64.
4. O conteúdo é armazenado na coluna `descricao_cifrada`.
5. Quando necessário, a aplicação recupera e descriptografa o conteúdo para exibição.

**Por que utilizar AES/GCM?**

- AES é um algoritmo de criptografia simétrica amplamente utilizado.
- GCM oferece confidencialidade e autenticação dos dados criptografados.
- A representação Base64 facilita o armazenamento do resultado binário em um campo textual.

**Importante:** Base64 não é criptografia; é apenas uma forma de representar dados. A proteção depende da criptografia AES/GCM e do gerenciamento correto da chave.

Em uma implantação real, a chave criptográfica deve ser protegida e armazenada separadamente dos dados cifrados. Também é necessário utilizar um nonce/IV único para cada operação de criptografia com a mesma chave e validar corretamente a autenticação na descriptografia.

A criptografia das descrições não substitui outras medidas, como controle de acesso ao banco, proteção das credenciais e uso de consultas parametrizadas.

---

## 🚀 Recursos avançados do MySQL

Além das operações CRUD, o projeto utiliza recursos programáveis do banco de dados.

| Recurso | Nome | Finalidade |
|---|---|---|
| View | `vw_tarefas_detalhadas` | Centraliza uma consulta que relaciona tarefas e categorias por meio de `INNER JOIN`. |
| Stored Procedure | `sp_estatisticas` | Executa consultas para calcular estatísticas das tarefas. |
| Chaves estrangeiras | `categoria_id` | Mantêm o relacionamento entre tarefas e categorias. |
| Restrições | Conforme o schema | Ajudam a preservar a consistência dos registros. |

### 👁️ View — `vw_tarefas_detalhadas`

A View reúne informações das tabelas `tarefa` e `categoria`, permitindo consultar dados relacionados por meio de uma única interface SQL.

Isso centraliza a consulta e simplifica o código que consome esses dados. O uso de uma View, por si só, não garante ganho de desempenho.

### ⚙️ Stored Procedure — `sp_estatisticas`

A procedure concentra a lógica SQL responsável por produzir as estatísticas das tarefas.

Na aplicação Java, o acesso é realizado por meio de `CallableStatement`, mecanismo JDBC utilizado para chamar procedimentos armazenados.

Essa organização permite manter parte da lógica de consulta no banco de dados e reutilizar o procedimento quando necessário.

---

## 📁 Estrutura do projeto

```text
todo-app/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── todo/
                    ├── Main.java
                    ├── Conexao.java
                    ├── Tarefa.java
                    ├── Categoria.java
                    ├── CriptoUtil.java
                    ├── TarefaDAO.java
                    ├── CategoriaDAO.java
                    └── EstatisticaService.java
```

### Responsabilidade das classes

| Classe | Responsabilidade |
|---|---|
| `Main.java` | Inicialização, menus e interação com o usuário. |
| `Conexao.java` | Gerenciamento da conexão JDBC. |
| `Tarefa.java` | Representação dos dados de uma tarefa. |
| `Categoria.java` | Representação dos dados de uma categoria. |
| `CriptoUtil.java` | Operações de criptografia e descriptografia. |
| `TarefaDAO.java` | Persistência e consultas relacionadas às tarefas. |
| `CategoriaDAO.java` | Persistência e consultas relacionadas às categorias. |
| `EstatisticaService.java` | Integração com a Stored Procedure de estatísticas. |

---

## 📋 Pré-requisitos

Antes de executar o projeto, verifique se o ambiente possui:

- [Java JDK 17](https://adoptium.net/) ou compatível com a versão definida no projeto.
- [MySQL 8.0](https://dev.mysql.com/downloads/mysql/) instalado e em execução.
- [Apache Maven](https://maven.apache.org/download.cgi) instalado.
- Um editor ou IDE Java, como IntelliJ IDEA ou Visual Studio Code.
- O código-fonte do projeto.

Para conferir as instalações, execute no terminal:

```bash
java -version
mvn -version
```

O servidor MySQL também precisa estar ativo e acessível com as credenciais utilizadas pela aplicação.

---

## ⚙️ Instalação e configuração

### 1. Obtenha o projeto

Clone o repositório:

```bash
git clone <URL_DO_REPOSITORIO>
cd todo-app
```

Substitua `<URL_DO_REPOSITORIO>` pelo endereço real do repositório.

Se o projeto já estiver baixado, abra o terminal na pasta que contém o arquivo `pom.xml`.

### 2. Configure o banco de dados

Acesse o MySQL com um usuário autorizado:

```bash
mysql -u <USUARIO_MYSQL> -p
```

Crie o banco de dados, caso ele ainda não exista:

```sql
CREATE DATABASE todo_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

Selecione o banco:

```sql
USE todo_db;
```

Em seguida, execute o script SQL do projeto que cria as tabelas, as chaves estrangeiras, a View e a Stored Procedure.

**Atenção:** o script de criação precisa ser executado antes da inicialização da aplicação. Os nomes das tabelas e dos objetos devem corresponder aos utilizados pelo código Java.

### 3. Configure a conexão JDBC

Ajuste as configurações de conexão utilizadas por `Conexao.java`, incluindo:

- Endereço do servidor MySQL.
- Porta de conexão.
- Nome do banco de dados.
- Usuário.
- Senha.

Uma URL JDBC para uma instalação local pode seguir este formato:

```text
jdbc:mysql://localhost:3306/todo_db
```

Utilize as credenciais do seu próprio ambiente.

**Boa prática:** não publique senhas, chaves criptográficas ou outras credenciais no repositório. Prefira variáveis de ambiente ou um mecanismo de configuração externo ao código-fonte.

### 4. Verifique as dependências

O Maven utiliza o arquivo `pom.xml` para gerenciar as dependências do projeto, incluindo o driver MySQL Connector/J.

Na pasta do projeto, execute:

```bash
mvn clean install
```

Esse comando limpa os arquivos de compilação anteriores, compila o projeto, executa os testes configurados e empacota o artefato quando aplicável.

Se o projeto não possuir testes automatizados, essa etapa não substitui os testes manuais de integração com o banco.

---

## ▶️ Como executar

### Opção 1 — Pela IDE

1. Abra o projeto no IntelliJ IDEA ou em outra IDE compatível.
2. Aguarde a importação das dependências do Maven.
3. Verifique a configuração da conexão MySQL.
4. Localize `Main.java`.
5. Execute o método `main()`.

### Opção 2 — Pelo Maven

Na pasta que contém o `pom.xml`, execute:

```bash
mvn clean compile
```

Depois, inicie a classe principal usando a configuração de execução da IDE ou o comando Maven Exec, caso o respectivo plugin esteja configurado no projeto.

Por exemplo, se o plugin estiver disponível:

```bash
mvn exec:java -Dexec.mainClass="com.todo.Main"
```

O comando pressupõe que `Main.java` esteja declarado no pacote `com.todo`, conforme a estrutura documentada, e que a configuração do plugin permita sua execução.

---

## 💻 Exemplo de utilização

Após iniciar a aplicação, o usuário interage com os menus disponíveis no console.

Um fluxo típico de utilização pode incluir:

1. Cadastrar uma categoria, como `Estudos`.
2. Criar uma tarefa, como `Concluir atividade de banco de dados`.
3. Informar a descrição e associar a tarefa à categoria.
4. Consultar as tarefas cadastradas.
5. Atualizar os dados ou o status de uma tarefa.
6. Marcar a tarefa como concluída.
7. Consultar as estatísticas.
8. Excluir registros quando necessário.

Os nomes e as opções efetivamente exibidos dependem da implementação dos menus em `Main.java`.

---

## 🧪 Verificações recomendadas

Para conferir o funcionamento da aplicação, recomenda-se testar:

- Cadastro e consulta de categorias.
- Cadastro, edição e exclusão de tarefas.
- Associação entre tarefas e categorias.
- Filtros de tarefas por status.
- Atualização das datas, conforme as regras do banco.
- Funcionamento da View e da Stored Procedure.
- Criptografia e descriptografia das descrições.
- Tratamento de entradas inválidas.
- Comportamento do sistema quando o MySQL estiver indisponível.
- Integridade dos registros após reiniciar a aplicação.

Essas verificações podem ser realizadas manualmente ou complementadas com testes automatizados.

---

## 🔮 Possíveis melhorias

Embora o projeto tenha sido desenvolvido para uma avaliação acadêmica, existem oportunidades de evolução:

- [ ] Adicionar testes unitários e de integração.
- [ ] Utilizar variáveis de ambiente para todas as configurações sensíveis.
- [ ] Implementar logs de execução e tratamento padronizado de erros.
- [ ] Criar documentação específica para a instalação do banco de dados.
- [ ] Adicionar datas de vencimento e prioridades às tarefas.
- [ ] Implementar pesquisa por título e categoria.
- [ ] Desenvolver uma interface gráfica ou uma API REST.
- [ ] Automatizar a compilação e os testes com integração contínua.

Esses itens representam sugestões futuras, não funcionalidades necessariamente presentes na versão atual.

---

## 👨‍💻 Desenvolvedor

Projeto desenvolvido como avaliação final da disciplina de Banco de Dados, com foco na aplicação prática de Java, MySQL, JDBC, modelagem relacional e segurança de informações.

**Guibson Wendell**

- [GitHub](https://github.com/Guibsonwendell)
- [LinkedIn](www.linkedin.com/in/guibsonwendell-dev)

---

<div align="center">

**Java ☕ + MySQL 🐬 + Organização 📋**

*Um projeto acadêmico voltado à prática de programação, banco de dados e desenvolvimento de software.*

</div>
