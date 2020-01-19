## CRUD Android e SQL Server

<p>Este projeto foi desenvolvido para fins didáticos, nele foi desenvolvido um CRUD utilizando Android Studio e SQL Server conectando diretamente através do Driver JTDS.</p>

<p>Driver Utilizado: https://sourceforge.net/projects/jtds/</p>

<p>Estrutura do Banco utilizado nos testes:</p>
```
CREATE DATABASE empresa;

CREATE TABLE funcionarios (
	id INTEGER IDENTITY(1,1) PRIMARY KEY NOT NULL,
 	nome VARCHAR(100) NOT NULL,
 	telefone VARCHAR(20) NOT NULL,
 	idade INTEGER,
);
```