-- Inserir uma empresa
INSERT INTO empresa (nome, CNPJ, endereco, data_cadastro, ativo, email, nome_senha, whatsapp)
VALUES ('Tauá Lanches', '12.345.678/0001-00', 'Rua das Acácias, 123', '2023-01-01', true, 'nogueiramaykoon@gmail.com', 'taua', '+5511942780654');

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

-- Inserir uma empresa
insert into empresa (nome, CNPJ, endereco, data_cadastro, ativo, email, nome_senha, whatsapp)
values ('Tauá Lanches', '12.345.678/0001-00', 'Rua das Acácias, 123', '2023-01-01', true, 'yasmim.silva510@gmail.com', 'taua', '+5511942780654');


-- Inserir funcionário
insert into funcionario (nome, cpf, cargo, data_admissao, acesso_setor_cozinha, acesso_setor_estoque, acesso_setor_atendimento, proprietario, ativo, senha, empresa_id)
values ('João Silva', '123.456.789-00', 'Gerente', '2023-05-15', true, true, true, true, true, '$2a$10$G2/SarKppek1QTyQ0fw1Le.DwFHuw5st2bai9T2edfysLey2BwmYC', 1);

-- Inserir setor
insert into setor (nome, funcionario_id) values
('Pastelaria', 1),
('Mercado', 1),
('Restaurante', 1),
('Lanchonete', 1);


-- Inserir categoria
insert into categoria (nome, funcionario_id) values
('Pratos Principais', 1),
('Guarnições', 1),
('Pastéis Salgados', 1),
('Pastéis Doces', 1),
('Lanches', 1),
('Sucos Naturais', 1),
('Refrigerantes', 1),
('Cervejas', 1),
('Bebidas Alcoólicas', 1),
('Bebidas Naturais', 1);

-- Inserir prato

