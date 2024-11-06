drop table reservas;
/*data de devolução real pode ser nula na criação do registro*/
create table reservas (
    id serial not null primary key,
    uuid UUID default gen_random_uuid(),
    id_usuario int not null,
    id_livro int not null,
    data_emprestimo date not null,
    data_devolucao date not null,
    data_devolucao_real date,
    status varchar(10),
    foreign key (id_usuario) references usuarios (id),
    foreign key (id_livro) references livros (id)
);