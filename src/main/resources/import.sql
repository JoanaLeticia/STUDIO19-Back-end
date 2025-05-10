-- Usuários (pais de administrador/cliente)
INSERT INTO usuario (id, nome, email, senha, perfil) VALUES 
(1, 'Admin Sistema', 'admin@email.com', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 0),
(2, 'João Cliente', 'joao@email.com', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 1),
(3, 'Maria Cliente', 'maria@email.com', '2jqHB2Uf9imuz2oRVlzQCEMTCOoHPgbnPCwXCm100JmUzMNhlZFMjcXoeWp9T91TTCruG2sL5JNYRvt6wtw2Ew==', 1);

-- Administrador (relacionado ao usuario)
INSERT INTO administrador (id, cpf, data_nascimento) VALUES 
(1, '111.222.333-44', '1980-01-10');

-- Cliente (relacionado ao usuario)
INSERT INTO cliente (id) VALUES 
(2),
(3);

-- Telefone (relacionado a cliente)
INSERT INTO telefone (ddd, numero, id_cliente) VALUES 
('61', '912345678', 2),
('63', '912345675', 3);

-- Autor
INSERT INTO autor (id, nome, email_profissional, descricao, instagram, twitter, behance)
VALUES 
(10, 'Carlos Designer', 'carlos@studio.com', 'Especialista em branding', '@carlos', '@carlos_tw', '@carlos_bh');

-- Tags
INSERT INTO tag (id, nome) VALUES 
(100, 'UI Design'),
(101, 'Logotipo'),
(102, 'Social Media');

-- Projeto (relacionado a autor)
INSERT INTO projeto (id, nome_projeto, id_autor, categoria, data_desenvolvimento, nome_cliente, descricao_projeto, link_externo)
VALUES 
(200, 'Projeto Marca X', 10, 'DESIGN_BRANDING', '2023-09-01', 'Cliente X', 'Desenvolvimento de marca visual', 'https://exemplo.com/projetox');

-- Projeto_Tags (relacionamento N:N com projeto e tag)
INSERT INTO projeto_tags (projeto_id, tag_id) VALUES 
(200, 100),
(200, 101);

-- Produto
INSERT INTO produto (id, nome, subtitulo, descricao, preco, categoria)
VALUES 
(300, 'Identidade Visual Completa', 'Logo + Manual + Paleta', 'Pacote de identidade completa', 999.90, 'DESIGN_FIVEM');

INSERT INTO pacote (id, nome, descricao, valor) 
VALUES 
(1, 'Pacote Básico', 'Pacote com serviços essenciais', 199.99),
(2, 'Pacote Premium', 'Pacote com serviços adicionais e exclusivos', 499.99);

-- Inserindo itens inclusos nos pacotes
INSERT INTO pacote_itens (pacote_id, item_incluso) 
VALUES 
(1, 'Design de Logo'),
(1, 'Cartão de Visita'),
(2, 'Design de Logo'),
(2, 'Cartão de Visita'),
(2, 'Identidade Visual Completa');

-- Carrinho (relacionado ao cliente)
INSERT INTO carrinho (id, id_cliente, preco_total) VALUES 
(400, 2, 0.0);

-- Pedido (relacionado ao cliente)
INSERT INTO pedido (id, id_cliente, status, valor_total, link_projeto)
VALUES 
(500, 2, 'PENDENTE', 999.90, 'https://exemplo.com/entrega');

-- Item do pedido (relacionado a pedido e produto)
INSERT INTO item_pedido (id, id_pedido, id_produto, quantidade, preco)
VALUES 
(600, 500, 300, 1, 999.90),
(1, 1, 2, 199.99),
(2, 2, 1, 499.99);

-- Pagamento (relacionado ao pedido)
INSERT INTO pagamento (id, id_pedido, metodoPagamento, statusPagamento, data_pagamento, id_transacao, valor_pago)
VALUES 
(700, 500, 'PIX', 'APROVADO', '2024-05-08T10:00:00', 'txn_001abc', 999.90);

-- Área do cliente (relacionado ao cliente)
INSERT INTO area_cliente (id, id_cliente) VALUES 
(800, 2);

-- Atualização do pedido com área_cliente (após área criada)
UPDATE pedido SET id_area_cliente = 800 WHERE id = 500;