-- Pratos Principais (categoria_id = 1)
INSERT INTO prato (
  nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id, quantidade_vendida, total_vendas
) VALUES
('Baião De Dois Com Bisteca', '', 26.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Baião De Dois Com Bisteca. Uma refeição completa, saborosa e típica, com ingredientes selecionados que garantem um sabor caseiro e irresistível.', TRUE, 1, 3, 1, NULL, NULL),
('Filé De Frango Grelhado Com Salada', '', 18.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Filé De Frango Grelhado Com Salada. Ideal para quem busca leveza e saúde, sem abrir mão do sabor e da praticidade no dia a dia.', TRUE, 1, 3, 1, NULL, NULL),
('Fígado À Veneziana', '', 18.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Fígado À Veneziana. Um clássico para os apreciadores de sabores fortes, com cebolas caramelizadas e temperos marcantes.', TRUE, 1, 3, 1, NULL, NULL),
('Calabresa Acebolada', '', 18.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Calabresa Acebolada. Preparado com calabresa dourada e cebola refogada, é perfeito para quem gosta de refeições com personalidade.', TRUE, 1, 3, 1, NULL, NULL),
('Panqueca À Parmegiana Com Purê', '', 18.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Panqueca À Parmegiana Com Purê. Uma explosão de sabor com panqueca gratinada, molho especial e purê de batatas cremoso.', TRUE, 1, 3, 1, NULL, NULL),
('Bife À Cavalo Ou Acebolado', '', 22.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Bife À Cavalo Ou Acebolado. Tradicional e nutritivo, esse prato é uma ótima escolha para uma refeição robusta e saborosa.', TRUE, 1, 3, 1, NULL, NULL),
('Contra Filé Com Fritas', '', 28.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Contra Filé Com Fritas. Um prato suculento com carne no ponto e fritas crocantes, perfeito para qualquer ocasião.', TRUE, 1, 3, 1, NULL, NULL),
('Omelete Misto', '', 18.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Omelete Misto. Uma refeição leve e saborosa, com recheio de presunto e queijo, ideal para qualquer hora do dia.', TRUE, 1, 3, 1, NULL, NULL),
('À Parmegiana De Contra Filé', '', 32.00, 'Prato feito com uma porção de arroz, feijão e a mistura: À Parmegiana De Contra Filé. Um clássico empanado com molho especial e queijo derretido, ideal para paladares exigentes.', TRUE, 1, 3, 1, NULL, NULL),
('À Parmegiana De Frango', '', 32.00, 'Prato feito com uma porção de arroz, feijão e a mistura: À Parmegiana De Frango. Suculento e gratinado, esse prato conquista pela textura crocante e sabor do frango ao molho.', TRUE, 1, 3, 1, NULL, NULL),
('Picanha Grelhada Com Fritas', '', 39.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Picanha Grelhada Com Fritas. Carne macia e saborosa, combinada com fritas crocantes para uma refeição inesquecível.', TRUE, 1, 3, 1, NULL, NULL),
('Medalhão De Filé Mignon Grelhado Com Purê', '', 0.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Medalhão De Filé Mignon Grelhado Com Purê. Sofisticado e delicioso, é a pedida certa para quem busca uma experiência gastronômica.', FALSE, 1, 3, 1, NULL, NULL),
('Salmão Grelhado Ao Molho Meunière', '', 0.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Salmão Grelhado Ao Molho Meunière. Uma opção leve, nutritiva e muito saborosa, com molho refinado e peixe fresco.', FALSE, 1, 3, 1, NULL, NULL),
('Filé De Tilápia Grelhado Com Legumes', '', 28.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Filé De Tilápia Grelhado Com Legumes. Uma combinação saudável e colorida que traz equilíbrio para sua alimentação.', TRUE, 1, 3, 1, NULL, NULL),
('Filé De Merluza Grelhado', '', 24.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Filé De Merluza Grelhado. Sabor suave e textura delicada, ideal para quem prefere pratos mais leves.', TRUE, 1, 3, 1, NULL, NULL),
('Salada Especial Da Casa', '', 18.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Salada Especial Da Casa. Refrescante, nutritiva e cheia de ingredientes frescos, perfeita para dias quentes.', TRUE, 1, 3, 1, NULL, NULL),
('Prato Do Dia', '', 14.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Prato Do Dia. Uma surpresa saborosa preparada com ingredientes do dia, sempre frescos e bem temperados.', TRUE, 1, 3, 1, NULL, NULL),
('Carne De Panela', '', 19.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Carne De Panela. Cozida lentamente até ficar macia, essa carne é uma explosão de sabor e suculência.', TRUE, 1, 3, 1, NULL, NULL),
('Pernil Ao Molho', '', 17.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Pernil Ao Molho. Com molho especial e carne desmanchando, é perfeito para um almoço reforçado.', TRUE, 1, 3, 1, NULL, NULL),
('Calabresa Acebolada', '', 17.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Calabresa Acebolada. Uma variação suculenta da clássica calabresa com cebola, ideal para quem gosta de tradição.', TRUE, 1, 3, 1, NULL, NULL),
('Frango No Molho', '', 17.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Frango No Molho. Um prato caseiro, temperado com carinho, que agrada a todos os gostos.', TRUE, 1, 3, 1, NULL, NULL),
('Frango Assado', '', 17.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Frango Assado. Crocante por fora e macio por dentro, perfeito para um almoço completo.', TRUE, 1, 3, 1, NULL, NULL),
('Filé de Frango Grelhado', '', 17.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Filé de Frango Grelhado. Leve, saboroso e ideal para quem busca praticidade com qualidade.', TRUE, 1, 3, 1, NULL, NULL),
('Omelete Completo', '', 18.00, 'Prato feito com uma porção de arroz, feijão e a mistura: Omelete Completo. Rico em proteínas, com recheios variados e muito sabor.', TRUE, 1, 3, 1, NULL, NULL),
('Panqueca À Parmegiana', '', 17.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Panqueca À Parmegiana. Recheada, empanada e gratinada, é sucesso garantido no prato.', TRUE, 1, 3, 1, NULL, NULL),
('Filé de Tilapia', '', 17.99, 'Prato feito com uma porção de arroz, feijão e a mistura: Filé de Tilapia. Uma opção saudável, leve e muito saborosa, para refeições equilibradas.', TRUE, 1, 3, 1, NULL, NULL);

-- Acompanhamentos - Guarnições (categoria_id = 2)
INSERT INTO prato (
  nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id, quantidade_vendida, total_vendas
) VALUES
('Arroz', '', 8.00, 'Porção de arroz branco', true, 1, 3, 2, NULL, NULL),
('Feijão', '', 8.00, 'Porção de feijão temperado', true, 1, 3, 2, NULL, NULL),
('Farofa', '', 7.00, 'Farofa crocante feita com farinha de mandioca e temperos especiais', true, 1, 3, 2, NULL, NULL),
('Purê', '', 7.00, 'Purê de batata cremoso e suave', true, 1, 3, 2, NULL, NULL),
('Legumes', '', 7.00, 'Mix de legumes frescos cozidos no vapor', true, 1, 3, 2, NULL, NULL),
('Salada', '', 7.00, 'Salada fresca com folhas verdes selecionadas e molho especial', true, 1, 3, 2, NULL, NULL),
('Macarrão', '', 7.00, 'Macarrão ao dente com molho leve', true, 1, 3, 2, NULL, NULL),
('Batata Frita Média', '', 10.00, 'Porção de batata frita média', true, 1, 3, 2, NULL, NULL),
('Batata Frita Pequena', '', 5.00, 'Porção de batata frita pequena', true, 1, 3, 2, NULL, NULL),
('Batata Frita Grande', '', 15.00, 'Porção de batata frita grande', true, 1, 3, 2, NULL, NULL);


-- Pasteis Salgados (categoria_id = 3)
INSERT INTO prato (
  nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id, quantidade_vendida, total_vendas
) VALUES
('Pastel De Bauru', '', 10.99, 'Mussarella, presunto, orégano e tomate.', true, 1, 1, 3, NULL, NULL),
('Pastel De Brocolis', '', 10.99, 'Brocolis com queijo mussarela.', true, 1, 1, 3, NULL, NULL),
('Pastel De Carne', '', 10.99, 'Carne moída.', true, 1, 1, 3, NULL, NULL),
('Pastel Ceará', '', 10.99, 'Carne moída e pimenta seca.', true, 1, 1, 3, NULL, NULL),
('Pastel De Carne Com Queijo', '', 11.99, 'Carne moída com queijo mussarela.', true, 1, 1, 3, NULL, NULL),
('Pastel De Carne Com Queijo e Ovo', '', 11.99, 'Carne moída com queijo mussarela e fatias de ovo cozido.', true, 1, 1, 3, NULL, NULL),
('Pastel De Carne Seca Com Cheddar', '', 14.99, 'Carne seca desfiada com cheddar.', true, 1, 1, 3, NULL, NULL),
('Pastel De Carne Seca Com Queijo', '', 14.99, 'Carne seca desfiada com queijo mussarela.', true, 1, 1, 3, NULL, NULL),
('Pastel De Carne Seca Com Catupiry', '', 14.99, 'Carne seca desfiada com catupiry.', true, 1, 1, 3, NULL, NULL),
('Pastel De Calabresa Com Queijo', '', 11.99, 'Calabresa fatiada com queijo mussarela.', true, 1, 1, 3, NULL, NULL),
('Pastel De Calabresa Com Catupiry', '', 10.99, 'Calabresa com catupiry.', true, 1, 1, 3, NULL, NULL),
('Pastel Caipira', '', 11.99, 'Frango, milho, queijo e bacon.', true, 1, 1, 3, NULL, NULL),
('Pastel De Camarão Com Mussarela', '', 16.99, 'Camarão temperado com mussarela.', true, 1, 1, 3, NULL, NULL),
('Pastel De Camarão Com Catupiry', '', 16.90, 'Camarão com catupiry.', true, 1, 1, 3, NULL, NULL),
('Pastel De Camarão Com Cheddar', '', 16.90, 'Camarão com cheddar.', true, 1, 1, 3, NULL, NULL),
('Pastel De Costela No Bafo Mussarrela', '', 13.99, 'Costela desfiada cozida no bafo com mussarrella.', true, 1, 1, 3, NULL, NULL),
('Pastel De Costela No Bafo Catupiry', '', 13.99, 'Costela desfiada cozida no bafo, catupiry.', true, 1, 1, 3, NULL, NULL),
('Pastel De Costela No Bafo Cheddar', '', 13.99, 'Costela desfiada cozida no bafo, cheddar.', true, 1, 1, 3, NULL, NULL),
('Pastel De Frango Com Catupiry', '', 10.99, 'Frango desfiado com catupiry.', true, 1, 1, 3, NULL, NULL),
('Pastel De Frango Com Cheddar', '', 10.99, 'Frango desfiado com cheddar.', true, 1, 1, 3, NULL, NULL),
('Pastel De Palmito', '', 12.99, 'Palmito picado com queijo e azeitonas verde.', true, 1, 1, 3, NULL, NULL),
('Pastel Portuguesa', '', 12.99, 'Presunto, queijo, ovo, ervilha, cebola, azeitona picada e oregano.', true, 1, 1, 3, NULL, NULL),
('Pastel Pizza', '', 10.99, 'Mussarela, azeitona picada, tomate e orégano.', true, 1, 1, 3, NULL, NULL),
('Pastel De Queijo', '', 10.99, 'Pastel clássico de queijo mussarela.', true, 1, 1, 3, NULL, NULL),
('Pastel De Queijo Branco', '', 13.99, 'Queijo branco, tomate e orégano.', true, 1, 1, 3, NULL, NULL),
('Pastel Quatro Queijos', '', 13.99, 'Mussarela, parmesão, queijo prato e catupiry.', true, 1, 1, 3, NULL, NULL),
('Pastel Especial', '',  20.00, 'Carne, queijo, ovo, presunto, azeitona e tomate.', true, 1, 1, 3, NULL, NULL),
('Pastel De Escarola Com Queijo E Bacon', '', 11.99, 'Escarola refogada com queijo e bacon com pedaços de azoitona verde e cebola.', true, 1, 1, 3, NULL, NULL),
('Pastel Tauá', '', 14.99, 'Carne seca, queijo coalho e jerimum.', true, 1, 1, 3, NULL, NULL);

-- Pasteis Doces (categoria_id = 4)
INSERT INTO prato (
  nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id, quantidade_vendida, total_vendas
) VALUES
('Pastel De Banana Com Chocolate', '', 10.99, 'Banana fatiada com calda de chocolate derretida.', true, 1, 1, 4, NULL, NULL),
('Pastel De Banana Com Doce De Leite', '', 10.99, 'Banana fatiada com doce de leite.', true, 1, 1, 4, NULL, NULL),
('Pastel De Coco Com Leite Condensado', '', 10.99, 'Coco ralado com leite condensado.', true, 1, 1, 4, NULL, NULL),
('Pastel De Coco Com Doce De Leite', '', 10.99, 'Coco ralado com doce de leite.', true, 1, 1, 4, NULL, NULL),
('Pastel De Morango Com Chocolate', '', 13.99, 'Morango com chocolate derretido.', true, 1, 1, 4, NULL, NULL),
('Pastel De Chocolate', '', 10.99, 'Chocolate derretido.', true, 1, 1, 4, NULL, NULL),
('Pastel De Nutella Com Morango', '', 19.99, 'Nutella com morangos fatiados.', true, 1, 1, 4, NULL, NULL),
('Pastel Romeu E Julieta', '', 13.99, 'Queijo branco e goiabada cascão.', true, 1, 1, 4, NULL, NULL);

-- Lanches (categoria_id = 5)
INSERT INTO prato (
  nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id, quantidade_vendida, total_vendas
) VALUES
('Hot-Dog', '', 11.99, 'Salsicha, catupiry, milho, maionese, mostarda, ketchup, batata palha e pão de hot-dog', true, 1, 4, 5, NULL, NULL),
('Misto Quente', '', 10.00, 'Sanduíche de presunto, queijo e pão francês.', true, 1, 4, 5, NULL, NULL),
('Bauru', '', 11.00, 'Presunto, queijo, tomate, orégano e pão francês.', true, 1, 4, 5, NULL, NULL),
('Americano', '', 13.90, 'Presunto, queijo, ovo, tomate, alface e maionese.', true, 1, 4, 5, NULL, NULL),
('X-Salada', '', 12.90, 'Hambúrguer, queijo, alface, tomate, maionese e pão de hambúrguer.', true, 1, 4, 5, NULL, NULL),
('X-Egg', '', 14.90, 'Hambúrguer, queijo, ovo, alface, tomate, maionese e pão de hambúrguer.', true, 1, 4, 5, NULL, NULL),
('X-Bacon', '', 14.90, 'Hambúrguer, queijo, alface, tomate, maionese e pão de hambúrguer.', true, 1, 4, 5, NULL, NULL),
('X-Calabresa', '', 13.90, 'Linguiça calabresa, queijo, alface, tomate, maionese e pão francês.', true, 1, 4, 5, NULL, NULL),
('X-Frango', '', 13.90, 'Frango desfiado, queijo, alface, tomate, maionese e pão francês.', true, 1, 4, 5, NULL, NULL),
('X-Tudo', '', 22.00, 'Hambúrguer, queijo, ovo, presunto, bacon, alface, tomate, maionese e pão de hambúrguer.', true, 1, 4, 5, NULL, NULL),
('X-Churrasco', '', 22.00, 'Contra filé grelhado, queijo, alface, tomate, maionese e pão francês.', true, 1, 4, 5, NULL, NULL);

-- Sucos (categoria_id = 6)
INSERT INTO prato (
  nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id, quantidade_vendida, total_vendas
) VALUES
('Suco de Laranja Narutal', '', 14.00, 'Suco de laranja natural.', true, 1, 3, 6, NULL, NULL),
('Suco de Graviola com Água', '', 7.50, 'Suco de polpa de graviola preparado com água.', true, 1, 3, 6, NULL, NULL),
('Suco de Cupuaçu com Água', '', 7.50, 'Suco de polpa de cupuaçu preparado com água.', true, 1, 3, 6, NULL, NULL),
('Suco de Abacaxi com Hortelã Natural', '', 10.00, 'Suco de abacaxi com hortelã natural.', true, 1, 3, 6, NULL, NULL),
('Suco de Abacaxi com Hortelã com Água', '', 7.50, 'Suco de polpa abacaxi com hortelã preparado com água.', true, 1, 3, 6, NULL, NULL),
('Suco de Goiaba com Água', '', 7.50, 'Suco de polpa de goiaba preparado com água.', true, 1, 3, 6, NULL, NULL),
('Suco de Acerola com Água', '', 7.50, 'Suco de polpa de acerola preparado com água.', true, 1, 3, 6, NULL, NULL),
('Suco de Morango com Água', '', 10.00, 'Suco de polpa de morango preparado com água.', true, 1, 3, 6, NULL, NULL),
('Suco de Morango Natural', '', 14.00, 'Suco de morango natural.', true, 1, 3, 6, NULL, NULL),
('Suco de Maracujá com Água', '', 10.00, 'Suco de polpa de maracujá preparado com água.', true, 1, 3, 6, NULL, NULL),
('Vitaminas Mistas', '', 10.00, 'Vitamina com mix de frutas preparado com leite.', true, 1, 3, 6, NULL, NULL),
('Suco de Laranja com Leite', '', 14.00, 'Suco de laranja com polpa e leite.', true, 1, 3, 6, NULL, NULL),
('Suco de Graviola com Leite', '', 14.00, 'Suco de graviola com polpa e leite.', true, 1, 3, 6, NULL, NULL),
('Suco de Cupuaçu com Leite', '', 14.00, 'Suco de cupuaçu com polpa e leite.', true, 1, 3, 6, NULL, NULL),
('Suco de Abacaxi com Hortelã com Leite', '', 14.00, 'Suco de abacaxi com hortelã, polpa e leite.', true, 1, 3, 6, NULL, NULL),
('Suco de Goiaba com Leite', '', 14.00, 'Suco de goiaba com polpa e leite.', true, 1, 3, 6, NULL, NULL),
('Suco de Acerola com Leite', '', 14.00, 'Suco de acerola com polpa e leite.', true, 1, 3, 6, NULL, NULL),
('Suco de Morango com Leite', '', 14.00, 'Suco de morango com polpa e leite.', true, 1, 3, 6, NULL, NULL),
('Suco de Maracujá com Leite', '', 14.00, 'Suco de maracujá com polpa e leite.', true, 1, 3, 6, NULL, NULL);

-- Caipirinhas, energéticos (categoria_id = 9)
INSERT INTO prato (
  nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id, quantidade_vendida, total_vendas
) VALUES
('Caipirinha', '', 12.00, 'Caipirinha', true, 1, 3, 9, NULL, NULL),
('Caipirinha Com Frutas', '', 18.00, 'Caipirinha Com Frutas', true, 1, 3, 9, NULL, NULL),
('Caipiroska', '', 16.00, 'Caipiroska', true, 1, 3, 9, NULL, NULL),
('Caipiroska Com Frutas', '', 22.00, 'Caipiroska Com Frutas', true, 1, 3, 9, NULL, NULL),
('Monster Grande', '', 15.00, 'Monster Grande', true, 1, 3, 9, NULL, NULL),
('Red Bull', '', 15.00, 'Red Bull', true, 1, 3, 9, NULL, NULL);


-- BEBIDAS NATURAIS (categoria_id = 10)
INSERT INTO prato (
  nome, imagem, valor_venda, descricao, disponivel, funcionario_id, setor_id, categoria_id, quantidade_vendida, total_vendas
) VALUES
('Caldo De Cana 300ml', '', 7.00, 'Caldo De Cana 300ml', true, 1, 1, 10, NULL, NULL),
('Caldo De Cana 500ml', '', 9.00, 'Caldo De Cana 500ml', true, 1, 1, 10, NULL, NULL),
('Caldo De Cana 1 Litro', '', 18.00, 'Caldo De Cana 1 Litro', true, 1, 1, 10, NULL, NULL),
('Coco Verde', '', 8.00, 'Coco Verde', true, 1, 1, 10, NULL, NULL);
