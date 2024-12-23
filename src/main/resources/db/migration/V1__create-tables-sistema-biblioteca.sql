create table usuarios (
    usuario_id bigserial not null primary key,
    uuid UUID default gen_random_uuid(),
    nome varchar(100) not null,
    email varchar(100) not null,
    telefone varchar(20) not null,
    tipo_usuario varchar(13) not null,
    data_nasc date not null
);

create table autores(
    autor_id bigserial not null primary key,
    uuid UUID default gen_random_uuid(),
    nome varchar(150) not null
);

create table livros(
    livro_id bigserial not null primary key,
    uuid UUID default gen_random_uuid(),
    titulo varchar(100) not null,
    editora varchar(100) not null,
    ano_publicacao integer not null,
    isbn varchar(17) not null,
    categoria varchar(30) not null,
    quantidade_disponivel integer not null,
    descricao text
);

create table livros_autores (
    livro_id bigint not null,
    autor_id bigint not null,
    primary key (livro_id, autor_id),
    foreign key (livro_id) references livros (livro_id),
    foreign key (autor_id) references autores (autor_id)
);

create table reservas (
    reserva_id bigserial not null primary key,
    uuid UUID default gen_random_uuid(),
    usuario_id bigint not null,
    livro_id bigint not null,
    data_emprestimo date not null,
    data_devolucao date not null,
    data_devolucao_real date,
    status varchar(10),
    foreign key (usuario_id) references usuarios (usuario_id),
    foreign key (livro_id) references livros (livro_id)
);