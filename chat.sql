CREATE TABLE `usuario` (
  `usu_id` int(11) NOT NULL AUTO_INCREMENT,
  `usu_grupofk` int(11) NOT NULL,
  `usu_autenticacaofk` int(11) NOT NULL,
  `usu_pessoafk` int(11) NOT NULL,
  `usu_carencia` int(11) DEFAULT NULL,
  `usu_verificacarencia` int(11) DEFAULT NULL,
  `usu_ldapuid` varchar(100) DEFAULT NULL,
  `usu_ldapassociatoken` varchar(100) DEFAULT NULL,
  `usu_ldapoufk` int(11) DEFAULT NULL,
  `usu_ldapqueryfk` int(11) DEFAULT NULL,
  `usu_ldapdn` varchar(256) DEFAULT NULL,
  `usu_ldapatributonome` varchar(100) DEFAULT NULL,
  `usu_ultimaautenticacao` varchar(14) DEFAULT NULL,
  `usu_ldapativo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`usu_id`),
  KEY `usu_pessoafk` (`usu_pessoafk`),
  KEY `usu_autenticacaofk` (`usu_autenticacaofk`),
  KEY `usu_grupofk` (`usu_grupofk`),
  KEY `usu_ldapoufk` (`usu_ldapoufk`),
  KEY `usu_ldapqueryfk` (`usu_ldapqueryfk`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`usu_pessoafk`) REFERENCES `pessoa` (`pes_id`) ON DELETE CASCADE,
  CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`usu_autenticacaofk`) REFERENCES `autenticacao` (`aut_id`),
  CONSTRAINT `usuario_ibfk_3` FOREIGN KEY (`usu_grupofk`) REFERENCES `grupo` (`gru_id`),
  CONSTRAINT `usuario_ibfk_4` FOREIGN KEY (`usu_ldapoufk`) REFERENCES `ldapou` (`ldo_id`) ON DELETE CASCADE,
  CONSTRAINT `usuario_ibfk_5` FOREIGN KEY (`usu_ldapqueryfk`) REFERENCES `ldapquery` (`ldq_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10103 DEFAULT CHARSET=utf8;



CREATE TABLE `canal` (
  `can_id` int(11) NOT NULL AUTO_INCREMENT,
  `can_descricao` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`can_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;



CREATE TABLE `canaisdosusuarios` (
  `cdu_usuariofk` int(11) NOT NULL,
  `cdu_canalfk` int(11) NOT NULL,
  PRIMARY KEY (`cdu_usuariofk`,`cdu_canalfk`),
  KEY `cdu_canalfk` (`cdu_canalfk`),
  CONSTRAINT `canaisdosusuarios_ibfk_1` FOREIGN KEY (`cdu_usuariofk`) REFERENCES `usuario` (`usu_id`),
  CONSTRAINT `canaisdosusuarios_ibfk_2` FOREIGN KEY (`cdu_canalfk`) REFERENCES `canal` (`can_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `mensagem` (
  `men_id` int(11) NOT NULL AUTO_INCREMENT,
  `men_corpo` varchar(10000) DEFAULT NULL,
  `men_data_envio` varchar(50) DEFAULT NULL,
  `men_data_recebimento` varchar(50) DEFAULT NULL,
  `men_status` int(11) DEFAULT NULL,
  `men_usuariofk` int(11) NOT NULL,
  `men_canalfk` int(11) NOT NULL,
  PRIMARY KEY (`men_id`),
  KEY `men_usuariofk` (`men_usuariofk`),
  KEY `men_canalfk` (`men_canalfk`),
  CONSTRAINT `mensagem_ibfk_1` FOREIGN KEY (`men_usuariofk`) REFERENCES `usuario` (`usu_id`),
  CONSTRAINT `mensagem_ibfk_2` FOREIGN KEY (`men_canalfk`) REFERENCES `canal` (`can_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1810 DEFAULT CHARSET=utf8;
