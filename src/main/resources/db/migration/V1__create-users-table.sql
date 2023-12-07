create table
  public.users (
    id bigserial,
    birth_date date not null,
    cpf text not null,
    email text not null,
    name text not null,
    password text not null,
    role text null,
    created_at timestamp without time zone null,
    constraint users_pkey primary key (id),
    constraint users_cpf_key unique (cpf),
    constraint users_email_key unique (email)
  ) tablespace pg_default;