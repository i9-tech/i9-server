INSERT INTO funcionario (
  nome,
  cpf,
  cargo,
  data_admissao,
  acesso_setor_cozinha,
  acesso_setor_estoque,
  acesso_setor_atendimento,
  proprietario,
  fk_empresa,
  senha
) VALUES
('Ana Souza', '12345678901', 'Atendente', '2023-01-10', true, false, true, false, 1, '1@12345678901'),
('Carlos Lima', '98765432100', 'Estoquista', '2022-08-15', false, true, false, false, 1, '1@98765432100'),
('Mariana Alves', '45612378900', 'Cozinheira', '2021-03-20', true, false, false, false, 1, '1@45612378900'),
('João Pedro', '32165498700', 'Gerente', '2020-11-01', true, true, true, true, 1, '1@32165498700'),
('Fernanda Costa', '15935748620', 'Caixa', '2023-06-05', false, false, true, false, 1, '1@15935748620'),
('NAO FOOII INTRUSO', '15935748620', 'Caixa', '2023-06-05', false, false, true, false, 25, '1@15935748620');

INSERT INTO produto (
  codigo,
  data_registro,
  data_vencimento,
  funcionario_id,
  quantidade,
  quantidade_max,
  quantidade_min,
  valor_compra,
  valor_unitario,
  categoria,
  descricao,
  nome,
  setor
) VALUES
(1001, '2025-04-11', '2025-12-31', 1, 50, 100, 80, 15.90, 22.00, 'Alimento', 'Arroz tipo 1 de ótima qualidade', 'Arroz Branco Tipo 1', 'Cozinha'),
(1002, '2025-04-11', '2025-11-15', 1, 30, 20, 5, 8.50, 12.00, 'Alimento', 'Feijão carioca grão selecionado', 'Feijão Carioca', 'Cozinha'),
(1003, '2025-04-11', '2026-01-01', 2, 80, 50, 20, 1.20, 2.50, 'Limpeza', 'Detergente para limpeza geral', 'Detergente Neutro', 'Estoque'),
(1004, '2025-04-11', '2025-06-30', 2, 200, 300, 50, 0.90, 1.50, 'Bebida', 'Água mineral sem gás', 'Água Mineral 500ml', 'Atendimento'),
(1005, '2025-04-11', '2026-12-31', 5, 40, 70, 10, 2.80, 4.50, 'Limpeza', 'Rolo de papel toalha absorvente', 'Papel Toalha', 'Estoque');
