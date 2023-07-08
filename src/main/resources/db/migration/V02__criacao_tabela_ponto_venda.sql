CREATE TABLE IF NOT EXISTS ponto_venda (
	id serial NOT NULL PRIMARY KEY,
	bairro varchar(255) NULL,
	cidade varchar(255) NULL,
	ind_ativo varchar(255) NULL,
	nome varchar(255) NULL,
	responsavel varchar(255) NULL,
	tipo varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS ponto_venda_email (
	ponto_venda_id int4 NOT NULL,
	emails varchar(255) NULL
);

ALTER TABLE ponto_venda_email ADD CONSTRAINT ponto_venda_id_fk FOREIGN KEY (ponto_venda_id) REFERENCES ponto_venda(id);

CREATE TABLE IF NOT EXISTS ponto_venda_telefone (
	ponto_venda_id int4 NOT NULL,
	telefones varchar(255) NULL
);

ALTER TABLE ponto_venda_telefone ADD CONSTRAINT ponto_venda_id_fk FOREIGN KEY (ponto_venda_id) REFERENCES ponto_venda(id);

--Inserir Pontos de vendas padrão

INSERT INTO ponto_venda (bairro, cidade, ind_ativo, nome, responsavel, tipo) VALUES('', '', 'S', 'Fabricante', 'empresa', 'FABRICANTE');
INSERT INTO ponto_venda (bairro, cidade, ind_ativo, nome, responsavel, tipo) VALUES('', '', 'S', 'Centro de distribuição', 'Fabio', 'CENTRO_DISTRIBUICAO');