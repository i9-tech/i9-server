
-- Inserir uma empresa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo, whatsapp)
VALUES ('Tauá Lanches', '12.345.678/0001-00', 'Rua das Acácias, 123', '2023-01-01', true, '+5511942780654');

-- Inserir outra empresa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo, whatsapp)
VALUES ('Doces da Tai', '98.765.432/0001-01', 'Avenida Central, 456', '2022-06-15', true, '+5511970533898');

-- Inserir uma empresa inativa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo, whatsapp)
VALUES ('Ygonna Bolos', '11.222.333/0001-02', 'Rua Secundária, 789', '2023-03-10', false, '+5511967232879');


-- Inserir um funcionário
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
VALUES ('João Silva', '123.456.789-00', 'Gerente', '2023-05-15', true, true, true, true, true, '$2a$10$G2/SarKppek1QTyQ0fw1Le.DwFHuw5st2bai9T2edfysLey2BwmYC', 1);
                                                                                                -- senha: 12345678900@taua

-- Inserir outro funcionário
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
VALUES ('Maria Oliveira', '987.654.321-11', 'Atendente', '2023-08-20', false, false, true, false, true, '$2a$10$.6TQeLGsSZQaw4sgI3lwrezCIDmYAfPaOSwIVjrIBhIxassLU4bW2', 1);
                                                                                                         -- senha: 98765432111@taua

-- Inserir um funcionário com acesso ao estoque
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
VALUES ('Pedro Santos', '111.222.333-44', 'Estoquista', '2022-12-10', false, true, false, false, true, '$2a$10$P1Tas.U/mjNwN15QG704I.4fr2Udv55gZcLM8HIdz18VL7EDqYquS', 3);
                                                                                                        -- senha: 11122233344@tai

-- Inserir um funcionário sem acesso a nenhum setor
INSERT INTO funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
VALUES ('Ana Paula', '555.666.777-88', 'Auxiliar', '2024-01-05', false, false, false, false, true, '$2a$10$xUpX3.bXqE5RtOuSZtNYZ.3BJdn9ZWfQUgq9INkqvq9g2cvNDwI0G', 2);
                                                                                                    -- senha: 55566677788@tai

-- Setores para restaurante/lanchonete vinculados aos funcionários
INSERT INTO setor (nome, funcionario_id) VALUES ('Cozinha Quente', 1);
INSERT INTO setor (nome, funcionario_id) VALUES ('Cozinha Fria', 1);
INSERT INTO setor (nome, funcionario_id) VALUES ('Estoque de Alimentos', 3);
INSERT INTO setor (nome, funcionario_id) VALUES ('Atendimento ao Cliente', 2);
INSERT INTO setor (nome, funcionario_id) VALUES ('Delivery', 2);
INSERT INTO setor (nome, funcionario_id) VALUES ('Higienização', 4);

-- Categorias de produtos/ingredientes típicos do ramo alimentício
INSERT INTO categoria (nome, funcionario_id) VALUES ('Carnes e Proteínas', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Massas e Pizzas', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Bebidas', 2);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Sobremesas', 2);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Frituras e Salgados', 3);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Ingredientes Básicos', 3);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Descartáveis e Embalagens', 4);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Produtos de Limpeza', 4);


-- PRODUTOS
INSERT INTO produto (
    codigo,
    data_registro,
    funcionario_id,
    quantidade,
    quantidade_max,
    quantidade_min,
    valor_compra,
    valor_unitario,
    categoria_id,
    descricao,
    nome,
    setor_id
) VALUES
(1001, '2025-04-11', 1, 50, 100, 80, 15.90, 22.00, 1, 'Arroz tipo 1 de ótima qualidade', 'Arroz Branco Tipo 1', 1),
(1002, '2025-04-11', 1, 30, 20, 5, 8.50, 12.00, 2, 'Feijão carioca grão selecionado', 'Feijão Carioca', 1),
(1003, '2025-04-11', 3, 80, 50, 20, 1.20, 2.50, 3, 'Detergente para limpeza geral', 'Detergente Neutro', 2),
(1004, '2025-04-11', 3, 200, 300, 50, 0.90, 1.50, 1, 'Água mineral sem gás', 'Água Mineral 500ml', 3),
(1005, '2025-04-11', 4, 40, 70, 10, 2.80, 4.50, 2, 'Rolo de papel toalha absorvente', 'Papel Toalha', 1);

-- PRATOS
INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Hambúrguer Artesanal', 'imagens/hamburguer.jpg', 35.50, 'Pão artesanal, carne angus, queijo cheddar.', true, 1, 1, 1);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Pizza Margherita', 'imagens/pizza_margherita.jpg', 42.00, 'Mussarela de búfala, tomate e manjericão.', true, 2, 2, 2);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Cerveja Artesanal', 'imagens/cerveja.jpg', 18.00, 'Cerveja puro malte artesanal.', true, 3, 3, 3);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Salada Caesar', 'imagens/salada_caesar.jpg', 27.50, 'Alface, parmesão, frango grelhado e croutons.', true, 1, 1, 4);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Brownie de Chocolate', 'imagens/brownie.jpg', 15.00, 'Brownie de chocolate com sorvete de creme.', true, 2, 2, 5);


-- ITENS CARRINHO
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 1, 1, 22); -- Produto 1 (Arroz Branco Tipo 1)

-- Inserir Item Carrinho com Produto
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 2, 1, 12); -- Produto 2 (Feijão Carioca)

-- Inserir Item Carrinho com Produto
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 3, 2, 2.5); -- Produto 3 (Detergente Neutro)

-- Inserir Item Carrinho com Produto
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 4, 3, 1.5); -- Produto 4 (Água Mineral 500ml)

-- Inserir Item Carrinho com Produto
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 5, 4, 4.5); -- Produto 5 (Papel Toalha)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 1, 1, 'Sem cebola', 35.50); -- Prato 1 (Hambúrguer Artesanal)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 2, 1, 'Com extra queijo', 42.00); -- Prato 2 (Pizza Margherita)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 3, 2, 'Sem gelo', 18.00); -- Prato 3 (Cerveja Artesanal)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 4, 3, 'Sem croutons', 27.50); -- Prato 4 (Salada Caesar)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 5, 4, 'Com sorvete de morango', 15.00); -- Prato 5 (Brownie de Chocolate)
