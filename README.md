# SistemaRH-Empresa-Funcionarios
Arquivo README

Desenvolvido por Gustavo Oliveira Tessaro.

Sistema RH - Cadastro de Empresas e Funcionarios.

**Funcionalidade e Para que Serve?**

O sistema de gerenciamento permite a administração de usuários, empresas e funcionários de forma integrada e eficiente. As principais funcionalidades são:

- Cadastro de Usuários: Permite o registro de usuários no sistema com nome e senha, facilitando o controle de acesso.
- Cadastro de Empresas: Facilita o gerenciamento das empresas, incluindo o registro de informações como nome, CNPJ e vinculação ao usuário responsável.
- Cadastro de Funcionários: Permite o registro dos funcionários das empresas, incluindo detalhes pessoais, cargo, salário e benefícios.
  
Estrutura do Banco de Dados
O sistema utiliza um banco de dados MySQL com as seguintes tabelas:

**Tabela Usuario:**

- id: Identificador único do usuário (AUTO_INCREMENT).
- nome: Nome do usuário (VARCHAR(100)).
- senha: Senha do usuário (VARCHAR(100)).

**Tabela Empresa:**

- id: Identificador único da empresa (AUTO_INCREMENT).
- nome: Nome da empresa (VARCHAR(100)).
- cnpj: CNPJ da empresa (VARCHAR(18)).
- usuario_id: Chave estrangeira referenciando a tabela Usuario (INT).
  
**Tabela Funcionarios:**

- id: Identificador único do funcionário (AUTO_INCREMENT).
- nome: Nome do funcionário (VARCHAR(100)).
- data_nascimento: Data de nascimento do funcionário (DATE).
- cpf: CPF do funcionário (VARCHAR(14)).
- cargo: Cargo do funcionário (VARCHAR(50)).
- salario: Salário do funcionário (DECIMAL(10, 2)).
- beneficios: Benefícios do funcionário (TEXT).
- empresa_id: Chave estrangeira referenciando a tabela Empresa (INT).
  
**Como Instalar**
- Instale o MySQL (caso ainda não tenha).

- Crie o banco de dados e as tabelas com o seguinte script:

```sql
CREATE DATABASE IF NOT EXISTS BancoDeDadosDoFrontEnd;

USE BancoDeDadosDoFrontEnd;

-- Criar a tabela Usuario
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

-- Criar a tabela Empresa
CREATE TABLE Empresa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) NOT NULL,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

-- Criar a tabela Funcionarios
CREATE TABLE Funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL,
    beneficios TEXT,
    empresa_id INT,
    FOREIGN KEY (empresa_id) REFERENCES Empresa(id) ON DELETE CASCADE
);
```

**Configure o Programa:**

- Abra o programa e ajuste as configurações de conexão com o banco de dados, substituindo as strings "url", "usuário" e "senha" pelos correspondentes ao seu ambiente de banco de dados. Caso não tenha senha, utilize "" para representar que está vazio.

**Funcionalidades Implementadas**
Cadastro de Usuários, Empresas e Funcionários:

- Registre e gerencie usuários, empresas e funcionários com todas as informações necessárias.
  
**Relacionamentos entre Tabelas:**

- As tabelas Empresa e Funcionarios estão associadas com chaves estrangeiras, garantindo integridade referencial.
  
Este sistema simplifica a administração de dados empresariais e de funcionários, ajudando na organização e controle das informações.
