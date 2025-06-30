create database supermercado;

use supermercado;

create table Funcionário(
	CPF varchar(14),
    Nome varchar(50),
    Função varchar(50),
    Salário double,
    primary key(CPF)
);