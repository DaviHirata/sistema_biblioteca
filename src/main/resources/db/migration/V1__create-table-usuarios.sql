create table usuarios (
    id serial not null primary key,
    uuid UUID default gen_random_uuid(),
    nome varchar(100) not null,
    email varchar(100) not null,
    telefone varchar(20) not null,
    senha varchar(255) not null,
    tipo_usuario varchar(13) not null,
    data_nasc date not null
)