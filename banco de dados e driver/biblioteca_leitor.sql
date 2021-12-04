CREATE DATABASE Biblioteca;
USE Biblioteca;

CREATE TABLE `leitor` (
  `codLeitor` int(11) NOT NULL AUTO_INCREMENT,
  `nomeLeitor` varchar(40) NOT NULL,
  `tipoLeitor` varchar(20) NOT NULL,
  PRIMARY KEY (`codLeitor`)
) ENGINE=InnoDB AUTO_INCREMENT=32312340 DEFAULT CHARSET=utf8;

INSERT INTO `leitor` VALUES (1,'Igor ','Aluno'),(2,'Professor Igor','Aluno');

