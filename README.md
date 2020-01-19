## CRUD Android e SQL Server

<p>Este projeto foi desenvolvido para fins didáticos, nele foi desenvolvido um CRUD utilizando Android Studio e SQL Server conectando diretamente através do Driver JTDS.</p>

<p>Driver Utilizado: https://sourceforge.net/projects/jtds/</p>

#### Estrutura do Banco utilizado

<p>CREATE DATABASE empresa;</p>
<br>
<p>CREATE TABLE funcionarios (</p>
<p>id INTEGER IDENTITY(1,1) PRIMARY KEY NOT NULL,</p>
<p>nome VARCHAR(100) NOT NULL,</p>
<p>telefone VARCHAR(20) NOT NULL,</p>
<p>idade INTEGER,</p>
<p>);</p>