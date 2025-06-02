
-- Inserir uma empresa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo, email, nome_senha, whatsapp)
VALUES ('Tauá Lanches', '12.345.678/0001-00', 'Rua das Acácias, 123', '2023-01-01', true, 'yasmim.silva510811@gmail.com', 'taua', '+5511942780654');

-- Inserir outra empresa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo, email, nome_senha, whatsapp)
VALUES ('Doces da Tai', '98.765.432/0001-01', 'Avenida Central, 456', '2022-06-15', true, 'tai@gmail.com', 'tai', '');

-- Inserir uma empresa inativa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo,  email, nome_senha, whatsapp)
VALUES ('Ygonna Bolos', '11.222.333/0001-02', 'Rua Secundária, 789', '2023-03-10', false, 'maria@gmail.com', 'maria', '');

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
INSERT INTO setor (nome, funcionario_id) VALUES ('Estoque de Alimentos', 1);
INSERT INTO setor (nome, funcionario_id) VALUES ('Atendimento ao Cliente', 1);
INSERT INTO setor (nome, funcionario_id) VALUES ('Delivery', 1);
INSERT INTO setor (nome, funcionario_id) VALUES ('Higienização', 1);

-- Categorias de produtos/ingredientes típicos do ramo alimentício
INSERT INTO categoria (nome, funcionario_id) VALUES ('Carnes e Proteínas', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Massas e Pizzas', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Bebidas', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Sobremesas', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Frituras e Salgados', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Ingredientes Básicos', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Descartáveis e Embalagens', 1);
INSERT INTO categoria (nome, funcionario_id) VALUES ('Produtos de Limpeza', 1);


-- PRODUTOS
INSERT INTO produto (
    codigo,
    imagem,
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
(1001, 'https://i9techblobstorage.blob.core.windows.net/i9-storage/arroz.png', '2025-04-11', 1, 50, 100, 80, 15.90, 22.00, 1, 'Arroz tipo 1 de ótima qualidade', 'Arroz Branco Tipo 1', 1),
(1002, 'https://i9techblobstorage.blob.core.windows.net/i9-storage/feijao-carioca.png', '2025-04-11', 1, 30, 20, 5, 8.50, 12.00, 2, 'Feijão carioca grão selecionado', 'Feijão Carioca', 1),
(1003, 'https://i9techblobstorage.blob.core.windows.net/i9-storage/detergente.png', '2025-04-11', 1, 80, 50, 20, 1.20, 2.50, 3, 'Detergente para limpeza geral', 'Detergente Neutro', 1),
(1004, 'https://i9techblobstorage.blob.core.windows.net/i9-storage/agua-mineral.png', '2025-04-11', 1, 200, 300, 50, 0.90, 1.50, 1, 'Água mineral sem gás', 'Água Mineral 500ml', 1),
(1005, 'https://i9techblobstorage.blob.core.windows.net/i9-storage/papel-toalha.png', '2025-04-11', 1, 40, 70, 10, 2.80, 4.50, 2, 'Rolo de papel toalha absorvente', 'Papel Toalha', 1);

INSERT INTO produto (codigo, imagem, data_registro, funcionario_id, quantidade, quantidade_max, quantidade_min, valor_compra, valor_unitario, categoria_id, descricao, nome, setor_id)
VALUES
(1009, '', CURRENT_DATE, 1, 50, 80, 20, 3.00, 4.50, 1, 'Pacote de açúcar 1kg', 'Açúcar', 1),
(1010, '', CURRENT_DATE, 1, 35, 60, 15, 4.00, 6.00, 1, 'Café torrado e moído 500g', 'Café', 1),
(1011, '', CURRENT_DATE, 1, 25, 40, 10, 2.50, 3.50, 1, 'Sal refinado 1kg', 'Sal', 1),
(1012, '', CURRENT_DATE, 1, 60, 100, 25, 2.00, 3.00, 3, 'Refrigerante 2L', 'Refri 2L', 1),
(1013, '', CURRENT_DATE, 1, 80, 120, 30, 1.00, 1.50, 3, 'Balas sortidas 100g', 'Balas', 1),
(1014, '', CURRENT_DATE, 1, 45, 70, 15, 3.50, 5.00, 5, 'Pacote de biscoito salgado', 'Biscoito Salgado', 1),
(1015, '', CURRENT_DATE, 1, 30, 60, 10, 5.00, 7.50, 5, 'Pacote de farinha de trigo 1kg', 'Farinha de Trigo', 1),
(1016, '', CURRENT_DATE, 1, 70, 90, 20, 1.20, 2.00, 5, 'Pacote de macarrão 500g', 'Macarrão', 1),
(1017, '', CURRENT_DATE, 1, 55, 75, 25, 2.80, 4.00, 5, 'Molho de tomate 340g', 'Molho de Tomate', 1),
(1018, '', CURRENT_DATE, 1, 40, 60, 20, 6.00, 9.00, 1, 'Óleo de soja 900ml', 'Óleo de Soja', 1);


-- PRATOS
INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)

