CREATE TABLE IF NOT EXISTS contato (
	id serial NOT NULL PRIMARY KEY,
	data date NULL,
	forma_contato varchar(255) NULL,
    venda_id int4 NULL,
    cliente_id int4 NULL,
    usuario_id int4 NULL,
	observacao varchar(255) NULL,
	ind_satisfeito varchar(255) NULL,
	retornar_ligacao BOOLEAN DEFAULT FALSE,
	data_retorno_ligacao date NULL
);

ALTER TABLE contato ADD CONSTRAINT venda_id_fk FOREIGN KEY (venda_id) REFERENCES venda(id);
ALTER TABLE contato ADD CONSTRAINT cliente_id_fk FOREIGN KEY (cliente_id) REFERENCES cliente(id);
ALTER TABLE contato ADD CONSTRAINT usuario_id_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);