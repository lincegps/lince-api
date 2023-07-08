CREATE TABLE IF NOT EXISTS usuario (
	id serial NOT NULL PRIMARY KEY,
	ind_ativo varchar(255) NULL,
	nome varchar(255) NULL,
	senha varchar(255) NULL,
	username varchar(255) NULL
);

INSERT INTO usuario(ind_ativo, nome, senha, username) VALUES ('S', 'Administrador', '123', 'admin')