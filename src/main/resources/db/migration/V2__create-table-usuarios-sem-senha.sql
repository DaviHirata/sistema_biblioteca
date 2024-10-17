drop table usuarios;

create table usuarios (
    id serial not null primary key,
    nome varchar(100) not null,
    email varchar(100) not null,
    telefone varchar(20) not null,
    tipo_usuario varchar(13) not null,
    data_nasc date not null
);