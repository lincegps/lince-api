ALTER TABLE usuario RENAME COLUMN senha TO password;

UPDATE usuario SET password = '$2a$10$Xb.XNfzQ1NJXmT2jCcuw/ufZjJb.TxAx.Ld0s.v8nyGZ4UPxcQvhq' WHERE username = 'admin';

