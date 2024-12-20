create table receitas(
    id bigint not null auto_increment,
    descricao varchar(100) not null,
    valor decimal(10,2) not null ,
    data datetime not null,
    primary key(id)
);