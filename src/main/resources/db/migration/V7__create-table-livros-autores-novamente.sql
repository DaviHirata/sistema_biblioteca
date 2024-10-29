create table livros_autores(
    id serial not null primary key,
    livro_id int not null,
    autor_id int not null,
    foreign key (livro_id) references livros(id),
    foreign key (autor_id) references autores(id)
);