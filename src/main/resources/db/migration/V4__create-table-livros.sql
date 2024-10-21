create table livros(
    id serial not null primary key,
    uuid UUID default gen_random_uuid(),
    titulo varchar(100) not null,
    editora varchar(100) not null,
    ano_publicacao integer not null,
    isbn varchar(17) not null,
    categoria varchar(30) not null,
    quantidade_disponivel integer not null,
    descricao text
);