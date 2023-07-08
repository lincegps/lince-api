CREATE TABLE venda (
	id serial NOT NULL PRIMARY KEY,
	data_criacao date NULL,
	data_vencimento date NULL,
	forma_pagamento varchar(255) NULL,
	status_venda varchar(255) NULL,
	valor numeric(9,2) NULL,
	valor_desconto numeric(9,2) NULL,
	valor_total numeric(9,2) NULL,
	ind_nota_fical varchar(255) NULL,
	cliente_id int4 NULL,
	ponto_venda_id int4 NULL,
	produto_id int4 NULL,
	usuario_id int4 NULL
);

ALTER TABLE venda ADD CONSTRAINT cliente_id_fk FOREIGN KEY (cliente_id) REFERENCES cliente(id);
ALTER TABLE venda ADD CONSTRAINT ponto_venda_id_fk FOREIGN KEY (ponto_venda_id) REFERENCES ponto_venda(id);
ALTER TABLE venda ADD CONSTRAINT produto_id_fk FOREIGN KEY (produto_id) REFERENCES produto(id);
ALTER TABLE venda ADD CONSTRAINT usuario_id_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);