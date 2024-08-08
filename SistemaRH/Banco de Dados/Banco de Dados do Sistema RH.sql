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