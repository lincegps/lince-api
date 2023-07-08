CREATE TABLE IF NOT EXISTS produto (
	id serial NOT NULL PRIMARY KEY,
	ind_ativo varchar(255) NULL,
	nome varchar(255) NULL,
	tipo_produto varchar(255) NULL,
	CONSTRAINT uk_nome UNIQUE (nome)
);

INSERT INTO produto (ind_ativo, nome, tipo_produto) VALUES('S', 'LinceGPS - APARELHO', 'APARELHO');
INSERT INTO produto (ind_ativo, nome, tipo_produto) VALUES('S', 'LinceGPS - ASSINATURA', 'ASSINATURA');