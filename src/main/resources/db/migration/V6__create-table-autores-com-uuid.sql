drop table livros_autores;
drop table autores;

create table autores(
    id serial not null primary key,
    uuid UUID default gen_random_uuid(),
    nome varchar(150) not null
);