VALUES ('Hambúrguer Artesanal', 'https://i9techblobstorage.blob.core.windows.net/i9-storage/hamburguer-artesanal.png', 35.50, 'Pão artesanal, carne angus, queijo cheddar.', true, 1, 1, 1);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Pizza Margherita', 'https://i9techblobstorage.blob.core.windows.net/i9-storage/pizza-margherita.png', 42.00, 'Mussarela de búfala, tomate e manjericão.', true, 1, 2, 2);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Cerveja Artesanal', 'https://i9techblobstorage.blob.core.windows.net/i9-storage/cerveja-artesanal.png', 18.00, 'Cerveja puro malte artesanal.', true, 1, 3, 3);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Salada Caesar', 'https://i9techblobstorage.blob.core.windows.net/i9-storage/salada-caesar.png', 27.50, 'Alface, parmesão, frango grelhado e croutons.', true, 1, 1, 4);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES ('Brownie de Chocolate', 'https://i9techblobstorage.blob.core.windows.net/i9-storage/brownie.png', 15.00, 'Brownie de chocolate com sorvete de creme.', true, 1, 2, 5);

INSERT INTO prato (nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id)
VALUES
('Risoto de Cogumelos', '', 32.00, 'Risoto cremoso com mix de cogumelos.', true, 1, 2, 1),
('Strogonoff de Frango', '', 30.00, 'Strogonoff acompanhado de arroz e batata palha.', true, 1, 1, 2),
('Macarrão ao Alho e Óleo', '', 22.00, 'Espaguete simples e saboroso.', true, 1, 2, 2),
('Carne Assada com Purê', '', 35.00, 'Carne bovina assada com purê de batata.', true, 1, 1, 1),
('Feijoada Completa', '', 38.00, 'Feijoada com acompanhamentos.', true, 1, 1, 1),
('Moqueca de Peixe', '', 42.00, 'Peixe ao molho com leite de coco.', true, 1, 2, 1),
('Tábua de Frios', '', 28.00, 'Variedade de queijos e embutidos.', true, 1, 3, 3),
('Brownie com Sorvete', '', 16.00, 'Sobremesa quente e gelada.', true, 1, 3, 4),
('Panqueca de Carne', '', 25.00, 'Panquecas recheadas com carne moída.', true, 1, 2, 2),
('Quiche de Alho Poró', '', 20.00, 'Torta salgada de alho poró e queijo.', true, 1, 3, 4);


-- ITENS CARRINHO
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 1, 1, 22); -- Produto 1 (Arroz Branco Tipo 1)

-- Inserir Item Carrinho com Produto
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 2, 1, 12); -- Produto 2 (Feijão Carioca)

-- Inserir Item Carrinho com Produto
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 3, 1, 2.5); -- Produto 3 (Detergente Neutro)

-- Inserir Item Carrinho com Produto
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 4, 1, 1.5); -- Produto 4 (Água Mineral 500ml)

-- Inserir Item Carrinho com Produto
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario)
VALUES ('venda2', 5, 1, 4.5); -- Produto 5 (Papel Toalha)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 1, 1, 'Sem cebola', 35.50); -- Prato 1 (Hambúrguer Artesanal)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 2, 1, 'Com extra queijo', 42.00); -- Prato 2 (Pizza Margherita)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 3, 1, 'Sem gelo', 18.00); -- Prato 3 (Cerveja Artesanal)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 4, 1, 'Sem croutons', 27.50); -- Prato 4 (Salada Caesar)

