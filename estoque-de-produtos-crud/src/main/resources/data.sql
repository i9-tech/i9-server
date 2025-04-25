
-- Inserir uma empresa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo)
VALUES ('Empresa Exemplo', '12.345.678/0001-00', 'Rua das Acácias, 123', '2023-01-01', true);

-- Inserir outra empresa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo)
VALUES ('Outra Empresa', '98.765.432/0001-01', 'Avenida Central, 456', '2022-06-15', true);

-- Inserir uma empresa inativa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo)
VALUES ('Empresa Inativa', '11.222.333/0001-02', 'Rua Secundária, 789', '2023-03-10', false);


-- Inserir um funcionário
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
VALUES ('João Silva', '123.456.789-00', 'Gerente', '2023-05-15', true, true, true, true, true, 'senha123', 1);

-- Inserir outro funcionário
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
VALUES ('Maria Oliveira', '987.654.321-11', 'Atendente', '2023-08-20', false, false, true, false, true, 'senha456', 1);

-- Inserir um funcionário com acesso ao estoque
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
VALUES ('Pedro Santos', '111.222.333-44', 'Estoquista', '2022-12-10', false, true, false, false, true, 'senha789', 2);

-- Inserir um funcionário sem acesso a nenhum setor
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
VALUES ('Ana Paula', '555.666.777-88', 'Auxiliar', '2024-01-05', false, false, false, false, true, 'senha012', 2);
