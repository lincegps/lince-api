CREATE TABLE IF NOT EXISTS movimentacao (
	id serial NOT NULL PRIMARY KEY,
	data_movimentacao date NULL,
	observacao varchar(255) NULL,
	quantidade int4 NULL,
	tipo_movimentacao varchar(255) NULL,
	destino_ponto_venda_id int4 NULL,
	origem_ponto_venda_id int4 NULL,
	produto_id int4 NULL,
	usuario_id int4 NULL
);

ALTER TABLE movimentacao ADD CONSTRAINT produto_id_fk FOREIGN KEY (produto_id) REFERENCES produto(id);
ALTER TABLE movimentacao ADD CONSTRAINT origem_ponto_venda_id_fk FOREIGN KEY (origem_ponto_venda_id) REFERENCES ponto_venda(id);
ALTER TABLE movimentacao ADD CONSTRAINT usuario_id_fk FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE movimentacao ADD CONSTRAINT destino_ponto_venda_id_fk FOREIGN KEY (destino_ponto_venda_id) REFERENCES ponto_venda(id);