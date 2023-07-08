ALTER TABLE venda ADD COLUMN bandeira varchar(255) NULL;
ALTER TABLE venda ADD COLUMN codigo_cartao varchar(255) NULL;
ALTER TABLE venda ADD COLUMN ponto_venda_entrega_id int4;

ALTER TABLE venda ADD CONSTRAINT ponto_venda_entrega_id_fk FOREIGN KEY (ponto_venda_entrega_id) REFERENCES ponto_venda(id);