-- Inserir Item Carrinho com Prato
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario)
VALUES ('venda2', 5, 1, 'Com sorvete de morango', 15.00); -- Prato 5 (Brownie de Chocolate)

-- ITENS CARRINHO (PRODUTOS)
INSERT INTO item_carrinho (venda, produto_id, funcionario_id, valor_unitario) VALUES
('venda2', 1, 1, 22.00),      -- Produto 1 (Arroz Branco Tipo 1, id=1)
('venda2', 2, 1, 12.00),      -- Produto 2 (Feijão Carioca, id=2)
('venda2', 3, 1, 2.50),       -- Produto 3 (Detergente Neutro, id=3)
('venda2', 4, 1, 1.50),       -- Produto 4 (Água Mineral 500ml, id=4)
('venda2', 5, 1, 4.50),       -- Produto 5 (Papel Toalha, id=5)
('venda2', 6, 1, 4.50),       -- Açúcar (id=6, corresponde ao código 1009)
('venda2', 7, 1, 6.00),       -- Café (id=7, corresponde ao código 1010)
('venda2', 10, 1, 1.50),      -- Balas (id=10, corresponde ao código 1013)
('venda2', 14, 1, 4.00),      -- Molho de Tomate (id=14, corresponde ao código 1017)
('venda2', 1, 1, 22.00),      -- Arroz Branco (id=1, corresponde ao código 1001)
('venda2', 15, 1, 9.00),      -- Óleo de Soja (id=15, corresponde ao código 1018)
('venda2', 8, 1, 3.50),       -- Sal refinado (id=8, corresponde ao código 1011)
('venda2', 13, 1, 2.00),      -- Macarrão (id=13, corresponde ao código 1016)
('venda2', 9, 1, 3.00),       -- Refrigerante 2L (id=9, corresponde ao código 1012)
('venda2', 11, 1, 5.00),      -- Biscoito Salgado (id=11, corresponde ao código 1014)
('venda2', 5, 1, 4.50),       -- Papel Toalha (id=5, corresponde ao código 1005)
('venda2', 12, 1, 7.50),      -- Farinha de Trigo (id=12, corresponde ao código 1015)
('venda2', 2, 1, 12.00),      -- Feijão Carioca (id=2, corresponde ao código 1002)
('venda2', 3, 1, 2.50),       -- Detergente Neutro (id=3, corresponde ao código 1003)
('venda2', 4, 1, 1.50);       -- Água Mineral 500ml (id=4, corresponde ao código 1004)

-- ITENS CARRINHO (PRATOS)
INSERT INTO item_carrinho (venda, prato_id, funcionario_id, observacao, valor_unitario) VALUES
('venda2', 1, 1, 'Sem cebola', 35.50),        -- Prato 1 (Hambúrguer Artesanal)
('venda2', 2, 1, 'Com extra queijo', 42.00),  -- Prato 2 (Pizza Margherita)
('venda2', 3, 1, 'Sem gelo', 18.00),          -- Prato 3 (Cerveja Artesanal)
('venda2', 4, 1, 'Sem croutons', 27.50),      -- Prato 4 (Salada Caesar)
('venda2', 5, 1, 'Com sorvete de morango', 15.00), -- Prato 5 (Brownie de Chocolate)
('venda2', 6, 1, NULL, 32.00),                -- Risoto de Cogumelos (Assumindo func_id 1)
('venda2', 10, 1, NULL, 25.00),               -- Panqueca de Carne (Assumindo func_id 1)
('venda2', 9, 1, NULL, 16.00),                -- Brownie com Sorvete (Assumindo func_id 1)
('venda2', 7, 1, NULL, 30.00),                -- Strogonoff de Frango (Assumindo func_id 1)
('venda2', 8, 1, NULL, 35.00),                -- Carne Assada com Purê (Assumindo func_id 1)
('venda2', 6, 1, NULL, 42.00),                -- Moqueca de Peixe (Assumindo func_id 1)
('venda2', 8, 1, NULL, 22.00),                -- Macarrão ao Alho e Óleo (Assumindo func_id 1)
('venda2', 5, 1, NULL, 15.00),                -- Brownie de Chocolate (Assumindo func_id 1)
('venda2', 10, 1, NULL, 38.00);               -- Feijoada Completa (Assumindo func_id 1)



