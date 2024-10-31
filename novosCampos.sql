CREATE TABLE CANAL(
	can_id INTEGER NOT NULL AUTO_INCREMENT,
	can_descricao VARCHAR(256),
	PRIMARY KEY(can_id)
) ENGINE=InnoDB;

CREATE TABLE MENSAGEM(
	men_id INTEGER NOT NULL AUTO_INCREMENT,
	men_titulo VARCHAR(256),
	men_corpo VARCHAR(10000),
	men_data_envio VARCHAR(50),
	men_data_recebimento VARCHAR(50),
	men_status INTEGER,
	men_usuariofk INTEGER NOT NULL,
	men_canalfk INTEGER NOT NULL,
	PRIMARY KEY(men_id),
	FOREIGN KEY(men_usuariofk) REFERENCES usuario(usu_id),
	FOREIGN KEY(men_canalfk) REFERENCES canal(can_id)
) ENGINE=InnoDB;

CREATE TABLE CANAISDOSUSUARIOS(
	cdu_usuariofk INTEGER NOT NULL,
	cdu_canalfk INTEGER NOT NULL,
	PRIMARY KEY(cdu_usuariofk, cdu_canalfk),
	FOREIGN KEY(cdu_usuariofk) REFERENCES usuario(usu_id),
	FOREIGN KEY(cdu_canalfk) REFERENCES canal(can_id)
) ENGINE=InnoDB;