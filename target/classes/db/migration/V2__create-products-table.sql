create table
  public.products (
    id bigserial,
    description character varying(255) null,
    name character varying(255) null,
    price real null,
    quantity integer null,
    constraint products_pkey primary key (id)
  ) tablespace pg_default;