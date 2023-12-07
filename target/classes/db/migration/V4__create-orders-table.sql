create table
  public.orders (
    id bigserial,
    client_cpf character varying(255) null,
    client_id bigint null,
    client_name character varying(255) null,
    its_payed boolean not null,
    order_price real null,
    products jsonb null,
    user_id bigint null,
    user_name character varying(255) null,
    created_at timestamp without time zone null,
    payment_method bigint not null,
    constraint orders_pkey primary key (id),
    constraint orders_payment_method_fkey foreign key (payment_method) references payment_methods (id)
  ) tablespace pg_default;