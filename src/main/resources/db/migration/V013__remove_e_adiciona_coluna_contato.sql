ALTER TABLE contato DROP CONSTRAINT cliente_id_fk;

ALTER TABLE contato DROP COLUMN cliente_id;
ALTER TABLE contato DROP COLUMN forma_contato;

ALTER TABLE contato ADD COLUMN tipo_contato varchar(255) NULL;
ALTER TABLE contato ADD COLUMN feedback_contato varchar(255) NULL;


