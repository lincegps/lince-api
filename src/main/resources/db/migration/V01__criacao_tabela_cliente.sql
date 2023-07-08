CREATE TABLE IF NOT EXISTS cliente (
	id serial NOT NULL PRIMARY KEY,
	cpf_cnpj varchar(255) NULL,
	data_nascimento date NULL,
	bairro varchar(255) NULL,
	cidade varchar(255) NULL,
	ind_ativo varchar(255) NULL,
	nome varchar(255) NULL,
	tipo_pessoa varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS cliente_email (
	cliente_id int4 NOT NULL,
	emails varchar(255) NULL
);

CREATE TABLE IF NOT EXISTS cliente_telefone (
	cliente_id int4 NOT NULL,
	telefones varchar(255) NULL
);

ALTER TABLE cliente_email ADD CONSTRAINT cliente_id FOREIGN KEY (cliente_id) REFERENCES cliente(id);
ALTER TABLE cliente_telefone ADD CONSTRAINT cliente_id FOREIGN KEY (cliente_id) REFERENCES cliente(id);